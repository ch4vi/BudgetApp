package com.ch4vi.data.datasource

import com.ch4vi.data.api.ApiService
import com.ch4vi.data.api.call
import com.ch4vi.data.api.toLocation
import com.ch4vi.domain.entity.Location
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure
import com.ch4vi.domain.utils.flat

class LocationApiDataSourceImp(private val api: ApiService) : LocationApiDataSource {
    override fun getLocationList(): Either<Failure, List<Location>> {
        return api.getLocationList().call().either(::onError) { apiList ->
            apiList.map { it.toLocation() }.flat()
        }
    }

    private fun onError(failure: Failure) = Either.Error(failure)
}