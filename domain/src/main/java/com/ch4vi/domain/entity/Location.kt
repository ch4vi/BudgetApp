package com.ch4vi.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
    val id: String,
    val name: String,
    val zip: String
) : Parcelable