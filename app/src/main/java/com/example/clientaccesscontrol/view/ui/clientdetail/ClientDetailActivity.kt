package com.example.clientaccesscontrol.view.ui.clientdetail

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
    private var clientId: Int = 0
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

        clientId = intent.getIntExtra(CLIENT_ID, 0)
        Log.d("ClientDetailActivity", "Client ID: $clientId")
        setupActionButton()
        setupActionSpinner()
        clientDetail()
    }

    private fun clientDetail() {
        clientDetailViewModel.getClientDetail(clientId)
        Log.d("ClientDetailActivity", "Get Client Detail")
        clientDetailViewModel.getClientDetail.observe(this) { result ->
            Log.d("ClientDetailActivity", "Get Client Detail: $result")
            when (result) {
                is Results.Success -> {
                    val clientDetail = result.data.clientDetail?.firstOrNull()
                    Log.d("ClientDetailActivity", "Client Detail: ${result.data.clientDetail}")
                    if (clientDetail != null) {
                        binding.tvClientName.text = clientDetail.name
                        binding.tvPhone.text = clientDetail.phone
                        binding.tvAddress.text = clientDetail.address
                        binding.tvClientBTS.text = clientDetail.bts
                        binding.tvClientMode.text = clientDetail.mode
                        binding.tvClientSSID.text = clientDetail.ssid
                        binding.tvClientSSID.text = clientDetail.ssid
                        binding.tvClientIPInternet.text = clientDetail.ipAddress
                        binding.tvClientRadio.text = clientDetail.type
                        binding.tvClientRadioName.text = clientDetail.radioName
                        binding.tvClientFrequency.text = clientDetail.frequency
                        binding.tvClientChannelWidth.text = clientDetail.channelWidth
                        binding.tvClientSignal.text = clientDetail.radioSignal
                        binding.tvClientPresharedKey.text = clientDetail.presharedKey
                        binding.tvClientAPLocation.text = clientDetail.apLocation
                        binding.tvClientWLANMacAddress.text = clientDetail.wlanMacAddress
                        binding.tvClientUserPassword.text = clientDetail.password
                        binding.tvClientComment.text = clientDetail.comment
                    }
                }
                is Results.Error -> { }
                is Results.Loading -> { }
            }
        }
    }

    private fun setupActionSpinner() {
        clientDetailViewModel.getAccess.observe(this) { result ->
            when (result) {
                is Results.Success -> {
                    val accessList =
                        result.data.access?.mapNotNull { it?.internetAccess } ?: emptyList()
                    val accessAdapter =
                        ArrayAdapter(this, R.layout.spinner_dropdown_item, accessList)
                    accessAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
                    binding.spInternetAccess.adapter = accessAdapter

                    if (accessList.isNotEmpty()) {
                        clientDetailViewModel.getClientDetail.observe(this) { accessResult ->
                            when (accessResult) {
                                is Results.Success -> {
                                    val clientDetail = accessResult.data.clientDetail?.firstOrNull()
                                    if (clientDetail != null) {
                                        binding.spInternetAccess.setSelection(accessList.indexOf(clientDetail.internetAccess))
                                    }
                                }
                                is Results.Error -> { }
                                is Results.Loading -> { }
                            }
                        }
                    }
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

                    if (speedList.isNotEmpty()) {
                        clientDetailViewModel.getClientDetail.observe(this) { speedResult ->
                            when (speedResult) {
                                is Results.Success -> {
                                    val clientDetail = speedResult.data.clientDetail?.firstOrNull()
                                    if (clientDetail != null) {
                                        binding.spInternetSpeed.setSelection(speedList.indexOf(clientDetail.internetSpeed))
                                    }
                                }
                                is Results.Error -> { }
                                is Results.Loading -> { }
                            }
                        }
                    }
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

    companion object {
        const val CLIENT_ID = "CLIENT_ID"
    }
}