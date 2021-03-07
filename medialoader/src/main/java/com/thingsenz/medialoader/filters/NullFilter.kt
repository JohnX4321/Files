package com.thingsenz.medialoader.filters

import java.io.File
import java.io.FileFilter

class NullFilter: FileFilter {

    override fun accept(p0: File?): Boolean {
        return true
    }

}