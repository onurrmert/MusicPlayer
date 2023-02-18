package com.example.music.Util

import android.net.Uri
import com.example.music.Model.MusicModel
import java.io.File

class MusicList {

    companion object{

        fun getMusiclist(musicFileList : ArrayList<File>) : ArrayList<MusicModel>{

            val musicList = ArrayList<MusicModel>()

            musicFileList.forEach {
                musicList.add(MusicModel( Uri.fromFile(it), it.name))
            }
            return musicList
        }
    }
}