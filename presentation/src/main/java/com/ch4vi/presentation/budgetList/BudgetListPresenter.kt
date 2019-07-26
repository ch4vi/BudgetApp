package com.ch4vi.presentation.budgetList

import com.ch4vi.domain.entity.Budget
import com.ch4vi.domain.usecase.GetBudgetList
import com.ch4vi.domain.utils.Failure
import com.ch4vi.presentation.BasePresenter
import com.ch4vi.presentation.BaseView

class BudgetListPresenter(
    private val getBudgetList: GetBudgetList
) : BasePresenter<BudgetListView>() {

    fun getBudgetList() {
        getBudgetList(::onError) {
            view.loadBudgetList(it)
        }
    }

    fun openCreateBudget() {
        view.goToCreateBudget()
    }

    private fun onError(failure: Failure) {
        view.showError(failure.message)
    }
}


interface BudgetListView : BaseView {
    fun showError(message: String?)
    fun goToCreateBudget()
    fun loadBudgetList(list: List<Budget>)
}