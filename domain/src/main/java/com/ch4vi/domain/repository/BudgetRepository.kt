package com.ch4vi.domain.repository

import com.ch4vi.domain.entity.Budget
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure

interface BudgetRepository {
    fun getBudgetList(): Either<Failure, List<Budget>>
    fun createBudget(budget: Budget)
}