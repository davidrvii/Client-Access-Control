package com.example.clientaccesscontrol.view.ui.networklist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clientaccesscontrol.data.response.ChannelWidthsItem
import com.example.clientaccesscontrol.databinding.NetworkListBinding

class ChannelWidthAdapter(private var listChannelWidthAdapter: List<ChannelWidthsItem>) :
    RecyclerView.Adapter<ChannelWidthAdapter.ViewHolder>() {

    class ViewHolder(private val binding: NetworkListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChannelWidthsItem) {
            binding.tvNetworkName.text = item.channelWidth.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = NetworkListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listChannelWidthAdapter.subList(1, listChannelWidthAdapter.size)[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listChannelWidthAdapter.size - 1

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: List<ChannelWidthsItem>) {
        listChannelWidthAdapter = newList
        notifyDataSetChanged()
    }
}