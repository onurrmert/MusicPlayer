package com.example.music.Music

import android.content.Context
import com.example.music.Model.MusicModel

interface IFindMusic {

    fun getMusicFile(context: Context) : ArrayList<MusicModel>?
}