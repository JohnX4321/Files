package com.thingsenz.medialoader.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever

object AudioCoverUtils {

    @JvmStatic
    fun createAlbumArt(filePath: String): Bitmap? {
        var bitmap: Bitmap?=null
        val mmr=MediaMetadataRetriever()
        try{
            mmr.setDataSource(filePath)
            val bytes=mmr.embeddedPicture
            if (bytes!=null) bitmap=BitmapFactory.decodeByteArray(bytes,0,bytes.size)
        } catch (e: Exception){
            e.printStackTrace()
        } finally {
            try {
                mmr.release()
            } catch (e2: Exception){
                e2.printStackTrace()
            }
        }
        return bitmap
    }

}