package com.ch4vi.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ch4vi.data.database.entity.DbLocation

@Dao
interface LocationDao {
    @Query("SELECT * FROM location")
    fun fetchAll(): List<DbLocation>?

    @Query("SELECT * FROM location WHERE name LIKE :filter OR zip LIKE :filter")
    fun findLocations(filter: String): List<DbLocation>?

    @Query("DELETE FROM budget")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocation(location: DbLocation): Long

    @Query("SELECT COUNT(id) FROM location")
    fun countLocations(): Int
}