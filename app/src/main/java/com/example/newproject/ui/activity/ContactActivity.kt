package com.example.newproject.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newproject.R
import com.example.newproject.databinding.ActivityContactBinding



class ContactActivity : AppCompatActivity() {

    lateinit var binding: ActivityContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textViewPhone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:+977-9800000000")
            startActivity(intent)
        }

        binding.textViewEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:info@deliciousdeck.com")
            intent.putExtra(Intent.EXTRA_SUBJECT, "Customer Inquiry")
            startActivity(intent)
        }

        binding.textViewAddress.setOnClickListener {
            val mapIntent = Intent(Intent.ACTION_VIEW)
            mapIntent.data = Uri.parse("geo:27.7172,85.3240?q=Delicious Deck, Kathmandu") // Update with actual location
            startActivity(mapIntent)
        }

        binding.textViewInstagram.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/deliciousdeck"))
            startActivity(browserIntent)
        }

        binding.textViewFacebook.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/deliciousdeck"))
            startActivity(browserIntent)
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }
}