package com.thingsenz.medialoader.utils

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import java.util.*

object UriGetPath {

    @JvmStatic
    fun getPath(context: Context,uri: Uri?) : String? {
        if (DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri!!)) {
                val docId=DocumentsContract.getDocumentId(uri)
                val split=docId.split(":")
                val type=split[0]
                if ("primary".equals(type, ignoreCase = true))
                    return Environment.getExternalStorageDirectory().absolutePath+"/"+split[1]
            } else if (isDownloadsDocument(uri!!)) {
                val id=DocumentsContract.getDocumentId(uri)
                val curi=ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),id.toLong())
                return getDataColumn(context,curi,null,null)
            } else if (isMediaDocument(uri!!)){
                val docId=DocumentsContract.getDocumentId(uri)
                val split=docId.split(":")
                val type=split[0]
                var contentUri :  Uri?=null
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                val sel="_id=?"
                val selArgs= arrayOf(split[1])
                return getDataColumn(context,contentUri,sel,selArgs)
            }
        } else if ("content".equals(uri?.scheme,ignoreCase = true))
            return getDataColumn(context,uri,null,null)
        else if ("file".equals(uri?.scheme,ignoreCase = true))
            return uri?.path
        else if (uri!=null){
            val scheme=uri.scheme
            val ssp=uri.encodedSchemeSpecificPart
            return "$scheme:$ssp"
        }
        return null
    }

    @JvmStatic
    fun getDataColumn(context: Context,uri: Uri?,sel: String?,selArgs: Array<String>?): String? {
        var cursor: Cursor?=null
        var column="_data"
        var proj= arrayOf(column)
        try {
            cursor=context.contentResolver.query(uri!!,proj,sel,selArgs,null)
            if (cursor!=null&&cursor.moveToFirst()){
                val colIndex=cursor.getColumnIndexOrThrow(column)
                return cursor.getString(colIndex)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    @JvmStatic
    fun isDownloadsDocument(uri: Uri) = "com.android.provider.downloads.documents"==uri.authority

    @JvmStatic
    fun isMediaDocument(uri: Uri) = "com.android.providers.media.documents"==uri.authority

    @JvmStatic
    fun isExternalStorageDocument(uri: Uri) = "com.android.externalstorage.documents"==uri.authority

}