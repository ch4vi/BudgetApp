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


fun Category.toDb(): DbCategory {
    return DbCategory(id, parentId, name)
}

fun DbCategory.toCategory(): Either<Failure, Category> {
    id ?: return Error(Failure.MapperFailure("id"))
    parentId ?: return Error(Failure.MapperFailure("parentId"))
    name ?: return Error(Failure.MapperFailure("name"))

    return Success(Category(id, parentId, name))
}

fun Location.toDb(): DbLocation {
    return DbLocation(id, name, zip)
}

fun DbLocation.toLocation(): Either<Failure, Location> {
    id ?: return Error(Failure.MapperFailure("id"))
    name ?: return Error(Failure.MapperFailure("name"))
    zip ?: return Error(Failure.MapperFailure("zip"))

    return Success(Location(id, name, zip))
}

fun Budget.toDb(): DbBudget {
    return DbBudget(
        name,
        email,
        phone,
        subcategory.toDb(),
        location.toDb(),
        description
    ).apply { id = this@toDb.id }
}

fun DbBudget.toBudget(): Either<Failure, Budget> {
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