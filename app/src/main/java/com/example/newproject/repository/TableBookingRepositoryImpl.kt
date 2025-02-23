package com.example.newproject.repository

import com.example.newproject.model.TableBookingModel
import com.google.firebase.database.DatabaseReference

class TableBookingRepositoryImpl : TableBookingRepository {
    override fun createBooking(
        booking: TableBookingModel,
        database: DatabaseReference,
        callback: (Boolean, String?) -> Unit
    ) {
        val bookingId = database.push().key
        booking.bookingId = bookingId ?: ""

        database.child("bookings").child(booking.bookingId).setValue(booking)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

    override fun getBookings(
        database: DatabaseReference,
        callback: (List<TableBookingModel>?, String?) -> Unit
    ) {
        database.child("bookings").get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val bookings = snapshot.children.mapNotNull {
                        it.getValue(TableBookingModel::class.java)
                    }
                    callback(bookings, null)
                } else {
                    callback(null, "No bookings found")
                }
            }
            .addOnFailureListener { exception ->
                callback(null, exception.message)
            }
    }

    override fun updateBooking(
        booking: TableBookingModel,
        database: DatabaseReference,
        callback: (Boolean, String?) -> Unit
    ) {
        database.child("bookings").child(booking.bookingId).setValue(booking)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

    override fun deleteBooking(
        bookingId: String,
        database: DatabaseReference,
        callback: (Boolean, String?) -> Unit
    ) {
        database.child("bookings").child(bookingId).removeValue()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }
}