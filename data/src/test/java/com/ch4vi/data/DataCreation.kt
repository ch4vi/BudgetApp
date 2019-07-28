package com.ch4vi.data

import com.ch4vi.data.api.entity.ApiCategory
import com.ch4vi.data.api.entity.ApiLocation
import com.ch4vi.data.database.entity.DbBudget
import com.ch4vi.data.database.entity.DbCategory
import com.ch4vi.data.database.entity.DbLocation

fun createDbBudget(
    id: Int = 42,
    name: String? = "foo",
    email: String? = "foo",
    phone: String? = "foo",
    location: DbLocation = createDbLocation(),
    subcategory: DbCategory = createDbSubcategory(),
    description: String? = "foo"
) = DbBudget(name, email, phone, subcategory, location, description).apply {
    this.id = id
}

fun createApiLocation(
    id: String = "foo",
    parentId: String? = "foobar",
    name: String? = "bar",
    zip: String? = "42"
) = ApiLocation(id, parentId, name, zip)

fun createDbLocation(
    id: String = "foo",
    name: String? = "bar",
    zip: String? = "42"
) = DbLocation(id, name, zip)

fun createApiCategory(
    id: String? = "foo",
    parentId: String? = null,
    name: String? = "bar",
    description: String? = "waldo"
) = ApiCategory(id, parentId, name, description)

fun createApiSubcategory(
    id: String? = "foobar",
    parentId: String? = "foo",
    name: String? = "bar",
    description: String? = "waldo"
) = ApiCategory(id, parentId, name, description)

fun createDbSubcategory(
    id: String? = "foobar",
    parentId: String? = "foo",
    name: String? = "bar"
) = DbCategory(id, parentId, name)