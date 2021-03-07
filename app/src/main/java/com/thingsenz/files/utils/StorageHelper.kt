package com.thingsenz.files.utils

import android.os.Environment

object StorageHelper {

    var externalStorageReadable: Boolean?=null
    var externalStorageWritable: Boolean?=null

    @JvmStatic
    fun isExternalStorageReadable(): Boolean {
        checkStorage()
        return externalStorageReadable!!
    }

    @JvmStatic
    fun isExternalStorageWritable(): Boolean {
        checkStorage()
        return externalStorageWritable!!
    }

    @JvmStatic
    fun isExternalStorageReadableAndWritable(): Boolean {
        checkStorage()
        return externalStorageWritable!!&& externalStorageReadable!!
    }

    private fun checkStorage() {
        val state=Environment.getExternalStorageState();
        if (state==Environment.MEDIA_MOUNTED)
        {externalStorageReadable= true
            externalStorageWritable=true}
        else if (state==Environment.MEDIA_MOUNTED_READ_ONLY) {
            externalStorageReadable=true
            externalStorageWritable=false
        } else
        {
            externalStorageReadable=false
            externalStorageWritable=false
        }
    }





}