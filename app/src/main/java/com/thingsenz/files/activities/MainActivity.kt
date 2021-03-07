package com.thingsenz.files.activities

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.StatFs
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.thingsenz.files.FilesApp
import com.thingsenz.files.R
import com.thingsenz.files.fragments.*
import com.thingsenz.files.models.Song
import com.thingsenz.files.utils.MusicTouchListener
import com.thingsenz.files.utils.PrefsManager
import java.io.File


class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener,MusicTouchListener {

    interface ButtonBackPressListener{
        fun onButtonBackPressed(navItemIndex: Int)
    }

    val TAG_INTERNAL_STORAGE="INTERNAL STORAGE"
    val TAG_EXTERNAL_STORAGE="EXTERNAL STORAGE"
    val TAG_IMAGES_LIST="IMAGES"
    val TAG_AUDIOS_LIST="AUDIOS"
    val TAG_VIDEOS_LIST="VIDEOS"
    val TAG_SETTINGS="SETTINGS"
    val TAG_MUSIC_PLAYER="MUSIC PLAYER"
    companion object {
        lateinit var buttonBackPressListener: ButtonBackPressListener
    }

    var navItemIndex=0
    lateinit var navigationView: NavigationView
    lateinit var prefsManager: PrefsManager
    lateinit var handler: Handler
    lateinit var runnable: Runnable
    var i=5
    lateinit var drawer: DrawerLayout
    lateinit var activityTitles: Array<String>
    var FG_TAG=TAG_INTERNAL_STORAGE



    fun formatSize(size: Long, tag: String): String {
        var size=size
        var suffix: String? = null
        if (size >= 1024) {
            suffix = "KB"
            size /= 1024
            if (size >= 1024) {
                suffix = "MB"
                size /= 1024
                if (size >= 1024) {
                    suffix = "GB"
                    size /= 1024
                }
            }
        }
        val resultBuffer = StringBuilder(java.lang.Long.toString(size))
        var commaOffset = resultBuffer.length - 3
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',')
            commaOffset -= 3
        }

        if (suffix != null) resultBuffer.append(suffix)
        return resultBuffer.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar=findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        handler= Handler()
        prefsManager= PrefsManager(FilesApp.getInstance().applicationContext)
        activityTitles=resources.getStringArray(R.array.nav_item_activity_titles)
        drawer=findViewById(R.id.drawer_layout)

        val toggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        ) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                loadHomeFragment()
            }
        }

        drawer.addDrawerListener(toggle)
        toggle.syncState()
        navigationView=findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        if (savedInstanceState==null){
            navItemIndex=0
            FG_TAG=TAG_INTERNAL_STORAGE
            navigationView.menu.getItem(0).setChecked(true)
            loadHomeFragment()
        }
    }

    private fun setActivityTitle() {
        if (supportActionBar!=null) supportActionBar!!.setTitle(activityTitles[navItemIndex])
    }
    private fun loadHomeFragment(){
        setActivityTitle()
        invalidateOptionsMenu()
        if (supportFragmentManager.findFragmentByTag(FG_TAG)!=null) {
            drawer.closeDrawers()
            return
        }
        var pendingRunnable = Runnable {
            val frag: Fragment=getHomeFragment()
            val ft=supportFragmentManager.beginTransaction()
            ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            ft.replace(R.id.frame, frag, FG_TAG)
            ft.commitAllowingStateLoss()
        }
        if (pendingRunnable!=null) handler.post(pendingRunnable)
    }

    private fun getHomeFragment(): Fragment {
       when(navItemIndex) {
           1 -> return ExternalStorageFragment()
           2->{
               return MusicListFragment()
           }
           5->return SettingsFragment()
            else->return InternalStorageFragment()
        }
    }

    private fun removeFragment(){
        if (supportFragmentManager.findFragmentByTag(FG_TAG)==null){
            try{
                supportFragmentManager!!.findFragmentById(R.id.frame)?.let {
                    supportFragmentManager.beginTransaction().remove(
                        it
                    ).commit()
                }
            } catch (e: Exception){e.printStackTrace()}
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (navItemIndex==0)
            menuInflater.inflate(R.menu.main_internal_storage, menu)
        else menuInflater.inflate(R.menu.main_external_storage, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        val id = item.itemId
        if (id == R.id.action_new_folder) {
            val internalStorageFragment =
                supportFragmentManager.findFragmentByTag(FG_TAG) as InternalStorageFragment?
            internalStorageFragment?.createNewFolder()
            return true
        } else if (id == R.id.action_new_file) {
            val internalStorageFragment =
                supportFragmentManager.findFragmentByTag(FG_TAG) as InternalStorageFragment?
            internalStorageFragment?.createNewFile()
            return true
        } else if (id == R.id.action_new_folder_external) {
            val externalStorageFragment =
                supportFragmentManager.findFragmentByTag(FG_TAG) as ExternalStorageFragment?
            externalStorageFragment?.createNewFolder()
            return true
        } else if (id == R.id.action_new_file_external) {
            val externalStorageFragment =
                supportFragmentManager.findFragmentByTag(FG_TAG) as ExternalStorageFragment?
            externalStorageFragment?.createNewFile()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id=item.itemId
        when(id){
            R.id.nav_internal_storage -> {
                navItemIndex = 0
                FG_TAG = TAG_INTERNAL_STORAGE
            }
            R.id.nav_external_storage -> {
                navItemIndex = 1
                FG_TAG = TAG_EXTERNAL_STORAGE
            }
            R.id.nav_audios->{
                //startActivity(Intent(this,MusicActivity::class.java))
                navItemIndex=2
                FG_TAG=TAG_AUDIOS_LIST
            }
            else->{
                navItemIndex=5
                FG_TAG=TAG_SETTINGS
            }
        }
        removeFragment()
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun getAvailableExternalStoragePercentage(): Int {
        return if (Environment.getExternalStorageState() ==
            Environment.MEDIA_MOUNTED
        ) {
            val path: File = Environment.getExternalStorageDirectory()
            val stat = StatFs(path.getPath())
            val blockSize = stat.blockSizeLong
            val totalBlocks = stat.blockCountLong
            val totalSize = totalBlocks * blockSize
            val availableBlocks = stat.availableBlocksLong
            val availableSize = availableBlocks * blockSize
            Log.d("here is", "" + availableSize * 100 / totalSize)
            val size = (availableSize * 100 / totalSize).toInt()
            100 - size
        } else {
            0
        }
    }

    private fun getAvailableInternalMemorySize(): String? {
        val path: File = Environment.getDataDirectory()
        Log.d("getPath", path.getPath())
        val stat = StatFs(path.getPath())
        val blockSize = stat.blockSizeLong
        val availableBlocks = stat.availableBlocksLong
        return formatSize(availableBlocks * blockSize, "free")
    }

    private fun getAvailableExternalMemorySize(): String? {
        return if (Environment.getExternalStorageState() ==
            Environment.MEDIA_MOUNTED
        ) {
            val path: File = Environment.getExternalStorageDirectory()
            val stat = StatFs(path.getPath())
            val blockSize = stat.blockSizeLong
            val availableBlocks = stat.availableBlocksLong
            formatSize(availableBlocks * blockSize, "free")
        } else {
            "0"
        }
    }

    private fun getAvailableInternalStoragePercentage(): Int {
        val path: File = Environment.getDataDirectory()
        val stat = StatFs(path.getPath())
        val blockSize = stat.blockSize.toLong()
        val totalBlocks = stat.blockCount.toLong()
        val totalSize = totalBlocks * blockSize
        val availableBlocks = stat.availableBlocks.toLong()
        val availableSize = availableBlocks * blockSize
        Log.d("here is", "" + availableSize * 100 / totalSize)
        val size = (availableSize * 100 / totalSize).toInt()
        return 100 - size
    }



    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START)
        else {
            if (navItemIndex>1) {
                navItemIndex=0
                FG_TAG=TAG_INTERNAL_STORAGE
                navigationView.menu.getItem(0).isChecked=true
                loadHomeFragment()
            } else
                buttonBackPressListener.onButtonBackPressed(navItemIndex)

        }
    }

    override fun onSongTouch(song: Song) {
        FG_TAG=TAG_MUSIC_PLAYER
        val f=PlayMusicFragment()
        val b=Bundle()
        b.putParcelable("PLAY_SONG",song)
        f.arguments=b
        //supportFragmentManager.beginTransaction().replace(R.id.frame,f).commit()
        supportFragmentManager.beginTransaction().replace(R.id.frame,f,FG_TAG).commitAllowingStateLoss()
    }

}