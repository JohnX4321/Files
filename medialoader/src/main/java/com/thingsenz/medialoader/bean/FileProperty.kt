package com.thingsenz.medialoader.bean

import android.provider.MediaStore

class FileProperty(var extension: List<String>?,var mime: List<String>?) {

    fun createSelection(): String? {
        var sel: String? =null
        if (extension!=null){
            val size=extension?.size!!
            val exBuilder=StringBuilder()
            for (i in 0..size) {
                exBuilder.append("(").append(MediaStore.Files.FileColumns.DATA).append(" like ? ").append(")")
                if (i<size-1) exBuilder.append(" OR ")
            }
            sel=exBuilder.toString()
        } else if (mime!=null) {
            val size=mime!!.size
            val mimeBuilder=StringBuilder()
            for (i in 0..size){
                mimeBuilder.append("(").append(MediaStore.Files.FileColumns.MIME_TYPE).append(" ==? ").append(")")
                if (i<size-1) mimeBuilder.append(" OR ")
            }
            sel=mimeBuilder.toString()
        }
        return sel
    }

    fun createSelectionArgs(): Array<String>? {
        var args: Array<String>? = null
        val sb=StringBuilder()
        if (extension!=null) {
            val size=extension!!.size
            if (size>0) args= arrayOf("")
            for ( i in 0..size){
                args!![i]="%${extension!![i]}"
                sb.append(args[i])
                if (i<size-1) sb.append(",")
            }
        } else if (mime!=null){
            val size=mime!!.size
            if (size>0) args= arrayOf("")
            for ( i in 0..size){
                args!![i]="%${mime!![i]}"
                sb.append(args[i])
                if (i<size-1) sb.append(",")
            }
        }
        return args
    }

}