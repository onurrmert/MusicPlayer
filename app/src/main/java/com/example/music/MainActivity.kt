package com.example.music

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.music.View.CurrentActivity
import com.example.music.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.Main).launch {
            goCurrent()
        }
    }

    private suspend fun goCurrent(){
        delay(1000)
        startActivity(Intent(this@MainActivity, CurrentActivity::class.java))
        overridePendingTransition(R.anim.rigthtoleft1, R.anim.rigthtoleft2)
        finish()
    }
}