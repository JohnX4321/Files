package com.thingsenz.medialoader.utils

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.media.ThumbnailUtils
import android.os.Build
import android.os.CancellationSignal
import android.util.Size
import java.io.File

object VideoThumbnailUtils {

    fun getVideoThumb(path: String) : Bitmap {
        val media=MediaMetadataRetriever()
        media.setDataSource(path)
        return media.frameAtTime
    }

    fun getVideoThumb(path: String,kind: Int) = if (Build.VERSION.SDK_INT<=28) ThumbnailUtils.createVideoThumbnail(path,kind) else ThumbnailUtils.createVideoThumbnail(
        File(path),Size(96,96), CancellationSignal()
    )



}