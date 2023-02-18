package com.example.music.View

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.music.Model.MusicModel
import com.example.music.R
import com.example.music.Util.FindMusic
import com.example.music.Util.MediaPlayerController
import com.example.music.Util.MusicList
import com.example.music.ViewModel.MusicPlayerViewModel
import com.example.music.databinding.ActivityMusicPlayerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MusicPlayerActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMusicPlayerBinding

    private lateinit var mediaPlayer : MediaPlayer

    private lateinit var context: Context

    private lateinit var uri : Uri

    private var counterIcon = 0

    private var counterPositon = 0

    private val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory).get(MusicPlayerViewModel::class.java)
    }

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

        CoroutineScope(Dispatchers.Main).launch {
            initSeekBar()
        }
        seekBarChange()
    }

    private fun getMusic(position: Int){

        viewModel.musicList.observe(this) {

            val music = MusicList.getMusiclist(it).get(position)

            context = this

            music.musicUri?.let { uri = it }

            music.musicUri?.let { it1 -> initMediaPlayer() }

            btnClick(it.size)

            initText(music)
        }
    }

    private fun initMediaPlayer(){

        mediaPlayer = MediaPlayer.create(this, uri)

        MediaPlayerController.mediaPlayer = mediaPlayer

        MediaPlayerController.start()

        startService(Intent(this, MediaPlayerController::class.java))
    }

    private fun initText(musicModel: MusicModel){

        binding.textMusicName.text = musicModel.musicName!!

        binding.textDuration.text = mediaPlayer.duration.let { timestampToMSS(it) }

        CoroutineScope(Dispatchers.Main).launch {
            currentTextTime()
        }
    }

    @SuppressLint("SetTextI18n")
    private suspend fun currentTextTime() {
        while (true){
            delay(1000)
            binding.textStart.text = timestampToMSS(mediaPlayer.currentPosition)
        }
    }

    private fun seekBarChange() {

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                if (fromUser) mediaPlayer.seekTo(progress.times(1000))
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private suspend fun initSeekBar() {

        binding.seekBar.max = mediaPlayer.duration.div(1000)

        while (true){
            delay(1000)
            binding.seekBar.setProgress(mediaPlayer.currentPosition.div(1000))
        }
    }

    private fun btnClick(listSize : Int){

        binding.btnPlayOrPause.setOnClickListener {

            if (counterIcon % 2 == 0){
                MediaPlayerController.pause()
                binding.btnPlayOrPause.setImageResource(R.drawable.ic_start)
                counterIcon++

            }else{

                stopService(Intent(this, MediaPlayerController::class.java))
                MediaPlayerController.start()
                binding.btnPlayOrPause.setImageResource(R.drawable.ic_pause)
                counterIcon++
            }
        }

        binding.btnNext.setOnClickListener {

            if (counterPositon < (listSize - 1)){
                binding.btnPlayOrPause.setImageResource(R.drawable.ic_pause)
                counterIcon++
                mediaPlayer.stop()
                getMusic(++counterPositon)
            }
        }

        binding.btnPrev.setOnClickListener {

            if (counterPositon > 0){
                binding.btnPlayOrPause.setImageResource(R.drawable.ic_pause)
                counterIcon++
                mediaPlayer.stop()
                getMusic(--counterPositon)
            }
        }

        onCompleteMusic(listSize)
    }

    private fun onCompleteMusic(listSize: Int){

        if (counterPositon < (listSize -1)){

            mediaPlayer.setOnCompletionListener {
                getMusic(++counterPositon)
            }
        }else{
            mediaPlayer.isLooping = true
        }
    }

    private fun getPosition() : Int{
        return intent.getIntExtra("position", 0)
    }

    fun timestampToMSS(position: Int): String {
        val totalSeconds = Math.floor(position / 1E3).toInt()
        val minutes = totalSeconds / 60
        val remainingSeconds = totalSeconds - (minutes * 60)
        return (minutes.toString() + ":" + remainingSeconds.toString())
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        startActivity(Intent(this, CurrentActivity::class.java))
        overridePendingTransition(R.anim.lefttorigth1, R.anim.lefttorigth2)
        finish()
    }
}