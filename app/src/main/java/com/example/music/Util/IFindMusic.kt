package com.example.music.Util

import android.content.Context
import com.example.music.Model.MusicModel

interface IFindMusic {

    fun getMusicFile(context: Context) : ArrayList<MusicModel>?
}