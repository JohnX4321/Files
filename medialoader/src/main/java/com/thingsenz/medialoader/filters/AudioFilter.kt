package com.thingsenz.medialoader.filters

import java.io.File
import java.io.FileFilter

class AudioFilter: FileFilter {

    override fun accept(file: File?): Boolean {
        var name=file!!.name
        val i=name.lastIndexOf('.')
        if (i!=-1){
            name=name.substring(i)
            if (name.equals(".mp3",ignoreCase = true)||name.equals(".wma",ignoreCase = true)||name.equals(".wav",ignoreCase = true)||name.equals(".flac",ignoreCase = true)||name.equals(".amr",ignoreCase = true)||name.equals(".aac",ignoreCase = true))
                return true
        }
        return false
    }

}