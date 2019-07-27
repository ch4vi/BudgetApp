package com.ch4vi.data.datasource

import com.ch4vi.data.database.AppDatabase
import com.ch4vi.data.database.toDb
import com.ch4vi.data.database.toLocation
import com.ch4vi.domain.entity.Location
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure
import com.ch4vi.domain.utils.flat

class LocationDatabaseDataSourceImp(private val db: AppDatabase) : LocationDatabaseDataSource {

    override fun getLocationList(): Either<Failure, List<Location>> {
        val list = db.locationDao().fetchAll() ?: emptyList()
        return list.map { it.toLocation() }.flat()
    }

    override fun updateLocationList(locations: List<Location>) {
        db.locationDao().insertLocation(locations.map { it.toDb() })
    }

    override fun findLocations(filter: String): Either<Failure, List<Location>> {
        val list = db.locationDao().findLocations(filter) ?: emptyList()
        return list.map { it.toLocation() }.flat()
    }

    override fun countLocations(): Int {
        return db.locationDao().countLocations()
    }

    override fun clearLocations() {
        db.locationDao().deleteAll()
    }
}