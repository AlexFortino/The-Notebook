package com.example.android.navigation

import android.os.Parcel
import android.os.Parcelable
import android.widget.CheckBox

//@Parcelize
data class sandwhichData(
        var name: String,
        var basePrice: Double,
        var bread: String,
        var toppings: ArrayList<CheckBox>,
        var totalPrice: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readDouble(),
            parcel.readString(),
            TODO("toppings"),
            parcel.readDouble()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeDouble(basePrice)
        parcel.writeString(bread)
        parcel.writeDouble(totalPrice)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<sandwhichData> {
        override fun createFromParcel(parcel: Parcel): sandwhichData {
            return sandwhichData(parcel)
        }

        override fun newArray(size: Int): Array<sandwhichData?> {
            return arrayOfNulls(size)
        }
    }
}