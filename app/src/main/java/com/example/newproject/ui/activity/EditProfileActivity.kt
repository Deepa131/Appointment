package com.example.newproject.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newproject.R
import com.example.newproject.databinding.ActivityEditProfile2Binding
import com.example.newproject.repository.UserRepositoryImpl
import com.example.newproject.utils.LoadingUtils
import com.example.newproject.viewmodel.UserViewModel
import com.squareup.picasso.Picasso

class EditProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditProfile2Binding
    lateinit var userViewModel: UserViewModel
    private val PICK_IMAGE_REQUEST = 1
    private var selectedImageUri: Uri? = null
    lateinit var loadingUtils: LoadingUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityEditProfile2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingUtils = LoadingUtils(this)

        val repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        val currentUser = userViewModel.getCurrentUser()
        if (currentUser != null) {
            userViewModel.getUserFromDatabase(currentUser.uid)
        }

        userViewModel.userData.observe(this) { user ->
            binding.UpdateFname.setText(user?.firstName ?: "")
            binding.UpdateLname.setText(user?.lastName ?: "")
            binding.UpdateAddress.setText(user?.address ?: "")
            binding.UpdateContact.setText(user?.phoneNumber ?: "")
            binding.updateEmail.setText(user?.email ?: "")

            val profileImageUrl = user?.profileImageUrl
            if (!profileImageUrl.isNullOrEmpty()) {
                val updatedUrl = "$profileImageUrl?time=${System.currentTimeMillis()}"
                Picasso.get()
                    .load(updatedUrl)
                    .placeholder(R.drawable.baseline_person_24)
                    .into(binding.profileImageView)
            } else {
                binding.profileImageView.setImageResource(R.drawable.baseline_person_24)
            }
        }
        binding.profileImageView.setOnClickListener {
            openImagePicker()
        }

        binding.btnUpdateProfile.setOnClickListener {
            loadingUtils.show()
            val firstName = binding.UpdateFname.text.toString()
            val lastName = binding.UpdateLname.text.toString()
            val address = binding.UpdateAddress.text.toString()
            val contact = binding.UpdateContact.text.toString()
            val email = binding.updateEmail.text.toString()

            if (firstName.isEmpty() || lastName.isEmpty() || address.isEmpty() || contact.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                loadingUtils.dismiss()
                return@setOnClickListener
            }

            val updatedMap = mutableMapOf<String, Any>(
                "firstName" to firstName,
                "lastName" to lastName,
                "address" to address,
                "contact" to contact,
                "email" to email
            )

            selectedImageUri?.let { imageUri ->
                userViewModel.uploadImage(this, imageUri) { imageUrl ->
                    imageUrl?.let {
                        updatedMap["profileImageUrl"] = it
                    }
                    updateProfile(currentUser, updatedMap)
                }
            } ?: run {
                updateProfile(currentUser, updatedMap)
            }

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
    }

    private fun updateProfile(currentUser: com.google.firebase.auth.FirebaseUser?, updatedMap: MutableMap<String, Any>) {
        val userId = currentUser?.uid
        if (userId != null) {
            userViewModel.editProfile(userId, updatedMap) { success, message ->
                if (success) {
                    Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                    loadingUtils.dismiss()
                    finish()
                } else {
                    Toast.makeText(this, "Update failed: $message", Toast.LENGTH_SHORT).show()
                    loadingUtils.dismiss()
                }
            }
        } else {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
            loadingUtils.dismiss()
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.data
            Picasso.get()
                .load(selectedImageUri)
                .placeholder(R.drawable.placeholder)
                .into(binding.profileImageView)
        }
    }
}
