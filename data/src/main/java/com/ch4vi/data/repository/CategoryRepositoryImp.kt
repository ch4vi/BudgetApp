package com.ch4vi.data.repository

import com.ch4vi.data.datasource.CategoryApiDataSource
import com.ch4vi.domain.entity.Category
import com.ch4vi.domain.repository.CategoryRepository
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure

class CategoryRepositoryImp(
    private val categoryApi: CategoryApiDataSource
) : CategoryRepository {

    override fun getCategories(): Either<Failure, List<Category>> {
        return categoryApi.getCategoryList()
    }

    override fun getSubcategories(parentId: String): Either<Failure, List<Category>> {
        return categoryApi.getSubcategoryList(parentId)
    }
}