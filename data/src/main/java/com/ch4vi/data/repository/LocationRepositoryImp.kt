package com.ch4vi.data.repository

import com.ch4vi.data.datasource.LocationApiDataSource
import com.ch4vi.data.datasource.LocationDatabaseDataSource
import com.ch4vi.domain.entity.Location
import com.ch4vi.domain.repository.LocationRepository
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure

class LocationRepositoryImp(
    private val locationApi: LocationApiDataSource,
    private val locationDatabase: LocationDatabaseDataSource
) : LocationRepository {

    override fun getLocationsFromDatabase(): Either<Failure, List<Location>> {
        return locationDatabase.getLocationList()
    }

    override fun getLocationsFromApi(): Either<Failure, List<Location>> {
        return locationApi.getLocationList()
    }

    override fun updateLocations(locations: List<Location>) {
        locationDatabase.clearLocations()
        locationDatabase.updateLocationList(locations)
    }

    override fun isDatabaseEmpty(): Boolean {
        return locationDatabase.countLocations() <= 0
    }

    override fun findLocations(filter: String): Either<Failure, List<Location>> {
        return locationDatabase.findLocations(filter)
    }
}