package com.thingsenz.medialoader.loaders

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import com.thingsenz.medialoader.callback.OnLoaderCallback
import java.lang.ref.WeakReference

abstract class AbsLoaderCallback(val cont: Context,val onLoaderCallback: OnLoaderCallback): LoaderManager.LoaderCallbacks<Cursor> {

    val context=WeakReference<Context>(cont)
    var loaderId=0

    override fun onCreateLoader(id: Int, args: Bundle?): BaseCursorLoader {
        loaderId=id
        return BaseCursorLoader(context.get()!!,onLoaderCallback)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        onLoaderCallback.onLoadFinish(loader, data)
        destroyLoader()
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        onLoaderCallback.onLoaderReset()
    }

    private fun destroyLoader(){
        try{
            if (context!=null){
                val ctx=context.get()
                if (ctx!=null) (ctx as FragmentActivity).supportLoaderManager.destroyLoader(loaderId)
            }
        } catch (e: Exception){
            e.printStackTrace()
        }
    }



}