package com.example.music.Util

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

class MediaPlayerController{

    companion object{

        var mediaPlayer: MediaPlayer? = null

        @SuppressLint("StaticFieldLeak")
        var context : Context? = null

        var uri: Uri? = null

        fun start(){

            if (mediaPlayer == null) mediaPlayer = MediaPlayer.create(context, uri)

            mediaPlayer?.start()
        }

        fun pause(){

            if (mediaPlayer == null) mediaPlayer = MediaPlayer.create(context, uri)

            if (mediaPlayer!!.isPlaying) mediaPlayer!!.pause()
        }
    }
}