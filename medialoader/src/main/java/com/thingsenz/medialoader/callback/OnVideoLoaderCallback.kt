package com.thingsenz.medialoader.callback

import android.database.Cursor
import android.graphics.PointF
import android.net.Uri
import android.provider.BaseColumns
import android.provider.MediaStore
import androidx.loader.content.Loader
import com.thingsenz.medialoader.bean.PhotoFolder
import com.thingsenz.medialoader.bean.VideoFolder
import com.thingsenz.medialoader.bean.VideoItem
import com.thingsenz.medialoader.bean.VideoResult

abstract class OnVideoLoaderCallback: BaseLoaderCallback<VideoResult>() {

    override fun getQueryUri(): Uri? {
        return MediaStore.Video.Media.EXTERNAL_CONTENT_URI
    }

    override fun getSelectProjection(): Array<String>? {
        return arrayOf(MediaStore.Video.Media._ID,MediaStore.Video.Media.DATA,MediaStore.Video.Media.BUCKET_ID,MediaStore.Video.Media.BUCKET_DISPLAY_NAME,MediaStore.Video.Media.DISPLAY_NAME,MediaStore.Video.Media.DURATION,MediaStore.MediaColumns.SIZE,MediaStore.Video.Media.DATE_MODIFIED)
    }

    override fun onLoadFinish(loader: Loader<Cursor>?, data: Cursor?) {
        val folders=ArrayList<VideoFolder>()
        var folder: VideoFolder
        var item: VideoItem
        var ss=0L
        val items=ArrayList<VideoItem>()
        while (data!!.moveToNext()){
            val folderId=data.getString(data.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.BUCKET_ID))
            val folderName=data.getString(data.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME))
            val videoID=data.getInt(data.getColumnIndexOrThrow(BaseColumns._ID))
            val name=data.getString(data.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME))
            val path=data.getString(data.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA))
            val duration=data.getLong(data.getColumnIndexOrThrow(MediaStore.Video.VideoColumns.DURATION))
            val size=data.getLong(data.getColumnIndexOrThrow(MediaStore.MediaColumns.SIZE))
            val modified=data.getLong(data.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_MODIFIED))
            item= VideoItem(videoID,name,path,size, modified, duration)
            folder= VideoFolder()
            folder.id=folderId
            folder.name=folderName
            if (folders.contains(folder))
                folders[folders.indexOf(folder)].addItem(item)
            else {
                folder.addItem(item)
                folders.add(folder)
            }
            items.add(item)
            ss+=size
        }
        onResult(VideoResult(folders,items,ss))
    }

}