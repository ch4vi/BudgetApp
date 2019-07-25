package com.ch4vi.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    val id: String,
    val parentId: String?,
    val name: String
) : Parcelable