package com.ch4vi.data.datasource

import com.ch4vi.data.api.ApiService
import com.ch4vi.data.api.call
import com.ch4vi.data.api.toCategory
import com.ch4vi.data.api.toSubcategory
import com.ch4vi.domain.entity.Category
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure
import com.ch4vi.domain.utils.flat

class CategoryApiDataSourceImp(private val api: ApiService) : CategoryApiDataSource {
    override fun getCategoryList(): Either<Failure, List<Category>> {
        return api.getCategoryList().call().either(::onError) { apiList ->
            apiList.map { it.toCategory() }.flat()
        }
    }

    override fun getSubcategoryList(parentId: String): Either<Failure, List<Category>> {
        return api.getCategoryListByParentId(parentId).call().either(::onError) { apiList ->
            apiList.map { it.toSubcategory() }.flat()
        }
    }

    private fun onError(failure: Failure) = Either.Error(failure)
}