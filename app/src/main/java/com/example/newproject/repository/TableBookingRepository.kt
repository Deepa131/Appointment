package com.example.newproject.repository

import com.example.newproject.model.TableBookingModel
import com.google.firebase.database.DatabaseReference
import com.squareup.picasso.Callback

interface TableBookingRepository {
    fun createBooking(booking: TableBookingModel, database:DatabaseReference, callback: (Boolean, String?) ->  Unit)
    fun getBookings(database: DatabaseReference, callback: (List<TableBookingModel>?, String?) -> Unit)
    fun updateBooking(booking: TableBookingModel, database: DatabaseReference, callback: (Boolean, String?) -> Unit)
    fun deleteBooking(bookingId: String, database: DatabaseReference, callback: (Boolean, String?) -> Unit)

}