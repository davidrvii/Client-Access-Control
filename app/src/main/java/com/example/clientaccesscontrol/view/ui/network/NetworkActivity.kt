package com.example.clientaccesscontrol.view.ui.network

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clientaccesscontrol.R
import com.example.clientaccesscontrol.databinding.ActivityNetworkBinding
import com.example.clientaccesscontrol.databinding.CustomAddDialogBinding
import com.example.clientaccesscontrol.view.ui.home.MainActivity

class NetworkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNetworkBinding
    private lateinit var bindingDialog: CustomAddDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNetworkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}