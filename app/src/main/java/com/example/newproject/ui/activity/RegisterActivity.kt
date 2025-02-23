package com.example.newproject.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newproject.R
import com.example.newproject.databinding.ActivityRegisterBinding
import com.example.newproject.model.UserModel
import com.example.newproject.repository.UserRepositoryImpl
import com.example.newproject.utils.LoadingUtils
import com.example.newproject.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    lateinit var userViewModel: UserViewModel
    lateinit var loadingUtils: LoadingUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingUtils = LoadingUtils(this)

        val userRepository = UserRepositoryImpl()
        userViewModel = UserViewModel(userRepository)

        binding.btnRegister.setOnClickListener {

            var email: String = binding.registerEmail.text.toString()
            var password: String = binding.registerPassword.text.toString()
            var fname: String = binding.registerFname.text.toString()
            var lname: String = binding.registerLname.text.toString()
            var address: String = binding.registerAddress.text.toString()
            var contact: String = binding.registerContact.text.toString()

            if(email.isEmpty()){
                binding.registerEmail.error = "email can't be empty"
            }else if (password.isEmpty()){
                binding.registerPassword.error = "password can't be empty"
            }else if (fname.isEmpty()){
                binding.registerFname.error = " First name can't be empty"
            }else if(lname.isEmpty()){
                binding.registerLname.error = "Last name can't be empty"
            }else if(address.isEmpty()){
                binding.registerAddress.error = "Address can't be empty"
            }else if(contact.isEmpty()){
                binding.registerContact.error = "Contact can't be empty"
            }else{
                loadingUtils.show()
                userViewModel.signup(email, password) { success, message, userId ->
                    if (success) {
                        val userModel = UserModel(userId, email, fname, lname, address, contact)
                        addUser(userModel)
                    } else {
                        loadingUtils.dismiss()
                        Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
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

    fun addUser(userModel: UserModel) {
        userViewModel.addUserToDatabase(userModel.userId,userModel){
                success,message ->
            if(success){
                Toast.makeText(this@RegisterActivity
                    ,message,Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this@RegisterActivity
                    ,message,Toast.LENGTH_SHORT).show()
            }
            loadingUtils.dismiss()
        }
    }
}