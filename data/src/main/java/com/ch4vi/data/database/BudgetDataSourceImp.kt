package com.ch4vi.data.database

import com.ch4vi.data.datasource.BudgetDataSource
import com.ch4vi.domain.entity.Budget
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure
import com.ch4vi.domain.utils.flat

class BudgetDataSourceImp(private val db: AppDatabase) : BudgetDataSource {
    override fun getBudget(id: String): Either<Failure, Budget> {
        val dbBudget = db.budgetDao().fetchBudget(id)
        dbBudget ?: return Either.Error(Failure.GenericFailure("budget with id $id not foud"))
        return dbBudget.toBudget()
    }

    override fun getBudgetList(): Either<Failure, List<Budget>> {
        val list = db.budgetDao().fetchAll()
        list ?: return Either.Success(emptyList())
        return list.map { it.toBudget() }.flat().either({ Either.Error(it) }) {
            Either.Success(it)
        }
    }

    override fun createBudget(budget: Budget) {
        db.budgetDao().insertBudget(budget.toDb())
    }
}