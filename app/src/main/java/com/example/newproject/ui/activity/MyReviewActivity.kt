package com.example.newproject.ui.activity

import ReviewAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newproject.R
import com.example.newproject.model.ReviewModel
import com.example.newproject.utils.LoadingUtils
import com.google.firebase.database.*

class MyReviewActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var database: DatabaseReference
    private lateinit var reviewList: MutableList<ReviewModel>
    lateinit var loadingUtils: LoadingUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_review)

        loadingUtils = LoadingUtils(this)

        recyclerView = findViewById(R.id.recyclerViewReview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        reviewList = mutableListOf()
        reviewAdapter = ReviewAdapter(reviews = reviewList, onDeleteClick = { reviewId ->
            deleteReview(reviewId)
        }, onEditClick = { review ->
            // When the edit button is clicked, navigate to UpdateReviewActivity with review data
            val intent = Intent(this, UpdateReviewActivity::class.java).apply {
                putExtra("review", review) // Passing ReviewModel as Parcelable
            }
            startActivity(intent)
        })

        recyclerView.adapter = reviewAdapter

        // Firebase Database setup
        database = FirebaseDatabase.getInstance().getReference("reviews")

        // Load reviews
        loadReviews()

        // Edge-to-edge and window insets handling
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun loadReviews() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                reviewList.clear() // Clear existing reviews
                for (reviewSnapshot in snapshot.children) {
                    val review = reviewSnapshot.getValue(ReviewModel::class.java)
                    review?.let {
                        reviewList.add(it) // Add review to the list
                    }
                }
                reviewAdapter.notifyDataSetChanged() // Notify adapter to refresh the data
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Failed to load reviews.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun deleteReview(reviewId: String) {
        loadingUtils.show()
        database.child(reviewId).removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    loadingUtils.dismiss()
                    Toast.makeText(this, "Review deleted successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to delete review.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
