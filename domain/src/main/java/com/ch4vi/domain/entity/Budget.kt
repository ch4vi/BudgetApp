package com.ch4vi.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Budget(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val subcategory: Category,
    val location: Location,
    val description: String
) : Parcelable