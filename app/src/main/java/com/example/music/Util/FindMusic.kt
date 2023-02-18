package com.example.music.Util

import java.io.File

class FindMusic : IFindMusic {

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