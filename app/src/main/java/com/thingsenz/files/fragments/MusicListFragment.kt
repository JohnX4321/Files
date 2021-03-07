package com.thingsenz.files.fragments

import android.content.Context
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thingsenz.files.R
import com.thingsenz.files.adapters.SongsAdapter
import com.thingsenz.files.models.Song
import com.thingsenz.files.utils.DividerItemDecoration
import com.thingsenz.files.utils.MediaUtils
import com.thingsenz.files.utils.MusicTouchListener
import java.lang.ClassCastException

class MusicListFragment: Fragment() {

   // private var _binding: FragmentMusicListBinding?=null
    //private val binding get() = _binding!!
    var songList=ArrayList<Song>()
    private lateinit var songAdapter: SongsAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var musicTouchListener: MusicTouchListener



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadSong()
        return inflater.inflate(R.layout.fragment_music_list,container,false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try  {
            musicTouchListener=context as MusicTouchListener
        } catch (e: ClassCastException){
            e.printStackTrace()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView=view.findViewById(R.id.rvSongList)
        //loadSong()
        setupRV()
    }

    private fun loadSong(){
        val allSongsUri=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val sel=MediaStore.Audio.Media.IS_MUSIC+"!=0"
        val sortOrder=" ${MediaStore.Audio.Media.DISPLAY_NAME} ASC"
        val cursor=requireActivity().applicationContext.contentResolver.query(allSongsUri,null,sel,null,sortOrder)
        //songList.add(Song("Demo","Demo","Demo","Demo"))
        if (cursor!=null) {
            while (cursor.moveToNext()){
                val songURI =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                val songAuthor =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                val songName =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                val songDuration =
                    cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                val songDurLong = songDuration.toLong()
                songList.add(
                    Song(
                        songName, songAuthor,
                        songURI, MediaUtils.durationConverter(songDurLong)
                    )
                )
            }
            cursor.close()
        }

    }

    private fun setupRV(){
        songAdapter= SongsAdapter(requireContext(),songList,musicTouchListener)
       /* binding.rvSongList.apply {
            layoutManager=LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter=songAdapter
            addItemDecoration(object : DividerItemDecoration(activity,LinearLayout.VERTICAL)
            {})
        }*/
        Log.d("HUUGGE",songList.size.toString())
        //recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager=LinearLayoutManager(activity)
        recyclerView.adapter=songAdapter
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(androidx.recyclerview.widget.DividerItemDecoration(activity,LinearLayout.VERTICAL))
        //songList.clear()
    }

    override fun onDestroy() {
        super.onDestroy()

    }

}