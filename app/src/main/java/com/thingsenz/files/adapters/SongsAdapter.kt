package com.thingsenz.files.adapters

import android.content.Context
import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.thingsenz.files.R
import com.thingsenz.files.databinding.LayoutItemSongBinding

import com.thingsenz.files.models.Song
import com.thingsenz.files.utils.MusicTouchListener

class SongsAdapter(val context: Context,val songs: ArrayList<Song>,val musicTouchListener: MusicTouchListener): RecyclerView.Adapter<SongsAdapter.SongViewHolder>() {



    inner class SongViewHolder(v: View): RecyclerView.ViewHolder(v){
        val tvSortOrder=v.findViewById<TextView>(R.id.tvOrder)
        val tvTitle = v.findViewById<TextView>(R.id.song_title)
        val tvArtist=v.findViewById<TextView>(R.id.song_artist)
        val tvDuration=v.findViewById<TextView>(R.id.tvDuration)
        init {
            v.setOnClickListener { i -> musicTouchListener.onSongTouch(songs[adapterPosition]) }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.layout_item_song,parent,false)
        //v.setOnClickListener(this)
        return SongViewHolder(v)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val curr=songs[position]
        //Log.d("Index",position.toString())
        holder.tvTitle.text=curr.songTitle
        holder.tvArtist.text=curr.songArtist
        holder.tvDuration.text=curr.songDuration
        holder.tvSortOrder.text="${position+1}"
    }



    override fun onBindViewHolder(holder: SongViewHolder, position: Int, payloads: MutableList<Any>) {
        val curr=songs[position]
        //Log.d("Index",position.toString())
        holder.tvTitle.text=curr.songTitle
        holder.tvArtist.text=curr.songArtist
        holder.tvDuration.text=curr.songDuration
        holder.tvSortOrder.text="${position+1}"
    }

    override fun getItemCount(): Int {
        return songs.size
    }

}