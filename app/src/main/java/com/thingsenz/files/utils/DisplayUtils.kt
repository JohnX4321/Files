package com.thingsenz.files.utils

import android.content.res.Resources

object DisplayUtils {

    @JvmStatic
    fun dp2px(resources: Resources,dp: Float): Float {
        val scale=resources.displayMetrics.density
        return dp*scale+0.5f
    }

    @JvmStatic
    fun sp2px(resources: Resources,sp: Float): Float {
        val scale=resources.displayMetrics.scaledDensity
        return sp*scale
    }

}