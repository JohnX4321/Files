package com.thingsenz.medialoader.filters

import java.io.File
import java.io.FileFilter

class PhotoFilter: FileFilter {

    override fun accept(file: File?): Boolean {
        if (file!!.length()<0) return false
        var name=file!!.name
        val i=name.lastIndexOf('.')
        if (i!=-1){
            name=name.substring(i)
            if (name.equals(".jpg",ignoreCase = true)||name.equals(".jpeg",ignoreCase = true)||name.equals(".png",ignoreCase = true)||name.equals(".gif",ignoreCase = true)||name.equals(".bmp",ignoreCase = true))
                return true
        }
        return false
    }

}