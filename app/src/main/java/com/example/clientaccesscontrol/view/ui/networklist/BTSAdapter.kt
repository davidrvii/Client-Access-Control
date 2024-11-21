package com.example.clientaccesscontrol.view.ui.networklist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clientaccesscontrol.data.response.BtsItem
import com.example.clientaccesscontrol.databinding.NetworkListBinding

class BTSAdapter(private var listBTSAdapter: List<BtsItem>):
RecyclerView.Adapter<BTSAdapter.ViewHolder>() {

    class ViewHolder(private val binding: NetworkListBinding):
    RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BtsItem) {
            binding.tvNetworkName.text = item.bts.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = NetworkListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listBTSAdapter.subList(1, listBTSAdapter.size)[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listBTSAdapter.size - 1

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: List<BtsItem>) {
        listBTSAdapter = newList
        notifyDataSetChanged()
    }
}