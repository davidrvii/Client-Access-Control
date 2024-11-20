package com.example.clientaccesscontrol.view.ui.newclientprofile

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clientaccesscontrol.R
import com.example.clientaccesscontrol.databinding.ActivityNewClientProfileBinding
import com.example.clientaccesscontrol.view.ui.home.MainActivity
import com.example.clientaccesscontrol.view.ui.newclientqueue.NewClientQueueActivity

class NewClientProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewClientProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNewClientProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btNext.setOnClickListener {
            val intent = Intent(this, NewClientQueueActivity::class.java)
            startActivity(intent)
        }

        binding.btBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}