package com.example.newproject.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newproject.R
import com.example.newproject.adapter.TableBookingAdapter
import com.example.newproject.databinding.ActivityAppointmentBookingBinding
import com.example.newproject.databinding.ActivityMyAppointmentBinding
import com.example.newproject.databinding.ActivityMyTableBinding
import com.example.newproject.model.AppointmentBookingModel
import com.example.newproject.model.TableBookingModel
import com.example.newproject.repository.AppointmentBookingRepositoryImpl
import com.example.newproject.repository.TableBookingRepositoryImpl
import com.example.newproject.utils.LoadingUtils
import com.example.newproject.viewmodel.AppointmentBookingViewModel
import com.example.newproject.viewmodel.TableBookingViewModel

class MyAppointmentActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyAppointmentBinding
    lateinit var appointmentBookingViewModel: AppointmentBookingViewModel
    lateinit var adapter: TableBookingAdapter
    lateinit var loadingUtils: LoadingUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyAppointmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingUtils = LoadingUtils(this)

        val repo = AppointmentBookingRepositoryImpl()
        appointmentBookingViewModel = AppointmentBookingViewModel(repo)

        setupRecyclerView()
        observeViewModel()

        appointmentBookingViewModel.getAppointments()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupRecyclerView() {
        adapter = TableBookingAdapter(
            bookingList = emptyList(),
            onUpdateClick = { booking -> openUpdateDialog(booking) },
            onDeleteClick = { bookingId -> onDeleteBooking(bookingId) }
        )

        binding.recyclerViewTableBooking.apply {
            layoutManager = LinearLayoutManager(this@MyAppointmentActivity)
            adapter = this@MyAppointmentActivity.adapter
        }
    }

    private fun observeViewModel() {
        appointmentBookingViewModel.appointmentsList.observe(this) { appointments ->
            adapter.updateList(appointments)
        }

        appointmentBookingViewModel.appointmentStatus.observe(this) { status ->
            loadingUtils.dismiss()
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show()
        }
    }

    private fun openUpdateDialog(booking: AppointmentBookingModel) {
        val intent = Intent(this, UpdateBookingActivity::class.java)
        intent.putExtra("booking", booking)
        startActivity(intent)
    }

    private fun onDeleteBooking(bookingId: String) {
        loadingUtils.show()
        appointmentBookingViewModel.deleteAppointment(bookingId)
    }

}