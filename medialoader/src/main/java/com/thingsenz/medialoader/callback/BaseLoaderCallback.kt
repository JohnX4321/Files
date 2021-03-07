package com.thingsenz.medialoader.callback

import android.provider.MediaStore

abstract class BaseLoaderCallback<T> : OnLoaderCallback() {

    abstract fun onResult(result: T)

    override fun getSelections(): String? {
        return MediaStore.MediaColumns.SIZE+" > ?"
    }

    override fun getSelectionArgs(): Array<String>? {
        return arrayOf("0")
    }

    override fun getSortOrderSql(): String? {
        return MediaStore.MediaColumns.DATE_MODIFIED+" DESC"
    }

}