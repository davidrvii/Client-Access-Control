package com.example.clientaccesscontrol.view.ui.editrouter

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clientaccesscontrol.R
import com.example.clientaccesscontrol.databinding.ActivityEditRouterBinding
import com.example.clientaccesscontrol.databinding.CustomSaveDialogBinding

class EditRouterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditRouterBinding
    private lateinit var bindingDialog: CustomSaveDialogBinding

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
        val height = WindowManager.LayoutParams.WRAP_CONTENT
        val width = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window?.setLayout(width, height)

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        bindingDialog.btYesLogout.setOnClickListener {
            dialog.dismiss()
        }
        bindingDialog.btCancelLogout.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}