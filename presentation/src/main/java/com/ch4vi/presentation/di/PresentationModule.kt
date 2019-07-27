package com.ch4vi.presentation.di

import com.ch4vi.presentation.adapter.LocationAdapterPresenter
import com.ch4vi.presentation.budgetCreate.BudgetCreatePresenter
import com.ch4vi.presentation.budgetList.BudgetListPresenter
import org.koin.dsl.module

val presentationModule = module {
    factory { BudgetListPresenter(getBudgetList = get()) }
    factory {
        BudgetCreatePresenter(
            createBudget = get(),
            getCategoryList = get(),
            getLocationList = get(),
            getSubcategoryList = get(),
            validateEmail = get(),
            validateNotBlank = get(),
            validateNumeric = get()
        )
    }

    factory { LocationAdapterPresenter(findLocations = get()) }
}