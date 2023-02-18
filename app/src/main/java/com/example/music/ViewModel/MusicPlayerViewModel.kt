package com.example.music.ViewModel

import android.os.Environment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.music.Util.IFindMusic
import java.io.File

class MusicPlayerViewModel : ViewModel() {

    val musicList = MutableLiveData<ArrayList<File>>()

    fun getMusicFile(iFindMusic: IFindMusic){
        musicList.value = iFindMusic.getMusicFile(Environment.getExternalStorageDirectory())
    }
}