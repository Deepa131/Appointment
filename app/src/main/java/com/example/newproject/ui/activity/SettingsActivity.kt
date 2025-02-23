package com.example.newproject.ui.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowInsetsCompat
import com.example.newproject.databinding.ActivitySettingsBinding
import com.example.newproject.viewmodel.SettingViewModel
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val settingsViewModel: SettingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val switchDarkMode = binding.switchDarkMode
        val switchNotifications = binding.switchNotifications

        settingsViewModel.darkModeEnabled.observe(this) { isEnabled ->
            switchDarkMode.isChecked = isEnabled
        }

        settingsViewModel.notificationsEnabled.observe(this) { isEnabled ->
            switchNotifications.isChecked = isEnabled
        }

        binding.btnDeleteAccount.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account? This action is irreversible.")
                .setPositiveButton("Yes") { _, _ ->
                    deleteUserAccount()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        binding.cardAboutUs.setOnClickListener({
            val  intent = Intent(this@SettingsActivity, AboutUsActivity::class.java)
            startActivity(intent)
        })

        binding.cardContact.setOnClickListener({
            val  intent = Intent(this@SettingsActivity, ContactActivity::class.java)
            startActivity(intent)
        })

        binding.cardTerms.setOnClickListener({
            val  intent = Intent(this@SettingsActivity, TermsActivity ::class.java)
            startActivity(intent)
        })

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.toggleDarkMode(isChecked)
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.toggleNotifications(isChecked)
        }

        settingsViewModel.loadSettings()
    }


    private fun deleteUserAccount() {
        val user = FirebaseAuth.getInstance().currentUser

        user?.delete()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Account deleted successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Failed to delete account: ${task.exception?.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
