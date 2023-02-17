package com.example.music.Util

import android.net.Uri
import android.os.Environment
import com.example.music.Model.MusicModel
import java.io.File

class FindMusic : IFindMusic {

    override fun getMusic() : ArrayList<MusicModel>{

        val list = ArrayList<MusicModel>()

        getMusicFile(Environment.getExternalStorageDirectory()).forEach {
            list.add(MusicModel(Uri.fromFile(it), it.name))
        }

        return list
    }

    override fun getMusicFile(files: File): ArrayList<File> {

        val musicList = ArrayList<File>()

        val musicArrays = files.listFiles()

        for (singleFile in musicArrays!!){

            if (singleFile.isDirectory && !singleFile.isHidden){
                musicList.addAll(getMusicFile(singleFile))
            }else{
                if (singleFile.name.endsWith(".mp3")){
                    musicList.add(singleFile)
                }
            }
        }
        return musicList
    }
}