package com.example.newproject.repository

import com.example.newproject.model.ReviewModel
import com.google.firebase.database.DatabaseReference

class ReviewRepositoryImpl : ReviewRepository {
    override fun createReview(
        review: ReviewModel,
        database: DatabaseReference,
        callback: (Boolean, String?) -> Unit
    ) {
        val reviewId = database.push().key
        review.reviewId = reviewId ?: ""

        database.child("reviews").child(review.reviewId).setValue(review)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

    override fun getReviews(
        database: DatabaseReference,
        callback: (List<ReviewModel>?, String?) -> Unit
    ) {
        database.child("reviews").get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val reviews = snapshot.children.mapNotNull {
                        it.getValue(ReviewModel::class.java)
                    }
                    callback(reviews, null)
                } else {
                    callback(null, "No reviews found")
                }
            }
            .addOnFailureListener { exception ->
                callback(null, exception.message)
            }
    }

    override fun updateReview(
        review: ReviewModel,
        database: DatabaseReference,
        callback: (Boolean, String?) -> Unit
    ) {
        database.child("reviews").child(review.reviewId).setValue(review)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

    override fun deleteReview(
        reviewId: String,
        database: DatabaseReference,
        callback: (Boolean, String?) -> Unit
    ) {
        database.child("reviews").child(reviewId).removeValue()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }
}