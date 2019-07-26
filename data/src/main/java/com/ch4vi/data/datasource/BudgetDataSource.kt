package com.ch4vi.data.datasource

import com.ch4vi.domain.entity.Budget
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure

interface BudgetDataSource {
    fun getBudget(id: String): Either<Failure, Budget>
    fun getBudgetList(): Either<Failure, List<Budget>>
    fun createBudget(budget: Budget)
}