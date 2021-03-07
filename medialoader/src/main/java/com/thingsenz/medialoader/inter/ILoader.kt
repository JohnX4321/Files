package com.thingsenz.medialoader.inter

import android.net.Uri

interface ILoader {

    fun getSelectProjection(): Array<String>?
    fun getQueryUri(): Uri?
    fun getSortOrderSql(): String?
    fun getSelections(): String?
    fun getSelectionArgs(): Array<String>?

}