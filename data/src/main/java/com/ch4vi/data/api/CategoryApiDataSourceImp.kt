package com.ch4vi.data.api

import com.ch4vi.data.datasource.CategoryDataSource
import com.ch4vi.domain.entity.Category
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure
import com.ch4vi.domain.utils.flat

class CategoryApiDataSourceImp(private val api: ApiService) : CategoryDataSource {
    override fun getCategoryList(): Either<Failure, List<Category>> {
        return api.getCategoryList().call().either(::onError) { apiList ->
            apiList.map { it.toCategory() }.flat()
        }
    }

    override fun getCategoryListByParentId(parentId: String): Either<Failure, List<Category>> {
        return api.getCategoryListByParentId(parentId).call().either(::onError) { apiList ->
            apiList.map { it.toCategory() }.flat()
        }
    }

    private fun onError(failure: Failure) = Either.Error(failure)
}