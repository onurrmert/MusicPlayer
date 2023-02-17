package com.example.music.ViewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.music.Model.MusicModel
import com.example.music.Util.CheckPermission
import com.example.music.Util.IFindMusic

class CurrentViewModel () : ViewModel() {

    val musicList = MutableLiveData<ArrayList<MusicModel>>()

    fun checkPermission(findMusic: IFindMusic, context: Context){
        val checkPermission = CheckPermission(findMusic)
        checkPermission.checkPermissions(context)
    }

    fun getMusic(findMusic: IFindMusic,){
        musicList.value = findMusic.getMusic()
    }
}