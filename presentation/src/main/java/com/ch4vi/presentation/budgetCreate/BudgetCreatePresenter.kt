package com.ch4vi.presentation.budgetCreate

import com.ch4vi.domain.entity.Budget
import com.ch4vi.domain.entity.Category
import com.ch4vi.domain.entity.Location
import com.ch4vi.domain.usecase.CreateBudget
import com.ch4vi.domain.usecase.GetCategoryList
import com.ch4vi.domain.usecase.GetLocationList
import com.ch4vi.domain.usecase.GetSubcategoryList
import com.ch4vi.domain.usecase.ValidateEmail
import com.ch4vi.domain.usecase.ValidateNumeric
import com.ch4vi.domain.utils.Failure
import com.ch4vi.presentation.BasePresenter
import com.ch4vi.presentation.BaseView

class BudgetCreatePresenter(
    private val createBudget: CreateBudget,
    private val getCategoryList: GetCategoryList,
    private val getLocationList: GetLocationList,
    private val getSubcategoryList: GetSubcategoryList,
    private val validateEmail: ValidateEmail,
    private val validateNumeric: ValidateNumeric
) : BasePresenter<BudgetCreateView>() {

    var mutableBudget = MutableBudget()
        private set

    fun getCategories() {
        getCategoryList(::onError) {
            view.loadCategoryList(it)
        }
    }

    fun setCategory(category: Category?) {
        mutableBudget.category = null
        mutableBudget.subcategory = null

        view.clearSubcategory()
        if (category == null) {
            view.clearCategory()
            return
        }

        mutableBudget.category = category
        getSubcategoryList(::onError, GetSubcategoryList.Params(category.id)) {
            view.loadSubcategoryList(it)
        }
    }

    fun setSubcategory(subcategory: Category?, restored: Boolean = false) {
        mutableBudget.subcategory = subcategory
        if (restored && subcategory != null) {
            view.forceSubcategory(subcategory)
        }
    }

    fun getLocations() {
        getLocationList(::onError) {
            view.loadLocationsList(it)
        }
    }

    fun setLocation(location: Location?) {
        location ?: view.clearLocation()
        mutableBudget.location = location
    }

    fun setName(name: String?) {
        mutableBudget.name = name
    }

    fun setEmail(email: String?) {
        mutableBudget.email = null

        if (email == null) {
            view.showEmailInvalid(false)
        } else {
            validateEmail(params = ValidateEmail.Params(email),
                error = {
                    view.showEmailInvalid(true)
                }, success = {
                    view.showEmailInvalid(!it)
                    if (it) mutableBudget.email = email
                })
        }
    }

    fun setPhone(phone: String?) {
        mutableBudget.phone = null

        if (phone == null) {
            view.showPhoneInvalid(false)
        } else {
            validateNumeric(params = ValidateNumeric.Params(phone),
                error = {
                    view.showPhoneInvalid(true)
                }, success = {
                    view.showPhoneInvalid(!it)
                    if (it) mutableBudget.phone = phone
                })
        }
    }

    fun setDescription(description: String?) {
        mutableBudget.description = description
    }

    fun completeBudget() {
        val name = mutableBudget.name
        if (name == null || name.isBlank()) {
            view.showNameInvalid()
            return
        }

        val email = mutableBudget.email
        if (email == null) {
            view.showEmailInvalid(true)
            return
        }

        val phone = mutableBudget.phone
        if (phone == null) {
            view.showPhoneInvalid(true)
            return
        }

        val location = mutableBudget.location
        if (location == null) {
            view.showLocationInvalid()
            return
        }

        val category = mutableBudget.category
        if (category == null) {
            view.showCategoryInvalid()
            return
        }

        val subcategory = mutableBudget.subcategory
        if (subcategory == null) {
            view.showSubcategoryInvalid()
            return
        }

        val description = mutableBudget.description
        if (description == null || description.isBlank()) {
            view.showDescriptionInvalid()
            return
        }

        val budget = Budget(name, email, phone, subcategory, location, description)
        createBudget(budget)
    }

    private fun createBudget(budget: Budget) {
        createBudget(::onError, CreateBudget.Params(budget)) {
            view.budgetCreated()
        }
    }

    private fun onError(failure: Failure) {
        view.showError(failure.message)
    }
}

interface BudgetCreateView : BaseView {
    fun budgetCreated()
    fun clearCategory()
    fun clearLocation()
    fun clearSubcategory()
    fun loadCategoryList(list: List<Category>)
    fun loadLocationsList(list: List<Location>)
    fun loadSubcategoryList(list: List<Category>)
    fun forceSubcategory(subcategory: Category)
    fun showCategoryInvalid()
    fun showDescriptionInvalid()
    fun showEmailInvalid(enabled: Boolean)
    fun showError(message: String?)
    fun showLocationInvalid()
    fun showNameInvalid()
    fun showPhoneInvalid(enabled: Boolean)
    fun showSubcategoryInvalid()
}