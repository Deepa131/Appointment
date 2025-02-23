package com.example.newproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newproject.model.ReviewModel
import com.example.newproject.repository.ReviewRepository
import com.google.firebase.database.DatabaseReference

class ReviewViewModel (private val repository: ReviewRepository, private val database: DatabaseReference) : ViewModel(){

    val reviewsLiveData = MutableLiveData<List<ReviewModel>>()
    val operationStatus = MutableLiveData<Boolean>()

    fun addReview(email: String, rating: Int, message: String) {
        val review = ReviewModel("", email, rating, message)
        repository.createReview(review, database) { success, _ ->
            operationStatus.value = success
            if (success) loadReviews()
        }
    }

    private fun loadReviews() {
        repository.getReviews(database) { reviewsList, _ ->
            reviewsLiveData.value = reviewsList ?: emptyList()
        }
    }


    fun updateReview(review: ReviewModel) {
        repository.updateReview(review, database) { success, _ ->
            operationStatus.value = success
            if (success) loadReviews()
        }
    }

    fun deleteReview(reviewId: String) {
        repository.deleteReview(reviewId, database) { success, _ ->
            operationStatus.value = success
            if (success) loadReviews()
        }
    }

}