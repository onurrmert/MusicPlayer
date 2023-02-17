package com.example.music.Util

import com.example.music.Model.MusicModel
import java.io.File

interface IFindMusic {

    fun getMusicFile(files: File) : ArrayList<File>?

    fun getMusic() : ArrayList<MusicModel>?
}