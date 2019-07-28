package com.ch4vi.domain

import com.ch4vi.domain.entity.Budget
import com.ch4vi.domain.entity.Category
import com.ch4vi.domain.entity.Location

fun createBudget(
    name: String = "foo",
    email: String = "foo",
    phone: String = "foo",
    location: Location = createLocation(),
    subcategory: Category = createSubcategory(),
    description: String = "foo"
) = Budget(name, email, phone, subcategory, location, description)

fun createLocation(
    id: String = "foo",
    name: String = "bar",
    zip: String = "42"
) = Location(id, name, zip)

fun createCategory(
    id: String = "foo",
    parentId: String? = null,
    name: String = "bar"
) = Category(id, parentId, name)

fun createSubcategory(
    id: String = "foobar",
    parentId: String = "foo",
    name: String = "bar"
) = Category(id, parentId, name)