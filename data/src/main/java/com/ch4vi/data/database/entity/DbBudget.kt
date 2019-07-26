package com.ch4vi.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget")
data class DbBudget(
    val name: String? = null,
    val email: String? = null,
    val phone: String? = null,
    @Embedded(prefix = "subcategory_") val subcategory: DbCategory = DbCategory(),
    @Embedded(prefix = "location_") val location: DbLocation = DbLocation(),
    val description: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}