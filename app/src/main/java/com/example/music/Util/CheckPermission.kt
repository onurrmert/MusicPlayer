package com.example.music.Util

import android.content.Context
import android.os.Environment
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.music.Model.MusicModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.io.File

class CheckPermission(
    private val findMusic: IFindMusic
) {

    val musicList = MutableLiveData<ArrayList<File>>()

    fun checkPermissions(context: Context){

        Dexter.withContext(context)
            .withPermission(
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener{
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {

                    try {
                        musicList.value = findMusic.getMusicFile(Environment.getExternalStorageDirectory())
                    }catch (e : Exception){
                        println("error onPermissionGranted: " + e.localizedMessage)
                    }
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Toast.makeText(context, "Permission is required", Toast.LENGTH_SHORT).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    p1?.continuePermissionRequest()
                }

            }).check()
    }

}