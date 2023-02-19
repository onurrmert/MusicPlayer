package com.example.music.Util

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.example.music.Model.MusicModel
import java.io.File

class FindMusic : IFindMusic {

    @SuppressLint("Range")
    override fun getMusicFile(context: Context): ArrayList<MusicModel> {
        val contentResolver = context.contentResolver
        var audioList: ArrayList<MusicModel> = ArrayList()
        val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0"
        val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"
        val cursor: Cursor? = contentResolver.query(uri, null, selection, null, sortOrder)
        if (cursor != null && cursor.getCount() > 0) {
            audioList = ArrayList()
            while (cursor.moveToNext()) {
                val data: String = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                val title: String = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                val album: String = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                val artist: String = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                val songId: String = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                val albumId: String = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
                val artistId: String = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID))
                val uri = Uri.fromFile(File(data))

                audioList.add(MusicModel(data, title, album, artist, songId, albumId, artistId, uri))
            }
        }
        assert(cursor != null)
        if (cursor != null) {
            cursor.close()
        }
        return audioList
    }
}