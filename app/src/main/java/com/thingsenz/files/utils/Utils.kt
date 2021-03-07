package com.thingsenz.files.utils

object Utils {

    @JvmStatic
    fun millisToTimer(mills: Long): String {

        var finalTimerString=""
        var secString=""

        val hours=(mills/(1000*60*60)).toInt()
        val min=((mills%(1000*60*60))/(1000*60)).toInt()
        val sec=((mills%(1000*60*60))%(1000*60)/1000).toInt()
        if (hours>0)  finalTimerString= "$hours:"
        if (sec<10) secString="0$sec"
        else secString="$sec"
        finalTimerString+="$min:$secString"
        return finalTimerString
    }

    fun getProgressProgress(currDur: Long,totalDur: Long): Int {
        var pc: Double
        val currSec=(currDur/1000).toInt()
        val totSec=(totalDur/1000).toInt()
        pc=((currSec.toDouble()/totSec)*100)
        return pc.toInt()
    }

    fun progressToTimer(progress: Int,totalDur: Int): Int {
        var currDur: Int
        var totalDur=totalDur
        totalDur/=100
        currDur=((progress.toDouble()/100)*totalDur).toInt()
        return currDur*1000
    }

}