package com.ch4vi.domain.usecase

import com.ch4vi.domain.entity.Budget
import com.ch4vi.domain.repository.BudgetRepository
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure
import com.ch4vi.domain.utils.UseCase

class GetBudgetList(
    private val budgetRepository: BudgetRepository
) : UseCase<List<Budget>, Unit>() {

    override suspend fun run(params: Unit?): Either<Failure, List<Budget>> {
        return budgetRepository.getBudgetList()
    }
}