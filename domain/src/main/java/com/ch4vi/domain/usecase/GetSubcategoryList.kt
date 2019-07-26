package com.ch4vi.domain.usecase

import com.ch4vi.domain.entity.Category
import com.ch4vi.domain.repository.CategoryRepository
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure
import com.ch4vi.domain.utils.UseCase

class GetSubcategoryList(
    private val categoryRepository: CategoryRepository
) : UseCase<List<Category>, GetSubcategoryList.Params>() {

    override suspend fun run(params: Params?): Either<Failure, List<Category>> {
        params ?: return onError(Failure.ParamsFailure())
        return categoryRepository.getSubcategories(params.parentId)
    }

    private fun onError(failure: Failure) = Either.Error(failure)
    class Params(val parentId: String)
}