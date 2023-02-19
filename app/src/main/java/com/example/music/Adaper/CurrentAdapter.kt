package com.example.music.Adaper

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.music.Model.MusicModel
import com.example.music.R
import com.example.music.databinding.CurrentRecyclerRowBinding

class CurrentAdapter (
    private var musicList:ArrayList<MusicModel>,
    private val listener: IOnItemClickListener
    ) : RecyclerView.Adapter<CurrentAdapter.CurrenViewHolder>(){

    class CurrenViewHolder(layout: View) : RecyclerView.ViewHolder(layout){
        val binding = CurrentRecyclerRowBinding.bind(layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrenViewHolder {

        return CurrenViewHolder(
            LayoutInflater
            .from(parent.context)
            .inflate(R.layout.current_recycler_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    override fun onBindViewHolder(holder: CurrenViewHolder, position: Int) {

        holder.binding.textName.setText(musicList.get(position).title)

        holder.binding.textModified.setText(musicList.get(position).artist)

        holder.binding.recyclerRow.setOnClickListener {
            listener.onItemClick(musicList.get(position), position)
        }

        holder.binding.textName.setOnClickListener {
            listener.onItemClick(musicList.get(position), position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFilterList(filterList: ArrayList<MusicModel>){
        musicList = filterList
        notifyDataSetChanged()
    }
}