package com.ch4vi.domain.di

import com.ch4vi.domain.usecase.CreateBudget
import com.ch4vi.domain.usecase.FindLocations
import com.ch4vi.domain.usecase.GetBudgetList
import com.ch4vi.domain.usecase.GetCategoryList
import com.ch4vi.domain.usecase.GetLocationList
import com.ch4vi.domain.usecase.GetSubcategoryList
import com.ch4vi.domain.usecase.ValidateEmail
import com.ch4vi.domain.usecase.ValidateNotBlank
import com.ch4vi.domain.usecase.ValidateNumeric
import org.koin.dsl.module

val domainModule = module {
    factory { CreateBudget(budgetRepository = get()) }
    factory { GetBudgetList(budgetRepository = get()) }
    factory { FindLocations(locationRepository = get()) }
    factory { GetLocationList(locationRepository = get()) }
    factory { GetCategoryList(categoryRepository = get()) }
    factory { GetSubcategoryList(categoryRepository = get()) }

    factory { ValidateEmail() }
    factory { ValidateNotBlank() }
    factory { ValidateNumeric() }
}