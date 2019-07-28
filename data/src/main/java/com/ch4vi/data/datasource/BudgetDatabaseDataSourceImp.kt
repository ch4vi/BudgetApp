package com.ch4vi.data.datasource

import com.ch4vi.data.database.AppDatabase
import com.ch4vi.data.database.toBudget
import com.ch4vi.data.database.toDb
import com.ch4vi.domain.entity.Budget
import com.ch4vi.domain.utils.Either
import com.ch4vi.domain.utils.Failure
import com.ch4vi.domain.utils.flat

class BudgetDatabaseDataSourceImp(private val db: AppDatabase) : BudgetDatabaseDataSource {

    override fun getBudget(id: Int): Either<Failure, Budget> {
        val dbBudget = db.budgetDao().fetchBudget(id)
        dbBudget ?: return Either.Error(Failure.GenericFailure("budget with id $id not found"))
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