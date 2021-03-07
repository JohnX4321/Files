package com.thingsenz.medialoader.callback

import android.database.Cursor
import android.provider.BaseColumns
import android.provider.MediaStore
import androidx.loader.content.Loader
import com.thingsenz.medialoader.bean.FileItem
import com.thingsenz.medialoader.bean.FileProperty
import com.thingsenz.medialoader.bean.FileResult
import com.thingsenz.medialoader.bean.FileType

abstract class OnFileLoaderCallback: BaseFileLoaderCallback<FileResult> {

    constructor() {}
    constructor(type: FileType): super(type)
    constructor(property: FileProperty): super(property)

    override fun onLoadFinish(loader: Loader<Cursor>?, data: Cursor?) {
        val res=ArrayList<FileItem>()
        var item: FileItem
        var ss=0L
        while (data!!.moveToNext()){
            item=FileItem()
            val fileId=data.getInt(data.getColumnIndexOrThrow(BaseColumns._ID))
            val path=data.getString(data.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA))
            val size=data.getLong(data.getColumnIndexOrThrow(MediaStore.MediaColumns.SIZE))
            val name=data.getString(data.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME))
            val mime=data.getString(data.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE))
            val modified=data.getLong(data.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATE_MODIFIED))
            item.id=fileId
            item.displayName=name
            item.path=path
            item.size=size
            item.mime=mime
            item.modified=modified
            res.add(item)
            ss+=size
        }
        onResult(FileResult(ss,res))
    }


}