package com.thingsenz.files.utils

import android.content.Context
import android.widget.Toast
import java.util.concurrent.TimeUnit

object MediaUtils {

    fun Context.toast(msg: String) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    fun durationConverter(millis: Long)  = String.format("%02d:%02d",TimeUnit.MILLISECONDS.toMinutes(millis),TimeUnit.MILLISECONDS.toSeconds(millis)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)))




}