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
import com.example.newproject.databinding.ActivityPasswordRecoveryBinding
import com.example.newproject.repository.UserRepositoryImpl
import com.example.newproject.utils.LoadingUtils
import com.example.newproject.viewmodel.UserViewModel

class PasswordRecoveryActivity : AppCompatActivity() {
    lateinit var binding: ActivityPasswordRecoveryBinding
    lateinit var userViewModel: UserViewModel
    lateinit var loadingUtils: LoadingUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityPasswordRecoveryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingUtils = LoadingUtils(this)

        val userRepository = UserRepositoryImpl()
        userViewModel = UserViewModel(userRepository)

        binding.passwordRecoverybtn.setOnClickListener{
            loadingUtils.show()
            var  email: String = binding.passwordRecoveryEmail.text.toString()

            userViewModel.forgetPassword(email) { success, message ->
                if (success) {
                    loadingUtils.dismiss()
                    val intent = Intent(this@PasswordRecoveryActivity, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    loadingUtils.dismiss()
                    Toast.makeText(this@PasswordRecoveryActivity, message, Toast.LENGTH_SHORT).show()
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