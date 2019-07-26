package com.ch4vi.domain.usecase

import com.ch4vi.domain.entity.Budget
import com.ch4vi.domain.repository.BudgetRepository
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure
import com.ch4vi.domain.utils.UseCase

class CreateBudget(
    private val budgetRepository: BudgetRepository
) : UseCase<Unit, CreateBudget.Params>() {

    override suspend fun run(params: Params?): Either<Failure, Unit> {
        params ?: return onError(Failure.ParamsFailure())
        budgetRepository.createBudget(params.budget)
        return Either.Success(Unit)
    }

    private fun onError(failure: Failure) = Either.Error(failure)
    class Params(val budget: Budget)
}