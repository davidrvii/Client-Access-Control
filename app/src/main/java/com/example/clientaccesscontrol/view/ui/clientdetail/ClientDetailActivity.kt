package com.example.clientaccesscontrol.view.ui.clientdetail

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clientaccesscontrol.R
import com.example.clientaccesscontrol.databinding.ActivityClientDetailBinding
import com.example.clientaccesscontrol.databinding.CustomDeleteDialogBinding
import com.example.clientaccesscontrol.view.ui.editrouter.EditRouterActivity
import com.example.clientaccesscontrol.view.ui.home.MainActivity

class ClientDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientDetailBinding
    private lateinit var bindingDialog: CustomDeleteDialogBinding

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

        binding.btDelete.setOnClickListener {
            showCustomDialog()
        }

        binding.btBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
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