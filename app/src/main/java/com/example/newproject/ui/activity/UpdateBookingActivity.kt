package com.example.newproject.ui.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newproject.databinding.ActivityUpdateBookingBinding
import com.example.newproject.model.TableBookingModel
import com.example.newproject.repository.TableBookingRepositoryImpl
import com.example.newproject.viewmodel.TableBookingViewModel
import java.util.Calendar

class UpdateBookingActivity : AppCompatActivity() {

    lateinit var binding: ActivityUpdateBookingBinding
    lateinit var  tableBookingViewModel: TableBookingViewModel
    lateinit var booking: TableBookingModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repo = TableBookingRepositoryImpl()
        tableBookingViewModel = TableBookingViewModel(repo)

        val booking: TableBookingModel? = intent.getParcelableExtra("booking")
        if (booking == null) {
            Toast.makeText(this, "Booking data is missing", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        binding.etCustomerName.setText(booking.customerName)
        binding.etDate.setText(booking.date)
        binding.etTime.setText(booking.time)
        binding.etGuests.setText(booking.guests.toString())
        binding.etCustomerEmail.setText(booking.email)
        binding.etCustomerPhone.setText(booking.phone)

        binding.etDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val today = calendar.clone() as Calendar
            val minDate = today.timeInMillis

            calendar.add(Calendar.DAY_OF_YEAR, 3)
            val maxDate = calendar.timeInMillis

            val year = today.get(Calendar.YEAR)
            val month = today.get(Calendar.MONTH)
            val day = today.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                    val formattedDate = "${selectedDay}/${selectedMonth + 1}/${selectedYear}"
                    binding.etDate.setText(formattedDate)
                }, year, month, day)

            datePickerDialog.datePicker.minDate = minDate
            datePickerDialog.datePicker.maxDate = maxDate

            datePickerDialog.show()
        }

        binding.etTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                this,
                { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
                    val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                    binding.etTime.setText(formattedTime)
                },
                hour, minute, true
            )
            timePickerDialog.show()
        }

        binding.btnUpdateBooking.setOnClickListener {
            if (binding.etCustomerName.text.isNullOrEmpty() ||
                binding.etDate.text.isNullOrEmpty() ||
                binding.etTime.text.isNullOrEmpty() ||
                binding.etCustomerEmail.text.isNullOrEmpty() ||
                binding.etCustomerPhone.text.isNullOrEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            booking.customerName = binding.etCustomerName.text.toString()
            booking.date = binding.etDate.text.toString()
            booking.time = binding.etTime.text.toString()
            booking.guests = binding.etGuests.text.toString().toInt()
            booking.email = binding.etCustomerEmail.text.toString()
            booking.phone = binding.etCustomerPhone.text.toString()

            tableBookingViewModel.updateBooking(booking)
            Toast.makeText(this, "Booking updated!", Toast.LENGTH_SHORT).show()
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
}










