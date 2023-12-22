package com.example.dishdash.ui.WelcomeActivity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import com.example.dishdash.MainActivity
import com.example.dishdash.R
import com.example.dishdash.data.Result
import com.example.dishdash.data.UserModel
import com.example.dishdash.data.ViewModelFactory
import com.example.dishdash.databinding.ActivityLoginBinding
import com.example.dishdash.ui.viewModel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.btnLogin.setOnClickListener {
            setupAction()
        }
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupAction() {
            val email : String = binding.emailEditText.text.toString()
            val password : String = binding.passwordEditText.text.toString()
            viewModel.login(email, password).observe(this) { result ->
                when (result) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> {
                        val accessToken = result.data.toString()
                        val refreshToken = result.data.toString()
                        viewModel.saveSession(
                            UserModel(
                                email,
                                accessToken,
                                refreshToken,
                                true
                            )
                        )
                        showLoading(false)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finishAffinity() // Finish all activities in the current task
                    }
                    is Result.Error -> {
                        Toast.makeText(
                            this, "Login Gagal!",
                            Toast.LENGTH_SHORT
                        ).show()
                        showLoading(false)
                    }
                }
            }
        binding.textRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}