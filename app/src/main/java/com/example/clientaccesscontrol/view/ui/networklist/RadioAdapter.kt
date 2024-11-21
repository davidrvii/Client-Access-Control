package com.example.clientaccesscontrol.view.ui.networklist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clientaccesscontrol.data.response.RadiosItem
import com.example.clientaccesscontrol.databinding.NetworkListBinding

class RadioAdapter(private var listRadioAdapter: List<RadiosItem>) :
    RecyclerView.Adapter<RadioAdapter.ViewHolder>() {

    class ViewHolder(private val binding: NetworkListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RadiosItem) {
            binding.tvNetworkName.text = item.type.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = NetworkListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listRadioAdapter.subList(1, listRadioAdapter.size)[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listRadioAdapter.size - 1

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: List<RadiosItem>) {
        listRadioAdapter = newList
        notifyDataSetChanged()
    }
}