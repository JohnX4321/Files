package com.thingsenz.medialoader.callback

import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns
import android.provider.BaseColumns._ID
import android.provider.MediaStore
import android.provider.MediaStore.MediaColumns.*
import androidx.loader.content.Loader
import com.thingsenz.medialoader.bean.*

abstract class OnPhotoLoaderCallback: BaseLoaderCallback<PhotoResult>() {

    override fun getQueryUri(): Uri? {
        return MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    }

    override fun getSelectProjection(): Array<String>? {
        return arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.DATE_MODIFIED)
    }

    override fun onLoadFinish(loader: Loader<Cursor>?, data: Cursor?) {
        val folders=ArrayList<PhotoFolder>()
        val photos=ArrayList<PhotoItem>()
        if (data==null){
            onResult(PhotoResult(folders,photos))
            return
        }
        var folder: PhotoFolder
        var item: PhotoItem
        var ss=0L
        while (data!!.moveToNext()){
            val folderId=data.getString(data.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID))
            val folderName=data.getString(data.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))
            val imageID=data.getInt(data.getColumnIndexOrThrow(_ID))
            val name=data.getString(data.getColumnIndexOrThrow(DISPLAY_NAME))
            val size=data.getLong(data.getColumnIndexOrThrow(SIZE))
            val path=data.getString(data.getColumnIndexOrThrow(DATA))
            val modified=data.getLong(data.getColumnIndexOrThrow(DATE_MODIFIED))
            folder= PhotoFolder()
            folder.id=folderId
            folder.name=folderName
            item= PhotoItem(imageID,name,path,size,modified)
            if (folders.contains(folder))
                folders[folders.indexOf(folder)].addItem(item)
            else {
                folder.cover=path
                folder.addItem(item)
                folders.add(folder)
            }
            photos.add(item)
            ss+=size
        }
        onResult(PhotoResult(folders,photos,ss))
    }

}