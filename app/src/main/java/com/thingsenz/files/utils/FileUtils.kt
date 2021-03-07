package com.thingsenz.files.utils

import android.content.Context
import java.io.*
import java.lang.Exception
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream
import kotlin.jvm.Throws

object FileUtils {

    @JvmStatic @Throws(FileNotFoundException::class)
    fun unzipFile(context: Context,filename: String,target: String,progress: UnzipProgress?) {
        val zipFile=File(filename)
        val dest=File(target)
        val len=zipFile.length()
        var extLen=0
        val zis=ZipInputStream(BufferedInputStream(FileInputStream(zipFile)))
        try{
            var ze=zis.nextEntry
            var count=0
            var buffer=ByteArray(1024)
            while (ze!=null){
                if (progress!=null) {
                    extLen += ze.compressedSize.toInt()
                    var filen = ze.name
                    var pc = (extLen * 100 / len).toInt()
                    progress.progress(pc, filename)
                }
                val file=File(target,ze.name)
                val dir=if (ze.isDirectory) file else file.parentFile
                if (!dir.isDirectory&&!dir.mkdirs()) throw FileNotFoundException("Failed to ensure dir: "+dir.absolutePath)
                if (ze.isDirectory) {
                    ze=zis.nextEntry
                    continue
                }
                val fout=FileOutputStream(file)
                try{
                    count=zis.read(buffer)
                    while (count!=-1){
                        fout.write(buffer,0,count)
                        count=zis.read(buffer)
                    }
                } finally {
                    fout.close()
                }
                val time=ze.time
                if (time>0) file.setLastModified(time)
            }


        } finally {
            zis.close()
        }
    }


    @JvmStatic
    fun zipFile(files: Array<String>,filename: String) {
        try{
            val dest=FileOutputStream(filename)
            val out=ZipOutputStream(BufferedOutputStream(dest))
            var data=ByteArray(80000)
            for (i in files.indices){
                val fi=FileInputStream(files[i])
                val orig=BufferedInputStream(fi,80000)
                val entry=ZipEntry(files[i].substring(files[i].lastIndexOf("/")+1))
                out.putNextEntry(entry)
                var count=orig.read(data,0,80000)
                while (count!=-1){
                    out.write(data,0,count)
                    count=orig.read(data,0,80000)
                }
                orig.close()
            }
            out.close()
        } catch (e: Exception){
            e.printStackTrace()
        }
    }


}