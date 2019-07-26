package com.ch4vi.data.database.entity

import androidx.room.Entity


@Entity(tableName = "location", primaryKeys = ["id"])
data class DbLocation(
    val id: String = "",
    val name: String? = null,
    val zip: String? = null
)