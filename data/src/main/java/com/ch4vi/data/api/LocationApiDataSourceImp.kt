package com.ch4vi.data.api

import com.ch4vi.data.datasource.LocationDataSource
import com.ch4vi.domain.entity.Location
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure
import com.ch4vi.domain.utils.flat

class LocationApiDataSourceImp(private val api: ApiService) : LocationDataSource {
    override fun getLocationList(): Either<Failure, List<Location>> {
        return api.getLocationList().call().either(::onError) { apiList ->
            apiList.map { it.toLocation() }.flat()
        }
    }

    private fun onError(failure: Failure) = Either.Error(failure)
}