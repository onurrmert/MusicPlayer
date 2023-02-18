package com.example.music.View

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.music.Adaper.CurrentAdapter
import com.example.music.Adaper.IOnItemClickListener
import com.example.music.Model.MusicModel
import com.example.music.R
import com.example.music.Util.FindMusic
import com.example.music.Util.IFindMusic
import com.example.music.Util.MediaPlayerController
import com.example.music.ViewModel.CurrentViewModel
import com.example.music.databinding.ActivityCurrentBinding
import java.io.File
import java.text.FieldPosition

class CurrentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCurrentBinding

    private lateinit var findMusic: IFindMusic

    private val viewModel by lazy {
        ViewModelProvider(this, defaultViewModelProviderFactory).get(CurrentViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initVM()
    }

    private fun initVM(){

        findMusic = FindMusic()

        viewModel.checkPermission(findMusic, applicationContext)

        viewModel.getMusic(this)

        getMusic()
    }

    private fun getMusic(){
        viewModel.musicList.observe(this, {
            if (it.size > 0){
                initRecycler(it)
            }
        })
    }

    private fun initRecycler(musicFileList : ArrayList<File>){

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.recyclerView.adapter = CurrentAdapter(

            getMusicList(musicFileList),

            object: IOnItemClickListener{

                override fun onItemClick(item: MusicModel, position: Int) {

                    closeCurrentMusic()

                    //openMusicPlayerActivity(item.musicUri!!)
                }
            })
    }

    private fun getMusicList(musicFileList: ArrayList<File>) : ArrayList<MusicModel>{

        val musicList = ArrayList<MusicModel>()

        musicFileList.forEach {
            musicList.add(MusicModel( Uri.fromFile(it), it.name))
        }

        return musicList
    }

    private fun openMusicPlayerActivity(uri: Uri){
        val intent = Intent(this@CurrentActivity, MusicPlayerActivity::class.java)
        intent.putExtra("musicUri1", uri.toString())
        startActivity(intent)
        overridePendingTransition(R.anim.rigthtoleft1, R.anim.rigthtoleft2)
        finish()
    }

    private fun closeCurrentMusic(){
        MediaPlayerController.mediaPlayer?.stop()
    }
}