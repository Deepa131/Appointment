package com.example.newproject.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.newproject.databinding.FragmentSearchBinding
import com.example.newproject.model.ReviewModel
import com.example.newproject.ui.activity.MyReviewActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SearchFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance().getReference("reviews")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root // Return the root view of the binding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitButton1.setOnClickListener {
            submitReview()
        }
    }

    private fun submitReview() {
        val email = binding.emailEditText1.text.toString().trim()
        val rating = binding.ratingBar.rating.toInt()
        val message = binding.contentEditText1.text.toString().trim()

        if (email.isEmpty()) {
            binding.emailEditText1.error = "Email is required"
            return
        }

        if (message.isEmpty()) {
            binding.contentEditText1.error = "Message cannot be empty"
            return
        }

        val reviewId = database.push().key ?: return
        val review = ReviewModel(reviewId, email, rating, message)

        database.child(reviewId).setValue(review)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Review submitted successfully!", Toast.LENGTH_SHORT).show()
                    navigateToMyReviews()
                } else {
                    Toast.makeText(requireContext(), "Failed to submit review!", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun navigateToMyReviews() {
        val intent = Intent(requireContext(), MyReviewActivity::class.java)
        startActivity(intent)
        requireActivity().finish() // Close the current fragment
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Nullify the binding to avoid memory leaks
        _binding = null
    }
}
