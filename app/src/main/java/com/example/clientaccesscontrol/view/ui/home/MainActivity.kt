package com.example.clientaccesscontrol.view.ui.home

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clientaccesscontrol.R
import com.example.clientaccesscontrol.data.result.Results
import com.example.clientaccesscontrol.databinding.ActivityMainBinding
import com.example.clientaccesscontrol.databinding.CustomLogoutDialogBinding
import com.example.clientaccesscontrol.view.ui.connect.ConnectActivity
import com.example.clientaccesscontrol.view.ui.filter.FilterBottomSheet
import com.example.clientaccesscontrol.view.ui.network.NetworkActivity
import com.example.clientaccesscontrol.view.ui.newclientprofile.NewClientProfileActivity
import com.example.clientaccesscontrol.view.utils.FactoryViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingDialog: CustomLogoutDialogBinding
    private lateinit var clientAdapter: ClientAdapter
    private val mainViewModel by viewModels<MainVM> {
        FactoryViewModel.getInstance(this)
    }
    private var clicked = false

    //Button Menu Animation
    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim)
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim)
    }
    private val fromTop: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.from_top_anim)
    }
    private val toTop: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.to_top_anim)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mainViewModel.getSession()

        buttonMenuAction()
        searchBarAction()
        buttonNewClientAction()
        setupClientList()
    }

    private fun updateClientList() {
        Log.d("MainActivity", "Updating client list")

        val queueTrees = (mainViewModel.getQueueTree.value as? Results.Success)?.data
        Log.d("MainActivity", "Queue Trees: $queueTrees")

        val clients = (mainViewModel.getAllClient.value as? Results.Success)?.data?.clients
        Log.d("MainActivity", "Clients: $clients")

        if (clients != null && queueTrees != null) {
            Log.d("MainActivity", "Checking Client Data in Queue Tree Data")
            val queueTreeComments = queueTrees.map { it.comment }
            clients.forEach { client ->
                if (!queueTreeComments.contains(client?.comment)) {
                    mainViewModel.updateClient(client?.clientId!!, 3, client.speedId!!)
                    mainViewModel.updateClient.observe(this) { result ->
                        when (result) {
                            is Results.Success -> {
                                Log.d("MainActivity", "Client updated: ${client.clientId}")
                            }

                            is Results.Error -> {
                                Log.d("MainActivity", "Error updating client: ${result.error}")
                            }

                            is Results.Loading -> {
                                Log.d("MainActivity", "Loading client update...")
                            }
                        }
                    }
                } else {
                    mainViewModel.updateClient(client?.clientId!!, 2, client.speedId!!)
                }
            }
        }

        if (queueTrees != null && clients != null) {
            val clientComment = clients.map { it?.comment }
            queueTrees.forEach { queueTree ->
                if (!clientComment.contains(queueTree.comment)) {
                    Log.d("MainActivity", "Queue tree not found in client list: ${queueTree.comment}")
                }
            }
        }
    }

    private fun setupClientList() {
        mainViewModel.getAllClient.observe(this) { result ->
            when (result) {
                is Results.Success -> {
                    Log.d("MainActivity", "Received client data: ${result.data.clients}")
                    clientAdapter.updateData(result.data.clients?.filterNotNull() ?: emptyList())
                    updateClientList()
                }

                is Results.Error -> {
                    Log.d("MainActivity", "Error: ${result.error}")
                }

                is Results.Loading -> {
                    Log.d("MainActivity", "Loading client data...")
                }
            }
        }
        mainViewModel.getQueueTree.observe(this) { result ->
            when (result) {
                is Results.Success -> {
                    Log.d("MainActivity", "Received queue tree data: ${result.data}")
                }
                is Results.Error -> {
                    Log.d("MainActivity", "Error: ${result.error}")
                }
                is Results.Loading -> {
                    Log.d("MainActivity", "Loading queue tree data...")
                }
            }
        }
        setupClientRecyclerView()
    }

    private fun setupClientRecyclerView() {
        Log.d("MainActivity", "Setup RecyclerView")
        clientAdapter = ClientAdapter(emptyList())
        binding.rvClientList.apply {
            adapter = clientAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun buttonMenuAction() {
        binding.btMenu.setOnClickListener {
            onButtonMenuClicked()
        }

        binding.btNetwork.setOnClickListener {
            val intent = Intent(this, NetworkActivity::class.java)
            startActivity(intent)
            onButtonMenuClicked()
        }

        binding.btLogout.setOnClickListener {
            onButtonMenuClicked()
            showCustomDialog()
        }
    }

    private fun onButtonMenuClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            binding.btNetwork.visibility = View.VISIBLE
            binding.btLogout.visibility = View.VISIBLE
        } else {
            binding.btNetwork.visibility = View.INVISIBLE
            binding.btLogout.visibility = View.INVISIBLE
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            binding.btNetwork.startAnimation(fromTop)
            binding.btLogout.startAnimation(fromTop)
            binding.btMenu.startAnimation(rotateOpen)
        } else {
            binding.btNetwork.startAnimation(toTop)
            binding.btLogout.startAnimation(toTop)
            binding.btMenu.startAnimation(rotateClose)
        }
    }

    private fun showCustomDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        bindingDialog = CustomLogoutDialogBinding.inflate(layoutInflater)

        dialog.setContentView(bindingDialog.root)
        dialog.setCancelable(true)

        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val cardView = bindingDialog.root.findViewById<CardView>(R.id.LogoutCard)
        val layoutParams = cardView.layoutParams as ViewGroup.MarginLayoutParams
        val margin = (40 * resources.displayMetrics.density).toInt()
        layoutParams.setMargins(margin, 0, margin, 0)
        cardView.layoutParams = layoutParams

        bindingDialog.btYesLogout.setOnClickListener {
            dialog.dismiss()
            mainViewModel.logout()
            val intent = Intent(this, ConnectActivity::class.java)
            startActivity(intent)
            finish()
        }
        bindingDialog.btCancelLogout.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun searchBarAction() {
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    false
                }

            binding.searchBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.filter -> {
                        filterBottomSheet()
                    }
                }
                true
            }

        }
    }

    private fun buttonNewClientAction() {
        binding.btNewClient.setOnClickListener {
            val intent = Intent(this, NewClientProfileActivity::class.java)
            startActivity(intent)
            if (clicked) onButtonMenuClicked()
        }
    }

    private fun filterBottomSheet() {
//        val filterDialog = BottomSheetDialog(this)
//        val filterBinding = BottomSheetFilterBinding.inflate(layoutInflater)
//        filterDialog.apply {
//            setContentView(filterBinding.root)
//            setCancelable(true)
//            show()
//        }

        // Buat instance dari FilterBottomSheet
        val filterBottomSheet = FilterBottomSheet()

        // Tampilkan BottomSheetFragment
        filterBottomSheet.show(supportFragmentManager, filterBottomSheet.tag)
    }
}