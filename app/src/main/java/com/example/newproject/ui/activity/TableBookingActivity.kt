package com.example.newproject.ui.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newproject.R
import com.example.newproject.databinding.ActivityLoginBinding
import com.example.newproject.databinding.ActivityTableBookingBinding
import com.example.newproject.model.TableBookingModel
import com.example.newproject.repository.TableBookingRepository
import com.example.newproject.repository.TableBookingRepositoryImpl
import com.example.newproject.utils.LoadingUtils
import com.example.newproject.viewmodel.TableBookingViewModel
import com.example.newproject.viewmodel.UserViewModel
import java.util.Calendar

class TableBookingActivity : AppCompatActivity() {
    lateinit var binding: ActivityTableBookingBinding
    lateinit var tableBookingViewModel: TableBookingViewModel
    lateinit var loadingUtils: LoadingUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTableBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingUtils = LoadingUtils(this)

        val repo = TableBookingRepositoryImpl()
        tableBookingViewModel = TableBookingViewModel(repo)

        tableBookingViewModel.bookingStatus.observe(this, Observer { status ->
            loadingUtils.dismiss()
            Toast.makeText(this, status, Toast.LENGTH_SHORT).show()

            if (status == "Booking confirmed!") {
                navigateToMyTableActivity()
            }

        })

        binding.etDate.setOnClickListener {
            showDatePicker()
        }

        binding.etTime.setOnClickListener {
            showTimePicker()
        }

        binding.btnBookTable.setOnClickListener {
            val customerName = binding.etName.text.toString()
            val date = binding.etDate.text.toString()
            val time = binding.etTime.text.toString()
            val email = binding.etEmail.text.toString()
            val phone = binding.etPhone.text.toString()
            val guestCount = binding.etGuests.text.toString().toIntOrNull()

            if (customerName.isEmpty() || date.isEmpty() || time.isEmpty() || guestCount == null || guestCount <= 0) {
                Toast.makeText(this, "Please fill in all fields correctly.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val booking = TableBookingModel(
                bookingId = "",
                customerName = customerName,
                email = email,
                phone = phone,
                date = date,
                time = time,
                guests = guestCount
            )

            loadingUtils.show()
            tableBookingViewModel.createBooking(booking)




        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun navigateToMyTableActivity() {
        val intent = Intent(this, MyTableActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
            binding.etTime.setText(formattedTime)
        }, hour, minute, true)

        timePickerDialog.show()
    }


    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val today = calendar.clone() as Calendar
        val minDate = today.timeInMillis  // Minimum date is today

        calendar.add(Calendar.DAY_OF_YEAR, 3)  // Set to 3 days ahead
        val maxDate = calendar.timeInMillis  // Maximum date is 3 days from today

        val year = today.get(Calendar.YEAR)
        val month = today.get(Calendar.MONTH)
        val day = today.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            // Format and display the selected date
            val formattedDate = "${selectedYear}-${selectedMonth + 1}-${selectedDay}"
            binding.etDate.setText(formattedDate)
        }, year, month, day)

        // Set the min and max dates
        datePickerDialog.datePicker.minDate = minDate
        datePickerDialog.datePicker.maxDate = maxDate

        datePickerDialog.show()
    }

}