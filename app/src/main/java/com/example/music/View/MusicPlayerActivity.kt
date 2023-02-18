package com.example.music.View

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.music.R
import com.example.music.Util.FindMusic
import com.example.music.Util.MediaPlayerController
import com.example.music.Util.MusicList
import com.example.music.ViewModel.MusicPlayerViewModel
import com.example.music.databinding.ActivityMusicPlayerBinding


class MusicPlayerActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMusicPlayerBinding

    private val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory).get(MusicPlayerViewModel::class.java)
    }

    private var mediaPlayer : MediaPlayer? = null

    private var counterIcon = 0

    private var counterPositon = 0

    private val mHandler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val findMusic = FindMusic()

        viewModel.getMusicFile(findMusic)

        counterPositon = getPosition()

        getMusic(getPosition())
    }

    override fun onResume() {
        super.onResume()

        initSeekBar()

        seekBarChange()
    }

    private fun getMusic(position: Int){

        viewModel.musicList.observe(this) {

            val music = MusicList.getMusiclist(it).get(position)

            initMediaPlayer(music.musicUri!!)

            btnClick(music.musicUri, it.size)
        }
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

    private fun initMediaPlayer(uri: Uri){

        mediaPlayer = MediaPlayer.create(this, uri)

        MediaPlayerController.mediaPlayer = mediaPlayer

        MediaPlayerController.start(this, uri)
    }

    private fun btnClick(uri: Uri, listSize : Int){

        binding.btnPlayOrPause.setOnClickListener {

            if (counterIcon % 2 == 0){

                MediaPlayerController.pause(this, uri)
                binding.btnPlayOrPause.setImageResource(R.drawable.ic_start)
                counterIcon++

            }else{

                MediaPlayerController.start(this, uri)
                binding.btnPlayOrPause.setImageResource(R.drawable.ic_pause)
                counterIcon++
            }
        }

        binding.btnStop.setOnClickListener {
            MediaPlayerController.stop()
            counterIcon--
            binding.btnPlayOrPause.setImageResource(R.drawable.ic_start)
        }

        binding.btnNext.setOnClickListener {

            if (counterPositon < (listSize - 1)){

                mediaPlayer?.stop()
                getMusic(++counterPositon)
            }
        }

        binding.btnPrev.setOnClickListener {

            if (counterPositon > 0){

                mediaPlayer?.stop()
                getMusic(--counterPositon)
            }
        }
    }

    private fun getPosition() : Int{
        return intent.getIntExtra("position", 0)
    }

    override fun onBackPressed() {
        startActivity(Intent(this, CurrentActivity::class.java))
        overridePendingTransition(R.anim.lefttorigth1, R.anim.lefttorigth2)
        finish()
    }
}