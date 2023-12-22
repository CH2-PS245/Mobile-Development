package com.example.dishdash.ui.WelcomeActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.example.dishdash.R
import com.example.dishdash.data.Result
import com.example.dishdash.data.ViewModelFactory
import com.example.dishdash.databinding.ActivityRegisterBinding
import com.example.dishdash.ui.viewModel.RegisterViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel : RegisterViewModel by viewModels {
        ViewModelFactory.getInstance(this  )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myUsername = binding.usernameEditText
        val myEmail = binding.emailEditText
        val myPassword = binding.passwordEditText

        registerViewModel.isLoading.observe(this) {
            showLoading(it)
        }


        setupView()
        setupAction()
        setMyButtonEnabled()


        binding.checkboxSnk.setOnCheckedChangeListener { _, _ ->
            setMyButtonEnabled()
        }

        myEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnabled()
            }

            override fun afterTextChanged(s: Editable) {
                val email = s.toString()

            }
        })
        myUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Tindakan sebelum teks berubah pada nameEditText
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Tindakan ketika teks berubah pada nameEditText
                setMyButtonEnabled()
            }

            override fun afterTextChanged(s: Editable?) {
                // Tindakan setelah teks berubah pada nameEditText
            }
        })
        myPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Tindakan sebelum teks berubah pada nameEditText
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Tindakan ketika teks berubah pada nameEditText
                val passwordText = s.toString()
                if (passwordText.length < 8) {
                    binding.btnRegister.isEnabled = false
                } else {
                    binding.passwordEditText.error = null // Hapus pesan error jika valid
                    setMyButtonEnabled() // Validasi email dan name
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Tindakan setelah teks berubah pada nameEditText
            }
        })
    }

    private fun setupView() {
        window.statusBarColor = resources.getColor(R.color.white)
        window.navigationBarColor = resources.getColor(R.color.white)
    }

    private fun showLoading(isLoading: Boolean) = binding.progressBar.isVisible == isLoading
    private fun setupAction(){
        binding.btnRegister.setOnClickListener {
            val name = binding.usernameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            registerViewModel.register(name,email, password).observe(this){
                if (it != null){
                    when(it){
                        is Result.Loading -> {
                            showLoading(true)
                        }
                        is Result.Success -> {
                            showLoading(false)
                            Toast.makeText(this,"Berhasil Daftar!, Silahkan Login",Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        }
                        is Result.Error -> {
                            showLoading(false)
                            Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }


        }
        binding.textLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun setMyButtonEnabled() {
        val nameText = binding.usernameEditText.text.toString()
        val emailText = binding.emailEditText.text.toString()
        val passwordText = binding.passwordEditText.text.toString()
        val isCheckboxChecked = binding.checkboxSnk.isChecked

        val isEmailValid = emailText.isNotEmpty()
        val isNameValid = nameText.length >= 8
        val isPasswordValid = passwordText.length >= 8
        val isCheckboxValid = isCheckboxChecked

        Log.d("RegisterActivity", "Is Checkbox Valid: $isCheckboxValid")

        binding.btnRegister.isEnabled = isEmailValid && isNameValid && isPasswordValid && isCheckboxValid
    }
}