package com.example.music.ViewModel

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.music.Util.CheckPermission
import com.example.music.Util.IFindMusic
import java.io.File

class CurrentViewModel () : ViewModel() {

    val musicList = MutableLiveData<ArrayList<File>>()

    private lateinit var checkPermission: CheckPermission

    fun checkPermission(findMusic: IFindMusic, context: Context){
        checkPermission = CheckPermission(findMusic)
        checkPermission.checkPermissions(context)
    }

    fun getMusic(lifecycleOwner: LifecycleOwner){
        checkPermission.musicList.observe(lifecycleOwner, {
            musicList.value = it
        })
    }
}