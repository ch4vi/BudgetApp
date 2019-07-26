package com.ch4vi.data.datasource

import com.ch4vi.domain.entity.Location
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure

interface LocationApiDataSource {
    fun getLocationList(): Either<Failure, List<Location>>
}