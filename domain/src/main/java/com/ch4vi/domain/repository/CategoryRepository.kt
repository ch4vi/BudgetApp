package com.ch4vi.domain.repository

import com.ch4vi.domain.entity.Category
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure

interface CategoryRepository {
    fun getCategories(): Either<Failure, List<Category>>
    fun getSubcategories(parentId: String): Either<Failure, List<Category>>
}