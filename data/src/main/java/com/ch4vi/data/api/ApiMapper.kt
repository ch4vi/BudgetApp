package com.ch4vi.data.api

import com.ch4vi.data.api.entity.ApiCategory
import com.ch4vi.data.api.entity.ApiLocation
import com.ch4vi.domain.entity.Category
import com.ch4vi.domain.entity.Location
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure

internal fun ApiCategory.toCategory(): Either<Failure, Category> {
    id ?: return Either.Error(Failure.MapperFailure("id"))
    parentId ?: return Either.Error(Failure.MapperFailure("parentId"))
    name ?: return Either.Error(Failure.MapperFailure("name"))

    return Either.Success(Category(id, parentId, name))
}

internal fun ApiLocation.toLocation(): Either<Failure, Location> {
    id ?: return Either.Error(Failure.MapperFailure("id"))
    name ?: return Either.Error(Failure.MapperFailure("name"))
    zip ?: return Either.Error(Failure.MapperFailure("zip"))

    return Either.Success(Location(id, name, zip))
}