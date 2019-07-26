package com.ch4vi.data.repository

import com.ch4vi.data.datasource.BudgetDataSource
import com.ch4vi.domain.entity.Budget
import com.ch4vi.domain.repository.BudgetRepository
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure

class BudgetRepositoryImp(
    private val budgetDataSource: BudgetDataSource
) : BudgetRepository {
    override fun getBudgetList(): Either<Failure, List<Budget>> {
        return budgetDataSource.getBudgetList()
    }

    override fun createBudget(budget: Budget) {
        budgetDataSource.createBudget(budget)
    }
}