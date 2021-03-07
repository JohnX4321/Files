package com.thingsenz.medialoader.inter

import java.io.File

interface OnRecursionListener {

    fun onStart()
    fun onItemAdd(file: File,counter: Int)
    fun onFinish(files: List<File>)

}