package com.example.newproject.model

import android.os.Parcel
import android.os.Parcelable

data class TableBookingModel(
    var bookingId: String = "",
    var customerName: String = "",
    var email: String = "",
    var phone: String=" ",
    var date: String = "",
    var time: String = "",
    var guests: Int = 0,
    var specialRequest: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(bookingId)
        parcel.writeString(customerName)
        parcel.writeString(email)
        parcel.writeString(phone)
        parcel.writeString(date)
        parcel.writeString(time)
        parcel.writeInt(guests)
        parcel.writeString(specialRequest)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TableBookingModel> {
        override fun createFromParcel(parcel: Parcel): TableBookingModel {
            return TableBookingModel(parcel)
        }

        override fun newArray(size: Int): Array<TableBookingModel?> {
            return arrayOfNulls(size)
        }
    }
}


