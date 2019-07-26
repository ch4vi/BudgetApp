package com.ch4vi.data.database

import com.ch4vi.data.database.entity.DbBudget
import com.ch4vi.data.database.entity.DbCategory
import com.ch4vi.data.database.entity.DbLocation
import com.ch4vi.domain.entity.Budget
import com.ch4vi.domain.entity.Category
import com.ch4vi.domain.entity.Location
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Either.Error
import com.ch4vi.domain.utils.Either.Success
import com.ch4vi.domain.utils.Failure


internal fun Category.toDb(): DbCategory {
    return DbCategory(id, parentId, name)
}

internal fun DbCategory.toCategory(): Either<Failure, Category> {
    id ?: return Error(Failure.MapperFailure("id"))
    parentId ?: return Error(Failure.MapperFailure("parentId"))
    name ?: return Error(Failure.MapperFailure("name"))

    return Success(Category(id, parentId, name))
}

internal fun Location.toDb(): DbLocation {
    return DbLocation(id, name, zip)
}

internal fun DbLocation.toLocation(): Either<Failure, Location> {
    name ?: return Error(Failure.MapperFailure("name"))
    zip ?: return Error(Failure.MapperFailure("zip"))

    return Success(Location(id, name, zip))
}

internal fun Budget.toDb(): DbBudget {
    return DbBudget(
        name,
        email,
        phone,
        subcategory.toDb(),
        location.toDb(),
        description
    ).apply { id = this@toDb.id }
}

internal fun DbBudget.toBudget(): Either<Failure, Budget> {
    name ?: return Error(Failure.MapperFailure("name"))
    email ?: return Error(Failure.MapperFailure("email"))
    phone ?: return Error(Failure.MapperFailure("phone"))
    description ?: return Error(Failure.MapperFailure("description"))

    return subcategory.toCategory().either(
        error = { Error(it) },
        success = { subcategory ->
            location.toLocation().either(error = { Error(it) },
                success = { location ->
                    Success(Budget(id, name, email, phone, subcategory, location, description))
                })
        })
}