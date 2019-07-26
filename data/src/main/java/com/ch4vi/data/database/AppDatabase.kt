package com.ch4vi.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ch4vi.data.database.dao.BudgetDao
import com.ch4vi.data.database.dao.LocationDao
import com.ch4vi.data.database.entity.DbBudget
import com.ch4vi.data.database.entity.DbLocation

@Database(
    entities = [DbBudget::class, DbLocation::class],
    version = 1, exportSchema = true
)
@TypeConverters(value = [DateTypeConverter::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun budgetDao(): BudgetDao
    abstract fun locationDao(): LocationDao

    companion object {
        private const val DEFAULT_DATABASE_NAME = "app.db"

        @JvmOverloads
        fun create(
            context: Context,
            useInMemory: Boolean = false,
            dbName: String = DEFAULT_DATABASE_NAME
        ): AppDatabase {
            val databaseBuilder: Builder<AppDatabase> =
                if (useInMemory) Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                else Room.databaseBuilder(context, AppDatabase::class.java, dbName)

            return databaseBuilder
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}