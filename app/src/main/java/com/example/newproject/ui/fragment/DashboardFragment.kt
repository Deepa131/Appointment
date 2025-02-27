package com.example.newproject.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newproject.R
import com.example.newproject.databinding.FragmentDashboardBinding
import com.example.newproject.ui.activity.AppointmentBookingActivity

class DashboardFragment : Fragment() {

    lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bookATableButton.setOnClickListener({
            val intent = Intent(requireContext(), AppointmentBookingActivity::class.java)
            startActivity(intent)
        })



    }


}