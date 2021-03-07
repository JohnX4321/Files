package com.thingsenz.files.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.thingsenz.files.R


class WelcomeActivity: AppCompatActivity() {

    lateinit var btnTurnOn: Button
    lateinit var ldpl: LinearLayout
    val TAG=this.javaClass.simpleName
    val PERM_REQ_CODE=102
    val PERM= arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_activity)
        ldpl=findViewById(R.id.id_access_permissions_layout)
        btnTurnOn=findViewById(R.id.id_btn_turn_on)
        accessStorage()
        btnTurnOn.setOnClickListener { v->accessStorage() }
    }

    private fun accessStorage(){
        val hasWSP=ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (hasWSP!=PackageManager.PERMISSION_GRANTED) {
            val res=ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            if (res) {
                AlertDialog.Builder(this).setMessage("Storage Permission required")
                    .setPositiveButton(android.R.string.ok){ d, _->ActivityCompat.requestPermissions(
                        this,
                        PERM,
                        PERM_REQ_CODE
                    )}
                    .setNegativeButton("Deny"){ d, _->ldpl.visibility=View.VISIBLE}
                    .show()
                return
            } else
            {
                ActivityCompat.requestPermissions(this, PERM, PERM_REQ_CODE)
                return
            }
        }
        loadLockScreenActivity()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERM_REQ_CODE -> {
                if (grantResults.size > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        loadLockScreenActivity()
                    else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        ldpl.visibility = View.VISIBLE
                        val pref = getSharedPreferences("fileManager", 0)
                        if (!pref.getBoolean("is_camera_requested", false)) {
                            val edit = pref.edit()
                            edit.putBoolean("is_camera_requested", true);
                            edit.apply()
                            return
                        }
                    }
                }
            }
            else->super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun promptSettings() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Permission Required")
        builder.setMessage(Html.fromHtml("We require your consent to additional permission in order to proceed. Please enable them in <b>Settings</b>"))
        builder.setPositiveButton(
            "go to Settings"
        ) { dialog, which ->
            dialog.dismiss()
            goToSettings()
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, which ->
            // finish();
        }
        builder.show()
    }

    private fun loadLockScreenActivity(){
        startActivity(Intent(applicationContext,ScreenLockActivity::class.java))
        finish()
    }

    private fun goToSettings(){
        val i=Intent().apply {
            action=Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            addCategory(Intent.CATEGORY_DEFAULT)
            data=Uri.parse("package:"+packageName)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        }
    }


}