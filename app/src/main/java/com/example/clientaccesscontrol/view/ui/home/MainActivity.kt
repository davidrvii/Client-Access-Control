package com.example.clientaccesscontrol.view.ui.home

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clientaccesscontrol.R
import com.example.clientaccesscontrol.databinding.ActivityMainBinding
import com.example.clientaccesscontrol.databinding.CustomLogoutDialogBinding
import com.example.clientaccesscontrol.view.ui.network.NetworkActivity
import com.example.clientaccesscontrol.view.ui.clientdetail.ClientDetailActivity
import com.example.clientaccesscontrol.view.ui.filter.FilterBottomSheet
import com.example.clientaccesscontrol.view.ui.newclientprofile.NewClientProfileActivity
import com.example.clientaccesscontrol.view.utils.FactoryVM

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bindingDialog: CustomLogoutDialogBinding
    private val mainViewModel by viewModels<MainVM> {
        FactoryVM.getInstance(this)
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

        binding.cvClient.setOnClickListener {
            val intent = Intent(this, ClientDetailActivity::class.java)
            startActivity(intent)
        }
    }

    private fun buttonMenuAction() {
        binding.btMenu.setOnClickListener {
            onButtonMenuClicked()
        }

        binding.btNetwork.setOnClickListener {
            val intent = Intent(this, NetworkActivity::class.java)
            startActivity(intent)
        }

        binding.btLogout.setOnClickListener {
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
        val height = WindowManager.LayoutParams.WRAP_CONTENT
        val width = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window?.setLayout(width, height)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        bindingDialog.btYesLogout.setOnClickListener {
            dialog.dismiss()
            mainViewModel.logout()
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