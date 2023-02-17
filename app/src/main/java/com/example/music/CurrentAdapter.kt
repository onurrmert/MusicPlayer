package com.example.music

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.music.Model.MusicModel
import com.example.music.databinding.CurrentRecyclerRowBinding
import java.io.File

class CurrentAdapter (
    private val musicFileList:ArrayList<File>,
    private val musicList:ArrayList<MusicModel>,
) : RecyclerView.Adapter<CurrentAdapter.CurrenViewHolder>(){

    class CurrenViewHolder(val layout: View) : RecyclerView.ViewHolder(layout){
        val binding = CurrentRecyclerRowBinding.bind(layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrenViewHolder {

        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.current_recycler_row, parent, false)

        return CurrenViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    override fun onBindViewHolder(holder: CurrenViewHolder, position: Int) {

        holder.binding.textName.setText(musicList.get(position).musicName)

        holder.binding.textModified.setText("tarih")
    }
}