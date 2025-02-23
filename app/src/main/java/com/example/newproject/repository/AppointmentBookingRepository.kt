package com.example.newproject.repository

import com.example.newproject.model.AppointmentBookingModel
import com.google.firebase.database.DatabaseReference

interface AppointmentBookingRepository {
    fun createAppointment(appointment: AppointmentBookingModel, database: DatabaseReference, callback: (Boolean, String?) -> Unit)
    fun getAppointments(database: DatabaseReference, callback: (List<AppointmentBookingModel>?, String?) -> Unit)
    fun updateAppointment(appointment: AppointmentBookingModel, database: DatabaseReference, callback: (Boolean, String?) -> Unit)
    fun deleteAppointment(appointmentId: String, database: DatabaseReference, callback: (Boolean, String?) -> Unit)
}
