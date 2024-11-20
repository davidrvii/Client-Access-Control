package com.example.clientaccesscontrol.view.ui.connect

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.clientaccesscontrol.R
import com.example.clientaccesscontrol.databinding.ActivityConnectBinding
import com.example.clientaccesscontrol.view.ui.home.MainActivity
import com.example.clientaccesscontrol.view.utils.FactoryVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConnectActivity : AppCompatActivity() {

    private val connectViewModel by viewModels<ConnectVM> {
        FactoryVM.getInstance(this)
    }
    private lateinit var binding: ActivityConnectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        binding = ActivityConnectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Log.d("ConnectActivity", "viewmodel called: $connectViewModel")

        setupAction()
    }

    private fun setupAction() {
        binding.etUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                buttonSet()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                buttonSet()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        binding.etAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                buttonSet()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btConnect.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            val ipAddress = binding.etAddress.text.toString()
            Log.d("ConnectActivity", "Button Connect clicked with username: $username, password: $password, ipAddress: $ipAddress")
            lifecycleScope.launch(Dispatchers.IO) {
                Log.d("ConnectActivity", "Coroutine launched")
                loginProcess(username, password, ipAddress)
            }
        }
    }

    private fun buttonSet() {
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()
        val ipAddress = binding.etAddress.text.toString()

        val isFieldFilled = username.isNotEmpty() && password.isNotEmpty() && ipAddress.isNotEmpty()

        binding.btConnect.isEnabled = isFieldFilled
    }

    private suspend fun loginProcess(username: String, password: String, ipAddress: String) {
        connectViewModel.login(ipAddress, username, password)
        Log.d("ConnectActivity", "Login process started")
        try {
            Log.d("ConnectActivity", "Login successful")
            withContext(Dispatchers.Main) {
                loginSuccess()
            }
        } catch (e: Exception) {
            Log.e("Login Error", "${e.message}")
            Log.e("ConnectActivity", "Login failed: ${e.message}")
            if (e.message?.contains("Invalid IP Address") == true) {
                try {
                    Log.d("ConnectActivity", "Trying to register")
                    connectViewModel.register(username, password, ipAddress)
                    Log.d("ConnectActivity", "Attempting to register")
                    try {
                        connectViewModel.login(ipAddress, username, password)
                        Log.d("ConnectActivity", "Login successful")
                        withContext(Dispatchers.Main) {
                            loginSuccess()
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            loginFailed()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        loginFailed()
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    loginFailed()
                }
            }
        }
    }

    private fun loginSuccess() {
        Log.d("ConnectActivity", "Navigating to MainActivity")
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun loginFailed() {
        Log.d("ConnectActivity", "Login failed, showing toast")
        Toast.makeText(this, "Login gagal, silahkan coba lagi", Toast.LENGTH_SHORT).show()
    }
}