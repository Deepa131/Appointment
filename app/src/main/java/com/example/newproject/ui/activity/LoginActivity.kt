package com.example.newproject.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newproject.MainActivity
import com.example.newproject.R
import com.example.newproject.databinding.ActivityLoginBinding
import com.example.newproject.model.UserModel
import com.example.newproject.repository.UserRepositoryImpl
import com.example.newproject.utils.LoadingUtils
import com.example.newproject.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var userViewModel: UserViewModel
    lateinit var loadingUtils: LoadingUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingUtils = LoadingUtils(this)

        val userRepository = UserRepositoryImpl()
        userViewModel = UserViewModel(userRepository)

        binding.btnSignupnavigate.setOnClickListener{
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnForget.setOnClickListener({
            val  intent = Intent(this@LoginActivity, PasswordRecoveryActivity::class.java)
            startActivity(intent)
        })

        binding.btnLogin.setOnClickListener{
            var email: String = binding.editEmail.text.toString()
            var password: String = binding.editPassword.text.toString()

            if(email.isEmpty()){
                binding.editEmail.error = "email can't be empty"
            }else if(password.isEmpty()){
                binding.editPassword.error ="password can't be empty"
            }else{
                loadingUtils.show()
                userViewModel.login(email, password) { success, message ->
                    if (success) {
                        Toast.makeText(this@LoginActivity,message, Toast.LENGTH_LONG).show()
                        loadingUtils.dismiss()
                        val intent = Intent(this@LoginActivity, HomeActivity ::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
                        loadingUtils.dismiss()
                    }
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}