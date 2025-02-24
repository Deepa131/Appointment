package com.example.newproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newproject.R
import com.example.newproject.model.AppointmentBookingModel
import com.example.newproject.model.TableBookingModel

class TableBookingAdapter(
    private var bookingList: List<AppointmentBookingModel>,
    private val onUpdateClick: (AppointmentBookingModel) -> Unit,
    private val onDeleteClick: (String) -> Unit
) : RecyclerView.Adapter<TableBookingAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCustomerName: TextView = view.findViewById(R.id.tvCustomerName)
        val tvDateTime: TextView = view.findViewById(R.id.tvDateTime)
        val btnedit: Button = view.findViewById(R.id.btnedittablebooking)
        val btnDelete: Button = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_booking, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val booking = bookingList[position]
        holder.tvCustomerName.text = booking.customerName
        holder.tvDateTime.text = "${booking.date} at ${booking.time}"

        holder.btnedit.setOnClickListener { onUpdateClick(booking) }
        holder.btnDelete.setOnClickListener { onDeleteClick(booking.appointmentId) }
    }

    override fun getItemCount(): Int = bookingList.size

    fun updateList(newList: List<AppointmentBookingModel>) {
        bookingList = newList
        notifyDataSetChanged()
    }
}
