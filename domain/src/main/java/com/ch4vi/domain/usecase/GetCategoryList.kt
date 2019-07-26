package com.ch4vi.domain.usecase

import com.ch4vi.domain.entity.Category
import com.ch4vi.domain.repository.CategoryRepository
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure
import com.ch4vi.domain.utils.UseCase

class GetCategoryList(
    private val categoryRepository: CategoryRepository
) : UseCase<List<Category>, Unit>() {

    override suspend fun run(params: Unit?): Either<Failure, List<Category>> {
        return categoryRepository.getCategories()
    }
}