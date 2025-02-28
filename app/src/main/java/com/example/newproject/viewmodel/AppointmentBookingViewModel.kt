package com.example.newproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newproject.model.AppointmentBookingModel
import com.example.newproject.repository.AppointmentBookingRepository
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AppointmentBookingViewModel(private val repository: AppointmentBookingRepository) : ViewModel() {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    val appointmentStatus = MutableLiveData<String>()
    val appointmentsList = MutableLiveData<List<AppointmentBookingModel>>()

    fun createAppointment(appointment: AppointmentBookingModel) {
        repository.createAppointment(appointment, database) { success, error ->
            if (success) {
                appointmentStatus.postValue("Appointment booked successfully!")
            } else {
                appointmentStatus.postValue("Error: $error")
            }
        }
    }

    fun getAppointments() {
        repository.getAppointments(database) { appointments, error ->
            if (appointments != null) {
                appointmentsList.postValue(appointments)
            } else {
                appointmentStatus.postValue(error ?: "Error fetching appointments")
            }
        }
    }

    fun updateAppointment(appointment: AppointmentBookingModel) {
        repository.updateAppointment(appointment, database) { success, error ->
            if (success) {
                appointmentStatus.postValue("Appointment updated successfully!")
            } else {
                appointmentStatus.postValue("Error: $error")
            }
        }
    }

    fun deleteAppointment(appointmentId: String) {
        repository.deleteAppointment(appointmentId, database) { success, error ->
            if (success) {
                val currentList = appointmentsList.value?.toMutableList() ?: mutableListOf()
                val appointmentToRemove = currentList.find { it.appointmentId == appointmentId }
                if (appointmentToRemove != null) {
                    currentList.remove(appointmentToRemove)
                }

                appointmentsList.postValue(currentList)

                appointmentStatus.postValue("Appointment deleted successfully!")
            } else {
                appointmentStatus.postValue("Error: $error")
            }
        }
    }
}
