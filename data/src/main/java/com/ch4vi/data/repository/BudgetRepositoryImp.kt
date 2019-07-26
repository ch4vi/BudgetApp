package com.ch4vi.data.repository

import com.ch4vi.data.datasource.BudgetDatabaseDataSource
import com.ch4vi.domain.entity.Budget
import com.ch4vi.domain.repository.BudgetRepository
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure

class BudgetRepositoryImp(
    private val budgetDatabase: BudgetDatabaseDataSource
) : BudgetRepository {
    override fun getBudgetList(): Either<Failure, List<Budget>> {
        return budgetDatabase.getBudgetList()
    }

    override fun createBudget(budget: Budget) {
        budgetDatabase.createBudget(budget)
    }
}