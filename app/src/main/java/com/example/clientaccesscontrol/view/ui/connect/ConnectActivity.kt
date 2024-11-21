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
import com.example.clientaccesscontrol.view.ui.home.MainVM
import com.example.clientaccesscontrol.view.utils.FactoryVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConnectActivity : AppCompatActivity() {

    private val connectViewModel by viewModels<ConnectVM> {
        FactoryVM.getInstance(this)
    }
    private val session by viewModels<MainVM> {
        FactoryVM.getInstance(this)
    }
    private lateinit var binding: ActivityConnectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()

        session.getSession().observe(this) { user ->
            if (user.isLogin) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                setupUI()
            }
        }

    }

    private fun setupUI() {
        binding = ActivityConnectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val ipAddress = binding.etAddress.text.toString().trim()
            lifecycleScope.launch(Dispatchers.IO) {
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
        try {
            withContext(Dispatchers.Main) {
                loginSuccess()
            }
        } catch (e: Exception) {
            Log.e("ConnectActivity", "Login failed: ${e.message}")
            if (e.message?.contains("Invalid IP Address") == true) {
                try {
                    connectViewModel.register(username, password, ipAddress)
                    try {
                        connectViewModel.login(ipAddress, username, password)
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
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun loginFailed() {
        Toast.makeText(this, "Login gagal, silahkan coba lagi", Toast.LENGTH_SHORT).show()
    }
}