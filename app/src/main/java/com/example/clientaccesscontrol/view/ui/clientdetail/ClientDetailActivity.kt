package com.example.clientaccesscontrol.view.ui.clientdetail

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clientaccesscontrol.R
import com.example.clientaccesscontrol.data.result.Results
import com.example.clientaccesscontrol.databinding.ActivityClientDetailBinding
import com.example.clientaccesscontrol.databinding.CustomDeleteDialogBinding
import com.example.clientaccesscontrol.view.ui.editrouter.EditRouterActivity
import com.example.clientaccesscontrol.view.utils.FactoryVM

class ClientDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientDetailBinding
    private lateinit var bindingDialog: CustomDeleteDialogBinding
    private val clientDetailViewModel by viewModels<ClientDetailVM> {
        FactoryVM.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityClientDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupActionButton()
        setupActionSpinner()
    }

    private fun setupActionSpinner() {
        clientDetailViewModel.getAccess.observe(this) { result ->
            when (result) {
                is Results.Success -> {
                    val accessList =
                        result.data.access?.mapNotNull { it?.internetAccess } ?: emptyList()
                    val accessAdapter = ArrayAdapter(this, R.layout.spinner_dropdown_item, accessList)
                    accessAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
                    binding.spInternetAccess.adapter = accessAdapter
                }

                is Results.Error -> Toast.makeText(
                    this,
                    "Error: ${result.error}",
                    Toast.LENGTH_SHORT
                ).show()

                is Results.Loading -> {}
            }

            binding.spInternetAccess.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: android.view.View?,
                        position: Int,
                        id: Long,
                    ) {
                        val selectedSize = parent?.getItemAtPosition(position).toString()
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                }
        }

        clientDetailViewModel.getSpeed.observe(this) { result ->
            when (result) {
                is Results.Success -> {
                    val speedList =
                        result.data.speed?.mapNotNull { it?.internetSpeed } ?: emptyList()
                    val speedAdapter = ArrayAdapter(this, R.layout.spinner_dropdown_item, speedList)
                    speedAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
                    binding.spInternetSpeed.adapter = speedAdapter
                }

                is Results.Error -> Toast.makeText(
                    this,
                    "Error: ${result.error}",
                    Toast.LENGTH_SHORT
                ).show()

                is Results.Loading -> {}
            }

            binding.spInternetSpeed.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: android.view.View?,
                        position: Int,
                        id: Long,
                    ) {
                        val selectedSize = parent?.getItemAtPosition(position).toString()
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {}
                }
        }
    }

    private fun setupActionButton() {
        binding.btDelete.setOnClickListener {
            showCustomDialog()
        }
        binding.btBack.setOnClickListener {
            finish()
        }
        binding.btEdit.setOnClickListener {
            val intent = Intent(this, EditRouterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showCustomDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        bindingDialog = CustomDeleteDialogBinding.inflate(layoutInflater)
        dialog.setContentView(bindingDialog.root)
        dialog.setCancelable(true)
        val height = WindowManager.LayoutParams.WRAP_CONTENT
        val width = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window?.setLayout(width, height)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        bindingDialog.btYesDelete.setOnClickListener {
            dialog.dismiss()
        }
        bindingDialog.btCancelDelete.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}