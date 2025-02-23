package com.example.newproject.ui.activity

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newproject.R
import com.example.newproject.databinding.ActivityTermsBinding

class TermsActivity : AppCompatActivity() {
    lateinit var binding: ActivityTermsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityTermsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val termsText = getString(R.string.terms_conditions_text)

        val formattedText = formatTermsText(termsText)
        binding.tvTerms.text = formattedText

        binding.tvTerms.setLineSpacing(8f, 1f)


        binding.btnBack.setOnClickListener {
            finish()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun formatTermsText(termsText: String): CharSequence? {
        return termsText.replace(".", ".\n\n")

    }
}