package com.ch4vi.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ch4vi.data.database.entity.DbBudget

@Dao
interface BudgetDao {
    @Query("SELECT * FROM budget")
    fun fetchAll(): List<DbBudget>?

    @Query("SELECT * FROM budget WHERE id = :id")
    fun fetchBudget(id: String): DbBudget?

    @Query("DELETE FROM budget")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBudget(budget: DbBudget): Long
}