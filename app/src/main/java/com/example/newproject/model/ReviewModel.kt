package com.example.newproject.model

import android.os.Parcel
import android.os.Parcelable

data class ReviewModel(
    var reviewId: String = "",
    var email: String = "",
    var rating: Int = 0,
    var message: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(reviewId)
        parcel.writeString(email)
        parcel.writeInt(rating)
        parcel.writeString(message)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ReviewModel> {
        override fun createFromParcel(parcel: Parcel): ReviewModel {
            return ReviewModel(parcel)
        }

        override fun newArray(size: Int): Array<ReviewModel?> {
            return arrayOfNulls(size)
        }
    }
}