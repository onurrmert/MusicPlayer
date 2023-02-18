package com.example.music.View

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.music.R
import com.example.music.Util.SingletonMediaPlayer
import com.example.music.databinding.ActivityMusicPlayerBinding

class MusicPlayerActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMusicPlayerBinding

    private var mediaPlayer : MediaPlayer? = null

    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initMediaPlayer()
    }

    private fun initMediaPlayer(){

        mediaPlayer = MediaPlayer.create(this, getOpenUri())

        SingletonMediaPlayer.mediaPlayer = mediaPlayer

        SingletonMediaPlayer.start(this, getOpenUri())
    }

    override fun onResume() {
        super.onResume()
        btnClick()
    }

    private fun btnClick(){

        binding.btnPlayOrPause.setOnClickListener {

            if (counter % 2 == 0){

                SingletonMediaPlayer.pause(this, getOpenUri())
                binding.btnPlayOrPause.setImageResource(R.drawable.ic_started)
                counter++

            }else{

                SingletonMediaPlayer.start(this, getOpenUri())
                binding.btnPlayOrPause.setImageResource(R.drawable.ic_pause)
                counter++
            }
        }

        binding.btnStop.setOnClickListener {
            SingletonMediaPlayer.stop()
            counter--
            binding.btnPlayOrPause.setImageResource(R.drawable.ic_started)
        }
    }

    private fun getOpenUri() : Uri{
        return Uri.parse(intent.getStringExtra("musicUri1"))
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        openCurrentActivity()
    }

    private fun openCurrentActivity(){
        val intent = Intent(this, CurrentActivity::class.java)
        intent.putExtra("currentMusic", getOpenUri().toString())
        startActivity(intent)
        overridePendingTransition(R.anim.lefttorigth1, R.anim.lefttorigth2)
        finish()
    }
}