package com.example.kotlinize.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey
    var title: String,
    var description: String?,
    var price: Float?,
    var rating: Float?,
    var brand: String?,
    var thumbnail: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readString(),
        parcel.readString()

    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        price?.let { parcel.writeFloat(it) }
        rating?.let { parcel.writeFloat(it) }
        parcel.writeString(brand)
        parcel.writeString(thumbnail)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}
