package com.example.clientaccesscontrol.view.ui.editrouter

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clientaccesscontrol.R
import com.example.clientaccesscontrol.data.result.Results
import com.example.clientaccesscontrol.databinding.ActivityEditRouterBinding
import com.example.clientaccesscontrol.databinding.CustomSaveDialogBinding
import com.example.clientaccesscontrol.view.utils.FactoryVM

class EditRouterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditRouterBinding
    private lateinit var bindingDialog: CustomSaveDialogBinding
    private val editRouterViewModel by viewModels<EditRouterVM> {
        FactoryVM.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEditRouterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupActionSpinner()
        setupActionButton()
    }

    private fun setupActionSpinner() {
        editRouterViewModel.getBTS.observe(this) { result ->
            when (result) {
                is Results.Success -> {
                    val btsList =
                        result.data.bts?.mapNotNull { it?.bts } ?: emptyList()
                    val btsAdapter = ArrayAdapter(this, R.layout.spinner_dropdown_item, btsList)
                    btsAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
                    binding.spBTS.adapter = btsAdapter
                }

                is Results.Error -> {
                    Toast.makeText(this, "Error: ${result.error}", Toast.LENGTH_SHORT)
                        .show()
                }

                is Results.Loading -> {}
            }

            binding.spBTS.onItemSelectedListener =
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

        editRouterViewModel.getMode.observe(this) { result ->
            when (result) {
                is Results.Success -> {
                    val modeList =
                        result.data.modes?.mapNotNull { it?.mode } ?: emptyList()
                    val modeAdapter = ArrayAdapter(this, R.layout.spinner_dropdown_item, modeList)
                    modeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
                    binding.spMode.adapter = modeAdapter
                }

                is Results.Error -> {
                    Toast.makeText(this, "Error: ${result.error}", Toast.LENGTH_SHORT)
                        .show()
                }

                is Results.Loading -> {}
            }

            binding.spMode.onItemSelectedListener =
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

        editRouterViewModel.getRadio.observe(this) { result ->
            when (result) {
                is Results.Success -> {
                    val radioList =
                        result.data.radios?.mapNotNull { it?.type } ?: emptyList()
                    val radioAdapter = ArrayAdapter(this, R.layout.spinner_dropdown_item, radioList)
                    radioAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
                    binding.spRadio.adapter = radioAdapter
                }

                is Results.Error -> {
                    Toast.makeText(this, "Error: ${result.error}", Toast.LENGTH_SHORT)
                        .show()
                }

                is Results.Loading -> {}
            }

            binding.spRadio.onItemSelectedListener =
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

        editRouterViewModel.getChannelWidth.observe(this) { result ->
            when (result) {
                is Results.Success -> {
                    val channelWidthList =
                        result.data.channelWidths?.mapNotNull { it?.channelWidth } ?: emptyList()
                    val channelWidthAdapter =
                        ArrayAdapter(this, R.layout.spinner_dropdown_item, channelWidthList)
                    channelWidthAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
                    binding.spChannelWidth.adapter = channelWidthAdapter
                }

                is Results.Error -> {
                    Toast.makeText(this, "Error: ${result.error}", Toast.LENGTH_SHORT)
                        .show()
                }

                is Results.Loading -> {}
            }

            binding.spChannelWidth.onItemSelectedListener =
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

        editRouterViewModel.getPresharedKey.observe(this) { result ->
            when (result) {
                is Results.Success -> {
                    val presharedKeyList =
                        result.data.presharedKeys?.mapNotNull { it?.presharedKey } ?: emptyList()
                    val presharedKeyAdapter =
                        ArrayAdapter(this, R.layout.spinner_dropdown_item, presharedKeyList)
                    presharedKeyAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
                    binding.spPresharedKey.adapter = presharedKeyAdapter
                }

                is Results.Error -> {
                    Toast.makeText(this, "Error: ${result.error}", Toast.LENGTH_SHORT)
                        .show()
                }

                is Results.Loading -> {}
            }

            binding.spPresharedKey.onItemSelectedListener =
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
        binding.btSave.setOnClickListener {
            showCustomDialog()
        }

        binding.btBack.setOnClickListener {
            finish()
        }
    }

    private fun showCustomDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        bindingDialog = CustomSaveDialogBinding.inflate(layoutInflater)

        dialog.setContentView(bindingDialog.root)
        dialog.setCancelable(true)

        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val cardView = bindingDialog.root.findViewById<CardView>(R.id.SaveCard)
        val layoutParams = cardView.layoutParams as ViewGroup.MarginLayoutParams
        val margin = (40 * resources.displayMetrics.density).toInt()
        layoutParams.setMargins(margin, 0, margin, 0)
        cardView.layoutParams = layoutParams

        bindingDialog.btYesSave.setOnClickListener {
            finish()
            dialog.dismiss()
        }
        bindingDialog.btCancelSave.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}