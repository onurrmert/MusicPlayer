package com.example.music.Util

class TimetoMSS {

    companion object{
        fun timestampToMSS(position: Int): String {
            val totalSeconds = Math.floor(position / 1E3).toInt()
            val minutes = totalSeconds / 60
            val remainingSeconds = totalSeconds - (minutes * 60)
            return (minutes.toString() + ":" + remainingSeconds.toString())
        }
    }
}