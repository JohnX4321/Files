package com.thingsenz.files.utils

import com.thingsenz.files.models.Song

interface MusicTouchListener {

    fun onSongTouch(song: Song)

}