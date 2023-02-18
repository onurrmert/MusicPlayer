package com.example.music.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.music.Adaper.CurrentAdapter
import com.example.music.Adaper.IOnItemClickListener
import com.example.music.Model.MusicModel
import com.example.music.R
import com.example.music.Util.FindMusic
import com.example.music.Util.IFindMusic
import com.example.music.Util.MediaPlayerController
import com.example.music.Util.MusicList
import com.example.music.ViewModel.CurrentViewModel
import com.example.music.databinding.ActivityCurrentBinding
import java.util.*
import kotlin.collections.ArrayList

class CurrentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCurrentBinding

    private lateinit var findMusic: IFindMusic

    private val musicList = ArrayList<MusicModel>()

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

        search()
    }

    private fun getMusic(){
        viewModel.musicList.observe(this, {
            if (it.size > 0){

                musicList.addAll(MusicList.getMusiclist(it))

                initRecycler(MusicList.getMusiclist(it))
            }
        })
    }

    private fun search(){

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter1(newText!!)
                return true
            }
        })
    }

    private fun filter1(text : String){

        val filterList = ArrayList<MusicModel>()

        musicList.forEach {
            if (it.musicName!!.toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))){
                filterList.add(it)
            }
        }

        if (filterList.isEmpty()){
            Toast.makeText(this, "Music is not found", Toast.LENGTH_SHORT).show()
        }else{
            initRecycler(filterList)
        }
    }


    private fun initRecycler(musicModelList : ArrayList<MusicModel>){

            val adapter = CurrentAdapter(

                musicModelList,

                object: IOnItemClickListener{

                    override fun onItemClick(item: MusicModel, position: Int) {

                        closeCurrentMusic()

                        openMusicPlayerActivity(musicList.indexOf(item))
                    }
                })

            binding.recyclerView.adapter = adapter

            binding.recyclerView.layoutManager = LinearLayoutManager(this)

            adapter.setFilterList(musicModelList)
    }

    private fun openMusicPlayerActivity(position: Int){
        val intent = Intent(this@CurrentActivity, MusicPlayerActivity::class.java)
        intent.putExtra("position", position)
        startActivity(intent)
        overridePendingTransition(R.anim.rigthtoleft1, R.anim.rigthtoleft2)
        finish()
    }

    private fun closeCurrentMusic(){
        MediaPlayerController.mediaPlayer?.stop()
    }
}