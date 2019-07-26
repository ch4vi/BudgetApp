package com.ch4vi.data.datasource

import com.ch4vi.domain.entity.Location
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure

interface LocationDatabaseDataSource {
    fun getLocationList(): Either<Failure, List<Location>>
    fun updateLocationList(locations: List<Location>)
    fun findLocations(filter: String): Either<Failure, List<Location>>
    fun countLocations(): Int
    fun clearLocations()
}