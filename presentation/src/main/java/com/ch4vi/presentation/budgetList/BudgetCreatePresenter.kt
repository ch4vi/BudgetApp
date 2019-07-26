package com.ch4vi.presentation.budgetList

import com.ch4vi.domain.entity.Category
import com.ch4vi.domain.entity.Location
import com.ch4vi.domain.usecase.CreateBudget
import com.ch4vi.domain.usecase.FindLocations
import com.ch4vi.domain.usecase.GetCategoryList
import com.ch4vi.domain.usecase.GetLocationList
import com.ch4vi.domain.usecase.GetSubcategoryList
import com.ch4vi.domain.utils.Failure
import com.ch4vi.presentation.BasePresenter
import com.ch4vi.presentation.BaseView

class BudgetCreatePresenter(
    private val createBudget: CreateBudget,
    private val findLocations: FindLocations,
    private val getCategoryList: GetCategoryList,
    private val getLocationList: GetLocationList,
    private val getSubcategoryList: GetSubcategoryList
) : BasePresenter<BudgetListView>() {


    private fun onError(failure: Failure) {
        view.showError(failure.message)
    }
}


interface BudgetCreateView : BaseView {
    fun showError(message: String?)
    fun goToListBudget()
    fun loadLocationsList(list: List<Location>)
    fun loadCategoryList(list: List<Category>)
    fun loadSubcategoryist(list: List<Category>)
}