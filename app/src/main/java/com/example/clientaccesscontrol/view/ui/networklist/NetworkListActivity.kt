package com.example.clientaccesscontrol.view.ui.networklist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientaccesscontrol.R
import com.example.clientaccesscontrol.data.result.Results
import com.example.clientaccesscontrol.databinding.ActivityNetworkListBinding
import com.example.clientaccesscontrol.view.ui.network.NetworkActivity
import com.example.clientaccesscontrol.view.utils.FactoryVM

class NetworkListActivity : AppCompatActivity() {

    private val networkListViewModel by viewModels<NetworkListVM> {
        FactoryVM.getInstance(this)
    }
    private lateinit var binding: ActivityNetworkListBinding
    private lateinit var btsAdapter: BTSAdapter
    private lateinit var radioAdapter: RadioAdapter
    private lateinit var modeAdapter: ModeAdapter
    private lateinit var channelWidthAdapter: ChannelWidthAdapter
    private lateinit var presharedKeyAdapter: PresharedKeyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNetworkListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val network = intent.getStringExtra("network")
        binding.tvTopbar.text = network

        binding.btBack.setOnClickListener {
            val intent = Intent(this, NetworkActivity::class.java)
            startActivity(intent)
            finish()
        }

        when (network) {
            "BTS" -> {
                networkListViewModel.getBTS.observe(this) { result ->
                    when (result) {
                        is Results.Success -> btsAdapter.updateData(result.data.bts?.filterNotNull() ?: emptyList())
                        is Results.Error -> Log.d("NetworkListActivity", "Error: ${result.error}")
                        is Results.Loading -> {}
                    }
                }
                setupBTSRecyclerView()
            }

            "Radio" -> {
                networkListViewModel.getRadio.observe(this) { result ->
                    when (result) {
                        is Results.Success -> radioAdapter.updateData(result.data.radios?.filterNotNull() ?: emptyList())
                        is Results.Error -> Log.d("NetworkListActivity", "Error: ${result.error}")
                        is Results.Loading -> {}
                    }
                }
                setupRadioRecyclerView()
            }

            "Mode" -> {
                networkListViewModel.getMode.observe(this) { result ->
                    when (result) {
                        is Results.Success -> modeAdapter.updateData(result.data.modes?.filterNotNull() ?: emptyList())
                        is Results.Error -> Log.d("NetworkListActivity", "Error: ${result.error}")
                        is Results.Loading -> {}
                    }
                }
                setupModeRecyclerView()
            }

            "Channel Width" -> {
                networkListViewModel.getChannelWidth.observe(this) { result ->
                    when (result) {
                        is Results.Success -> channelWidthAdapter.updateData(result.data.channelWidths?.filterNotNull() ?: emptyList())
                        is Results.Error -> Log.d("NetworkListActivity", "Error: ${result.error}")
                        is Results.Loading -> {}
                    }
                }
                setupChannelWidthRecyclerView()
            }

            "Preshared Key" -> {
                networkListViewModel.getPresharedKey.observe(this) { result ->
                    when (result) {
                        is Results.Success -> presharedKeyAdapter.updateData(result.data.presharedKeys?.filterNotNull() ?: emptyList())
                        is Results.Error -> Log.d("NetworkListActivity", "Error: ${result.error}")
                        is Results.Loading -> {}
                    }
                }
                setupPresharedKeyRecyclerView()
            }
        }
    }

    private fun setupBTSRecyclerView() {
        btsAdapter = BTSAdapter(emptyList())
        binding.rvNetworkList.apply {
            adapter = btsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupRadioRecyclerView() {
        radioAdapter = RadioAdapter(emptyList())
        binding.rvNetworkList.apply {
            adapter = radioAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupModeRecyclerView() {
        modeAdapter = ModeAdapter(emptyList())
        binding.rvNetworkList.apply {
            adapter = modeAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupChannelWidthRecyclerView() {
        channelWidthAdapter = ChannelWidthAdapter(emptyList())
        binding.rvNetworkList.apply {
            adapter = channelWidthAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupPresharedKeyRecyclerView() {
        presharedKeyAdapter = PresharedKeyAdapter(emptyList())
        binding.rvNetworkList.apply {
            adapter = presharedKeyAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}