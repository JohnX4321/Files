package com.thingsenz.medialoader.utils

import java.io.File
import java.io.FileFilter
import java.util.*
import kotlin.collections.ArrayList
import android.os.AsyncTask
import com.thingsenz.medialoader.inter.OnRecursionListener

object TraversalLoader {


    data class LoadParams(var root: File, var fileFilter: FileFilter)
    data class LoadProgress(var file: File,var counter: Int)
    interface OnLoadListener {
        fun onItemAdd(file: File,counter: Int)
    }

    @JvmStatic
    fun loadSsync(params: LoadParams,onRecursionListener: OnRecursionListener?) {
        onRecursionListener?.onStart()
        val res=ArrayList<File>()
        load(params.root,params.fileFilter,res,object : OnLoadListener{
            override fun onItemAdd(file: File, counter: Int) {
                onRecursionListener?.onItemAdd(file,counter)
            }
        })
        onRecursionListener?.onFinish(res)
    }

    @JvmStatic
    fun load(root: File?,filter: FileFilter,res: MutableList<File>,onLoadListener: OnLoadListener) {
        if (root!=null&&root.isDirectory) {
            val dirQueue = LinkedList<File>()
            dirQueue.offer(root)
            while (!dirQueue.isEmpty()){
                val dir=dirQueue.poll()
                val dirFiles=dir.listFiles()
                if (dirFiles!=null&& dirFiles.isNotEmpty()){
                    for (f in dirFiles) {
                        if (filter.accept(f)) {
                            res.add(f)
                            onLoadListener?.onItemAdd(f, res.size)
                        }
                        if (f.isDirectory) dirQueue.offer(f)
                    }
                }
            }
        }
    }

    @JvmStatic
    fun loadAsync(params: LoadParams,onRecursionListener: OnRecursionListener) : AsyncTask<LoadParams,LoadProgress,List<File>> {
        return AsyncTaskExecutor.executeConcurrently(object : AsyncTask<LoadParams,LoadProgress,List<File>>() {
            override fun doInBackground(vararg p0: LoadParams?): List<File> {
                val res=ArrayList<File>()
                val p=p0[0]!!
                load(p.root,p.fileFilter,res,object : OnLoadListener {
                    override fun onItemAdd(file: File, counter: Int) {
                        publishProgress(LoadProgress(file,counter))
                    }

                })
                return res
            }

            override fun onPreExecute() {
                super.onPreExecute()
                onRecursionListener?.onStart()
            }

            override fun onProgressUpdate(vararg values: LoadProgress?) {
                super.onProgressUpdate(*values)
                if (onRecursionListener!=null){
                    val p=values[0]
                    onRecursionListener.onItemAdd(p?.file!!,p?.counter!!)
                }
            }

            override fun onPostExecute(result: List<File>?) {
                super.onPostExecute(result)
                onRecursionListener?.onFinish(result!!)
            }

        },params)
    }






}