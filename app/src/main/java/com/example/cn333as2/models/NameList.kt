package com.example.cn333as2.models

import android.os.Parcel
import android.os.Parcelable

class NameList(val name: String, val tasks: ArrayList<String> = ArrayList()) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.createStringArrayList()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeStringList(tasks)
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