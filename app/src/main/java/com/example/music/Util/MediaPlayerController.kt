package com.example.music.Util

import android.annotation.SuppressLint
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder

class MediaPlayerController : Service(){

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

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        start()
    }

    @Deprecated("Deprecated in Java")
    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
        start()
    }

    override fun stopService(name: Intent?): Boolean {
        pause()
        return super.stopService(name)
    }
}