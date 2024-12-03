package com.example.clientaccesscontrol.view.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clientaccesscontrol.data.response.AllClientItem
import com.example.clientaccesscontrol.databinding.ClientListBinding
import com.example.clientaccesscontrol.view.ui.clientdetail.ClientDetailActivity

class ClientAdapter(private var listClient: List<AllClientItem>) :
    RecyclerView.Adapter<ClientAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ClientListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AllClientItem) {
            binding.tvClientName.text = item.name
            binding.tvClientIPAddress.text = item.ipAddress

            binding.root.setOnClickListener {
                val intent = Intent(itemView.context, ClientDetailActivity::class.java).apply {
                    putExtra(ClientDetailActivity.CLIENT_ID, item.clientId)
                }
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ClientListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listClient[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listClient.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList: List<AllClientItem>) {
        listClient = newList
        notifyDataSetChanged()
    }
}