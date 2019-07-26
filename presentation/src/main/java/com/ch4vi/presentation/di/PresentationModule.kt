package com.ch4vi.presentation.di

import com.ch4vi.presentation.budgetList.BudgetCreatePresenter
import com.ch4vi.presentation.budgetList.BudgetListPresenter
import org.koin.dsl.module

val presentationModule = module {
    factory { BudgetListPresenter(getBudgetList = get()) }
    factory {
        BudgetCreatePresenter(
            createBudget = get(),
            findLocations = get(),
            getCategoryList = get(),
            getLocationList = get(),
            getSubcategoryList = get()
        )
    }
}