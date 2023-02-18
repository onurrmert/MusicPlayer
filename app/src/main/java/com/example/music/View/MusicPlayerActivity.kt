package com.example.music.View

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.music.R
import com.example.music.Util.MediaPlayerController
import com.example.music.databinding.ActivityMusicPlayerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MusicPlayerActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMusicPlayerBinding

    private var mediaPlayer : MediaPlayer? = null

    private var counter = 0

    private val mHandler: Handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initMediaPlayer()

        initSeekBar()

        seekBarChange()
    }

    private fun seekBarChange() {

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

               if (fromUser) mediaPlayer?.seekTo(progress.times(1000))

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }

    private fun initSeekBar() {

        binding.seekBar.max = mediaPlayer?.duration!!.div(1000)

        mHandler.postDelayed(object : Runnable{
            override fun run() {
                try {
                    binding.seekBar.setProgress(mediaPlayer?.currentPosition!!.div(1000))
                    mHandler.postDelayed(this, 1000)
                }catch (e : Exception){
                    binding.seekBar.setProgress(0)
                }
            }
        },0)
    }

    private fun initMediaPlayer(){

        mediaPlayer = MediaPlayer.create(this, getOpenUri())

        MediaPlayerController.mediaPlayer = mediaPlayer

        MediaPlayerController.start(this, getOpenUri())
    }

    override fun onResume() {
        super.onResume()
        btnClick()
    }

    private fun btnClick(){

        binding.btnPlayOrPause.setOnClickListener {

            if (counter % 2 == 0){

                MediaPlayerController.pause(this, getOpenUri())
                binding.btnPlayOrPause.setImageResource(R.drawable.ic_start)
                counter++

            }else{

                MediaPlayerController.start(this, getOpenUri())
                binding.btnPlayOrPause.setImageResource(R.drawable.ic_pause)
                counter++
            }
        }

        binding.btnStop.setOnClickListener {
            MediaPlayerController.stop()
            counter--
            binding.btnPlayOrPause.setImageResource(R.drawable.ic_start)
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