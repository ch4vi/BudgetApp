package com.ch4vi.domain.repository

import com.ch4vi.domain.entity.Location
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure

interface LocationRepository {
    fun getLocations(forceUpdate: Boolean = false): Either<Failure, List<Location>>
    fun findLocations(filter: String): List<Location>
}