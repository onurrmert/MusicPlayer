package com.example.music.Util

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

class MediaPlayerController {

    companion object{

        var mediaPlayer: MediaPlayer? = null

        fun start(context: Context, uri: Uri){

            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(context, uri)
            }

            mediaPlayer?.start()
        }

        fun pause(context: Context, uri: Uri){

            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(context, uri)
            }
            if (mediaPlayer!!.isPlaying){
                mediaPlayer!!.pause()
            }
        }

        fun stop(){
            if (mediaPlayer != null){
                mediaPlayer!!.stop()
                mediaPlayer!!.reset()
                mediaPlayer!!.release()
                mediaPlayer = null
            }
        }
    }
}