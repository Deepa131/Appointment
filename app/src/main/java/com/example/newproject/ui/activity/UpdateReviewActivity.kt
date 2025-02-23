package com.example.newproject.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newproject.databinding.ActivityUpdateReviewBinding
import com.example.newproject.model.ReviewModel
import com.example.newproject.utils.LoadingUtils
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateReviewBinding
    private lateinit var database: DatabaseReference
    private lateinit var reviewModel: ReviewModel
    lateinit var loadingUtils: LoadingUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingUtils = LoadingUtils(this)

        database = FirebaseDatabase.getInstance().getReference("reviews")

        reviewModel = intent.getParcelableExtra("review")!!

        with(binding) {
            emailEditText1.setText(reviewModel.email)
            ratingBar.rating = reviewModel.rating.toFloat()
            contentEditText1.setText(reviewModel.message)

            submitButton1.setOnClickListener { updateReview() }
        }
    }

    private fun updateReview() {
        loadingUtils.show()
        val updatedEmail = binding.emailEditText1.text.toString()
        val updatedRating = binding.ratingBar.rating
        val updatedMessage = binding.contentEditText1.text.toString()

        // Create an updated review object
        val updatedReview = mapOf(
            "email" to updatedEmail,
            "rating" to updatedRating,
            "message" to updatedMessage
        )

        if (reviewModel.reviewId.isNotEmpty()) {
            database.child(reviewModel.reviewId).updateChildren(updatedReview)
                .addOnSuccessListener {
                    loadingUtils.dismiss()
                    Toast.makeText(this, "Review updated successfully!", Toast.LENGTH_SHORT).show()
                    finish() // Close activity after update
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to update review.", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
