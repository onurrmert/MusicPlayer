package com.example.music.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.music.Util.FindMusic
import com.example.music.Util.IFindMusic
import com.example.music.ViewModel.CurrentViewModel
import com.example.music.databinding.ActivityCurrentBinding

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

        viewModel.getMusic(findMusic)

        getMusic()
    }

    private fun getMusic(){
        viewModel.musicList.observe(this, Observer {
            it.forEach {
                println(it.musicName)
            }
        })
    }
}