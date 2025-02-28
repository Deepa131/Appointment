package com.example.newproject.model

import android.os.Parcel
import android.os.Parcelable

data class AppointmentBookingModel(
    var appointmentId: String = "",
    var customerName: String = "",
    var email: String = "",
    var phone: String = "",
    var date: String = "",
    var time: String = "",
    var specialRequest: String? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(appointmentId)
        parcel.writeString(customerName)
        parcel.writeString(email)
        parcel.writeString(phone)
        parcel.writeString(date)
        parcel.writeString(time)
        parcel.writeString(specialRequest)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AppointmentBookingModel> {
        override fun createFromParcel(parcel: Parcel): AppointmentBookingModel {
            return AppointmentBookingModel(parcel)
        }

        override fun newArray(size: Int): Array<AppointmentBookingModel?> {
            return arrayOfNulls(size)
        }
    }
}
