package com.ch4vi.domain.usecase

import com.ch4vi.domain.entity.Location
import com.ch4vi.domain.repository.LocationRepository
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure
import com.ch4vi.domain.utils.UseCase

class GetLocationList(
    private val locationRepository: LocationRepository
) : UseCase<List<Location>, GetLocationList.Params>() {
    override suspend fun run(params: Params?): Either<Failure, List<Location>> {
        if (params?.forceUpdate == true || locationRepository.isDatabaseEmpty()) {
            return locationRepository.getLocationsFromApi().either(::onError) {
                locationRepository.updateLocations(it)
                Either.Success(it)
            }
        }
        return locationRepository.getLocationsFromDatabase()
    }

    private fun onError(failure: Failure) = Either.Error(failure)
    class Params(val forceUpdate: Boolean)
}