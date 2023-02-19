package com.example.music.Model

import android.net.Uri

data class MusicModel(
    val data    : String,
    val title   : String,
    val album   : String,
    val artist  : String,
    val songId  : String,
    val albumId : String,
    val artistId: String,
    val uri: Uri
)
