package com.ch4vi.domain.repository

import com.ch4vi.domain.entity.Location
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure

interface LocationRepository {
    fun getLocationsFromDatabase(): Either<Failure, List<Location>>
    fun getLocationsFromApi(): Either<Failure, List<Location>>
    fun updateLocations(locations: List<Location>)
    fun isDatabaseEmpty(): Boolean
    fun findLocations(filter: String): Either<Failure, List<Location>>
}