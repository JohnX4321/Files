package com.thingsenz.medialoader.callback

import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns
import android.provider.MediaStore
import androidx.loader.content.Loader
import com.thingsenz.medialoader.bean.AudioItem
import com.thingsenz.medialoader.bean.AudioResult

abstract class OnAudioLoaderCallback : BaseLoaderCallback<AudioResult>() {

    override fun getQueryUri(): Uri? {
        return MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    }

    override fun getSelectProjection(): Array<String>? {
        return arrayOf(MediaStore.Audio.Media._ID,MediaStore.Audio.Media.DATA,MediaStore.Audio.Media.DISPLAY_NAME,MediaStore.Audio.Media.DURATION,MediaStore.MediaColumns.SIZE,MediaStore.Audio.Media.DATE_MODIFIED)
    }

    override fun onLoadFinish(loader: Loader<Cursor>?, data: Cursor?) {
        val res=ArrayList<AudioItem>()
        var item: AudioItem
        var sum_size=0L
        while (data!!.moveToNext()){
            item= AudioItem()
            val audioID=data.getInt(data.getColumnIndexOrThrow(BaseColumns._ID))
            val name=data.getString(data.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME))
            val path=data.getString(data.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_MODIFIED))
            val duration=data.getLong(data.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DURATION))
            val size=data.getLong(data.getColumnIndexOrThrow(MediaStore.MediaColumns.SIZE))
            val modified=data.getLong(data.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_MODIFIED))
            item.id=audioID
            item.displayName=name
            item.path=path
            item.duration=duration
            item.size=size
            item.modified=modified
            res.add(item)
            sum_size+=size
        }
        onResult(AudioResult(sum_size,res))
    }

}