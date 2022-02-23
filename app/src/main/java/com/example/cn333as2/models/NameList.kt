package com.example.cn333as2.models

import android.os.Parcel
import android.os.Parcelable

class NameList(val name: String, val content: String) : Parcelable{
    constructor(parcel: String) : this(
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NameList> {
        override fun createFromParcel(parcel: Parcel): NameList {
            return NameList(parcel)
        }

        override fun newArray(size: Int): Array<NameList?> {
            return arrayOfNulls(size)
        }
    }
}