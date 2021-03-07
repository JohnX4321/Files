package com.thingsenz.medialoader.filters

import java.io.File
import java.io.FileFilter

class VideoFilter: FileFilter {

    override fun accept(file: File?): Boolean {
        var name=file!!.name
        val i=name.lastIndexOf('.')
        if (i!=-1){
            name=name.substring(i)
            if (name.equals(".mp4",ignoreCase = true)||name.equals(".wmv",ignoreCase = true)||name.equals(".3gp",ignoreCase = true)||name.equals(".ts",ignoreCase = true)||name.equals(".rmvb",ignoreCase = true)||name.equals(".mov",ignoreCase = true)||name.equals(".mov",ignoreCase = true)||name.equals(".avi",ignoreCase = true)||name.equals(".m3u8",ignoreCase = true)||name.equals(".flv",ignoreCase = true)||name.equals(".mkv",ignoreCase = true)||name.equals(".f4v",ignoreCase = true)||name.equals(".rm",ignoreCase = true)||name.equals(".mpg",ignoreCase = true)||name.equals(".swf",ignoreCase = true))
                return true
        }
        return false
    }

}