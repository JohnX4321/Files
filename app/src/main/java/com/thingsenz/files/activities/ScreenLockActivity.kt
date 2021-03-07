package com.thingsenz.files.activities


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.thingsenz.files.FilesApp
import com.thingsenz.files.R
import com.thingsenz.files.utils.PrefsManager


class ScreenLockActivity : AppCompatActivity() {
    private var preferManager: PrefsManager? = null
    private lateinit var btnOne: Button
    private lateinit var btnTwo: Button
    private lateinit var btnThree: Button
    private lateinit var btnFour: Button
    private lateinit var btnFive: Button
    private lateinit var btnSix: Button
    private lateinit var btnSeven: Button
    private lateinit var btnEight: Button
    private lateinit var btnNine: Button
    private lateinit var btnZero: Button
    private lateinit var btnCancel: Button
    private lateinit var imgDelete: ImageView
    private var tempPassword = ""
    private lateinit var txtPassword: EditText
    private var pswArray: ArrayList<String>? = null
    private var passwordLength = 0

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferManager = PrefsManager(FilesApp.getInstance().applicationContext)
        if (!preferManager!!.isPasswordActivated()) {
            val intent = Intent(
                FilesApp.getInstance().applicationContext,
                MainActivity::class.java
            )
            startActivity(intent)
            finish()
        } else {
            setContentView(R.layout.activity_screen_lock)
            btnOne = findViewById<View>(R.id.id_one) as Button
            btnTwo = findViewById<View>(R.id.id_two) as Button
            btnThree = findViewById<View>(R.id.id_three) as Button
            btnFour = findViewById<View>(R.id.id_four) as Button
            btnFive = findViewById<View>(R.id.id_five) as Button
            btnSix = findViewById<View>(R.id.id_six) as Button
            btnSeven = findViewById<View>(R.id.id_seven) as Button
            btnEight = findViewById<View>(R.id.id_eight) as Button
            btnNine = findViewById<View>(R.id.id_nine) as Button
            btnZero = findViewById<View>(R.id.id_zero) as Button
            btnCancel = findViewById<View>(R.id.id_cancel) as Button
            imgDelete = findViewById<View>(R.id.id_delete) as ImageView
            txtPassword = findViewById<View>(R.id.id_password) as EditText
            // set the ad unit ID

            pswArray = ArrayList()
            btnOne.setOnClickListener(object : OnClickListener {
                override fun onClick(view: View?) {
                    setPassword("1")
                }
            })
            btnTwo.setOnClickListener(object : OnClickListener {
                override fun onClick(view: View?) {
                    setPassword("2")
                }
            })
            btnThree.setOnClickListener(object : OnClickListener {
                override fun onClick(view: View?) {
                    setPassword("3")
                }
            })
            btnFour.setOnClickListener(object : OnClickListener {
                override fun onClick(view: View?) {
                    setPassword("4")
                }
            })
            btnFive.setOnClickListener(object : OnClickListener {
                override fun onClick(view: View?) {
                    setPassword("5")
                }
            })
            btnSix.setOnClickListener(object : OnClickListener {
                override fun onClick(view: View?) {
                    setPassword("6")
                }
            })
            btnSeven.setOnClickListener(object : OnClickListener {
                override fun onClick(view: View?) {
                    setPassword("7")
                }
            })
            btnEight.setOnClickListener(object : OnClickListener {
                override fun onClick(view: View?) {
                    setPassword("8")
                }
            })
            btnNine.setOnClickListener(object : OnClickListener {
                override fun onClick(view: View?) {
                    setPassword("9")
                }
            })
            btnZero.setOnClickListener(object : OnClickListener {
                override fun onClick(view: View?) {
                    setPassword("0")
                }
            })
            btnCancel.setOnClickListener(object : OnClickListener {
                override fun onClick(view: View?) {
                    finish()
                }
            })
            imgDelete.setOnClickListener(object : OnClickListener {
                override fun onClick(view: View?) {
                    removePassword()
                }
            })
        }
    }



    private fun removePassword() {
        passwordLength = pswArray!!.size
        Log.d("psw length", "" + passwordLength)
        if (passwordLength > 0) {
            pswArray!!.removeAt(passwordLength - 1)
            tempPassword = tempPassword.substring(0, passwordLength - 1)
            txtPassword!!.setText(tempPassword)
            Log.d("remove psw", tempPassword)
        }
    }

    private fun setPassword(strPassword: String) {
        passwordLength = pswArray!!.size
        if (passwordLength < 4) {
            pswArray!!.add(passwordLength, strPassword)
            tempPassword = tempPassword + pswArray!![passwordLength]
            txtPassword!!.setText(tempPassword)
            Log.d("password", tempPassword)
        }
        if (passwordLength == 3) {
            if (tempPassword == preferManager!!.getPassword()) {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(
                    FilesApp.getInstance().applicationContext,
                    "Wrong password",
                    Toast.LENGTH_SHORT
                ).show()
                tempPassword = ""
                txtPassword!!.setText("")
                passwordLength = 0
                pswArray!!.clear()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        //FilesApp.getInstance().trackScreenView("ScreenLock screen")
    }
}