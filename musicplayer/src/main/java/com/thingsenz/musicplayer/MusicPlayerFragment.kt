package com.thingsenz.musicplayer

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ramotion.fluidslider.FluidSlider

class MusicPlayerFragment : BottomSheetDialogFragment() {

    private lateinit var pauseBtn: Button
    private lateinit var playBtn: Button
    private lateinit var stopBtn: Button
    private lateinit var title: String
    private lateinit var artist: String
    private lateinit var uri: String
    private lateinit var duration: String
    private lateinit var seekbar: SeekBar
    private lateinit var mediaPlayer: MediaPlayer
    private var stop=false
    private var pause=false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        title=arguments!!.getString("Title","")
        artist=arguments!!.getString("Artist","")
        uri=arguments!!.getString("SongUri","")
        duration=arguments!!.getString("Duration","")
        return inflater.inflate(R.layout.layout_music_player,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playBtn=view.findViewById(R.id.id_play)
        pauseBtn=view.findViewById(R.id.id_pause)
        stopBtn=view.findViewById(R.id.id_stop)
        seekbar=view.findViewById(R.id.musicSeekBar)

        mediaPlayer = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setAudioAttributes(AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build())
            setDataSource(context!!, Uri.parse(uri))
            prepare()
        }


        playBtn.setOnClickListener { if (pause) {mediaPlayer.seekTo(mediaPlayer.currentPosition)
            mediaPlayer.start()
            pause=false
        }}

        pauseBtn.setOnClickListener {
            if (!pause) {
                pause=true
                mediaPlayer.pause()
            }
        }

        stopBtn.setOnClickListener {
            if (!pause){
                pause=true
                mediaPlayer.stop()
                mediaPlayer.release()
                mediaPlayer.release()
            }
        }
        val handler=Handler()
        requireActivity().runOnUiThread { object : Runnable {
            override fun run() {
                seekbar.progress=mediaPlayer.currentPosition/1000
                handler.postDelayed(this,1000)
            }

        }}

        seekbar.max=duration.toInt()
        seekbar.min=0
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (mediaPlayer!=null&&p2)
                    mediaPlayer.seekTo(p1*1000)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                //TODO("Not yet implemented")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                //TODO("Not yet implemented")
            }

        })

    }

}