package com.gchoy.weatherfetcher.zipcode

import android.os.Parcel
import android.os.Parcelable

/**
 * Representation of a zipcode per OpenWeatherMap.
 * Most analogous to a City.
 */
data class Zipcode(val name: String, val zipcode: String, val longitutde: Double, val latitude: Double) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readDouble()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(zipcode)
        parcel.writeDouble(longitutde)
        parcel.writeDouble(latitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (other is Zipcode) {
            return this.zipcode == other.zipcode
        }
        return false
    }

    override fun hashCode(): Int = this.zipcode.hashCode()

    companion object CREATOR : Parcelable.Creator<Zipcode> {
        override fun createFromParcel(parcel: Parcel): Zipcode {
            return Zipcode(parcel)
        }

        override fun newArray(size: Int): Array<Zipcode?> {
            return arrayOfNulls(size)
        }
    }
}