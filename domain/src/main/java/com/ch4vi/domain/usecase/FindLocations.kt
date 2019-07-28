package com.ch4vi.domain.usecase

import com.ch4vi.domain.entity.Location
import com.ch4vi.domain.repository.LocationRepository
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure
import com.ch4vi.domain.utils.UseCase

class FindLocations(
    private val locationRepository: LocationRepository
) : UseCase<List<Location>, FindLocations.Params>() {

    override suspend fun run(params: Params?): Either<Failure, List<Location>> {
        return locationRepository.findLocations(params?.filter ?: "")
    }

    class Params(val filter: String)
}