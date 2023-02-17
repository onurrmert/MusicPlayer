package com.example.music.View

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.music.CurrentAdapter
import com.example.music.Model.MusicModel
import com.example.music.Util.FindMusic
import com.example.music.Util.IFindMusic
import com.example.music.ViewModel.CurrentViewModel
import com.example.music.databinding.ActivityCurrentBinding
import java.io.File

class CurrentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCurrentBinding

    private lateinit var findMusic: IFindMusic

    private lateinit var viewModel: CurrentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        findMusic = FindMusic()

        viewModel = ViewModelProvider(this, defaultViewModelProviderFactory).get(CurrentViewModel::class.java)

        viewModel.checkPermission(findMusic, applicationContext)

        viewModel.getMusic(this)

        getMusic()
    }

    private fun getMusic(){
        viewModel.musicList.observe(this, Observer {
            initRecycler(it)
        })
    }

    private fun initRecycler(musicFileList : ArrayList<File>){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = CurrentAdapter(musicFileList, getMusicList(musicFileList))
    }

    private fun getMusicList(musicFileList: ArrayList<File>) : ArrayList<MusicModel>{

        val musicList = ArrayList<MusicModel>()

        musicFileList.forEach {
            musicList.add(MusicModel( Uri.fromFile(it), it.name))
        }
        return musicList
    }
}