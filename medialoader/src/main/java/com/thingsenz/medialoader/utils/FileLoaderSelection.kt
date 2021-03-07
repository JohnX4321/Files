package com.thingsenz.medialoader.utils

import android.provider.MediaStore

object FileLoaderSelection {

    @JvmStatic
    fun getSelection(fileExtNames: Array<String>?,fileMT: Array<String>?) {
        var sel: String
        if (fileExtNames!=null&&fileExtNames.isNotEmpty()){
            val sb=StringBuilder()
            for (e in fileExtNames) sb.append(MediaStore.Files.FileColumns.DATA).append(" like ").append("%$e")
        }
    }

}