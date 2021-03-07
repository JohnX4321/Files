package com.thingsenz.medialoader.callback

import android.database.Cursor
import androidx.loader.content.Loader
import com.thingsenz.medialoader.inter.ILoader

abstract class OnLoaderCallback : ILoader{

    abstract fun onLoadFinish(loader: Loader<Cursor>?,data: Cursor?)
    fun onLoaderReset() {}

}