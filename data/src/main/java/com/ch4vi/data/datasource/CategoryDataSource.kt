package com.ch4vi.data.datasource

import com.ch4vi.domain.entity.Category
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure

interface CategoryDataSource {
    fun getCategoryList(): Either<Failure, List<Category>>
    fun getCategoryListByParentId(parentId: String): Either<Failure, List<Category>>
}