package com.thingsenz.files.utils

import android.app.Notification
import android.content.Context
import android.content.SharedPreferences

class PrefsManager(context: Context) {

    companion object {
        const val SELECTED_ADDRESS="address"
        const val PREF_NAME="Files"
        const val KEY_NOTIFICATIONS="notifications"
        const val IS_FIRST_TIME="isFirstTime"
        const val IS_NOTIFICATIONS_ON="isNotificationOn"
        const val IS_PASSWORD_ON="isPasswordOn"
        const val KEY_PASSWORD="password"
        const val IS_HIDDEN_FILE_SHOW="isHiddenFileShow"
    }

    private val pref: SharedPreferences =context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
    private val editor=pref.edit()

    fun addNotification(notification: String){
        var old=getNotifications()
        if (old!=null) old+="|"+notification
        else old=notification
        editor.putString(KEY_NOTIFICATIONS,old)
        editor.commit()
    }

    fun getNotifications() = pref.getString(KEY_NOTIFICATIONS,null)

    fun clearSession() {
        editor.clear()
        editor.commit()
        setFirstTimeLaunch(false)
    }

    fun setFirstTimeLaunch(isFT: Boolean){
        editor.putBoolean(IS_FIRST_TIME,isFT)
        editor.commit()
    }

    fun isFirstTimeLaunch() = pref.getBoolean(IS_FIRST_TIME,true)

    fun setNotificationOn(inon: Boolean){
        editor.putBoolean(IS_NOTIFICATIONS_ON,inon)
        editor.commit()
    }

    fun isNotificationOn() = pref.getBoolean(IS_NOTIFICATIONS_ON,true)

    fun setPasswordActivated(ispon: Boolean){
        editor.putBoolean(IS_PASSWORD_ON,ispon)
        editor.commit()
    }

    fun isPasswordActivated() = pref.getBoolean(IS_PASSWORD_ON,false)

    fun setHiddenFileVisible(ihfv: Boolean) = editor.putBoolean(IS_HIDDEN_FILE_SHOW,ihfv).commit()

    fun isHiddenFileVisible() =pref.getBoolean(IS_HIDDEN_FILE_SHOW,false)

    fun setPassword(pass: String) = editor.putString(KEY_PASSWORD,pass).commit()

    fun getPassword() = pref.getString(KEY_PASSWORD,"")


}