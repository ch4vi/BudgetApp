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

    override fun getLocations(forceUpdate: Boolean): Either<Failure, List<Location>> {
        if (forceUpdate || locationDatabase.countLocations() == 0) {
            return locationApi.getLocationList().either({
                Either.Error(it)
            }) {
                locationDatabase.updateLocationList(it)
                Either.Success(it)
            }
        }
        return locationDatabase.getLocationList()
    }

    override fun findLocations(filter: String): List<Location> {
        return locationDatabase.findLocations(filter)
    }
}