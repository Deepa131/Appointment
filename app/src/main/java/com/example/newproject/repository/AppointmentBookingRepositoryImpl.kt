package com.example.newproject.repository

import com.example.newproject.model.AppointmentBookingModel
import com.google.firebase.database.DatabaseReference

class AppointmentBookingRepositoryImpl : AppointmentBookingRepository {
    override fun createAppointment(
        appointment: AppointmentBookingModel,
        database: DatabaseReference,
        callback: (Boolean, String?) -> Unit
    ) {
        val appointmentId = database.push().key
        appointment.appointmentId = appointmentId ?: ""

        database.child("appointments").child(appointment.appointmentId).setValue(appointment)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

    override fun getAppointments(
        database: DatabaseReference,
        callback: (List<AppointmentBookingModel>?, String?) -> Unit
    ) {
        database.child("appointments").get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val appointments = snapshot.children.mapNotNull {
                        it.getValue(AppointmentBookingModel::class.java)
                    }
                    callback(appointments, null)
                } else {
                    callback(null, "No appointments found")
                }
            }
            .addOnFailureListener { exception ->
                callback(null, exception.message)
            }
    }

    override fun updateAppointment(
        appointment: AppointmentBookingModel,
        database: DatabaseReference,
        callback: (Boolean, String?) -> Unit
    ) {
        database.child("appointments").child(appointment.appointmentId).setValue(appointment)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

    override fun deleteAppointment(
        appointmentId: String,
        database: DatabaseReference,
        callback: (Boolean, String?) -> Unit
    ) {
        database.child("appointments").child(appointmentId).removeValue()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }
}
