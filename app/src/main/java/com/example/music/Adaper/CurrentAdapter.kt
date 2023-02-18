package com.example.music.Adaper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.music.Model.MusicModel
import com.example.music.R
import com.example.music.databinding.CurrentRecyclerRowBinding

class CurrentAdapter (
    private val musicList:ArrayList<MusicModel>,
    private val listener: IOnItemClickListener
    ) : RecyclerView.Adapter<CurrentAdapter.CurrenViewHolder>(){

    class CurrenViewHolder(layout: View) : RecyclerView.ViewHolder(layout){
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

        holder.binding.recyclerRow.setOnClickListener {
            listener.onItemClick(musicList.get(position))
        }

        holder.binding.textName.setOnClickListener {
            listener.onItemClick(musicList.get(position))
        }
    }
}