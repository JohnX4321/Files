package com.thingsenz.files.utils

interface UnzipProgress {

    fun progress(percent: Int,filename: String)

}