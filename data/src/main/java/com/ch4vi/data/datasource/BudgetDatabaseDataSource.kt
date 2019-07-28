package com.ch4vi.data.datasource

import com.ch4vi.domain.entity.Budget
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure

interface BudgetDatabaseDataSource {
    fun getBudget(id: Int): Either<Failure, Budget>
    fun getBudgetList(): Either<Failure, List<Budget>>
    fun createBudget(budget: Budget)
}