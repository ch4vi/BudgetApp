package com.ch4vi.budgetlist.ui

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.ch4vi.budgetlist.BaseActivity
import com.ch4vi.budgetlist.R
import com.ch4vi.budgetlist.adapter.LocationAdapter
import com.ch4vi.budgetlist.extension.snackbar
import com.ch4vi.domain.entity.Category
import com.ch4vi.domain.entity.Location
import com.ch4vi.presentation.adapter.LocationAdapterPresenter
import com.ch4vi.presentation.budgetCreate.BudgetCreatePresenter
import com.ch4vi.presentation.budgetCreate.BudgetCreateView
import kotlinx.android.synthetic.main.activity_budget_create.budget_create_layout_create
import kotlinx.android.synthetic.main.activity_budget_create.budget_create_layout_include
import kotlinx.android.synthetic.main.view_budget_create.budget_create_layout_category
import kotlinx.android.synthetic.main.view_budget_create.budget_create_layout_description
import kotlinx.android.synthetic.main.view_budget_create.budget_create_layout_email
import kotlinx.android.synthetic.main.view_budget_create.budget_create_layout_email_container
import kotlinx.android.synthetic.main.view_budget_create.budget_create_layout_location
import kotlinx.android.synthetic.main.view_budget_create.budget_create_layout_name
import kotlinx.android.synthetic.main.view_budget_create.budget_create_layout_phone
import kotlinx.android.synthetic.main.view_budget_create.budget_create_layout_phone_container
import kotlinx.android.synthetic.main.view_budget_create.budget_create_layout_subcategory
import kotlinx.android.synthetic.main.view_budget_create.budget_create_layout_subcategory_container
import org.koin.android.ext.android.inject

private const val LOCATION = "location"
private const val CATEGORY = "category"
private const val SUBCATEGORY = "subcategory"

class CreateBudgetActivity : BaseActivity(), BudgetCreateView {

    private val presenter by inject<BudgetCreatePresenter>()
    private val adapterPresenter by inject<LocationAdapterPresenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_create)
        setPresenter(presenter)

        initViews()
        presenter.getCategories()
        presenter.getLocations()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(LOCATION, presenter.mutableBudget.location)
        outState.putParcelable(CATEGORY, presenter.mutableBudget.category)
        outState.putParcelable(SUBCATEGORY, presenter.mutableBudget.subcategory)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        presenter.setLocation(savedInstanceState.getParcelable(LOCATION))
        presenter.setCategory(savedInstanceState.getParcelable(CATEGORY))
        presenter.setSubcategory(savedInstanceState.getParcelable(SUBCATEGORY), true)
    }

    override fun onDestroy() {
        super.onDestroy()
        adapterPresenter.detachAdapter()
    }

    private fun initViews() {
        budget_create_layout_create.setOnClickListener {
            presenter.completeBudget()
        }

        budget_create_layout_name.doOnTextChanged { text, _, _, _ ->
            presenter.setName(text?.toString())
        }

        budget_create_layout_email.doOnTextChanged { text, _, _, _ ->
            presenter.setEmail(text?.toString())
        }

        budget_create_layout_phone.doOnTextChanged { text, _, _, _ ->
            presenter.setPhone(text?.toString())
        }

        budget_create_layout_location.doOnTextChanged { _, _, _, _ ->
            presenter.setLocation(null)
        }

        budget_create_layout_description.doOnTextChanged { text, _, _, _ ->
            presenter.setDescription(text?.toString())
        }
    }

    override fun showError(message: String?) {
        budget_create_layout_include.snackbar(message ?: getString(R.string.error_unknown_message))
    }

    override fun budgetCreated() {
        Toast.makeText(this, getString(R.string.budget_created), Toast.LENGTH_LONG).show()
        onBackPressed()
    }

    override fun loadLocationsList(list: List<Location>) {
        val adapter = LocationAdapter(this, adapterPresenter, list)
        budget_create_layout_location.setAdapter(adapter)
        budget_create_layout_location.setOnItemClickListener { _, _, position, _ ->
            presenter.setLocation(adapterPresenter.items.getOrNull(position))
        }
    }

    override fun loadCategoryList(list: List<Category>) {
        val adapter = ArrayAdapter(this, R.layout.dropdown_categoty_item, list.map { it.name })
        budget_create_layout_category.setAdapter(adapter)
        budget_create_layout_category.setOnItemClickListener { _, _, position, _ ->
            presenter.setCategory(list.getOrNull(position))
        }
    }

    override fun loadSubcategoryList(list: List<Category>) {
        budget_create_layout_subcategory_container.visibility = View.VISIBLE

        val adapter = ArrayAdapter(this, R.layout.dropdown_categoty_item, list.map { it.name })
        budget_create_layout_subcategory.setAdapter(adapter)
        budget_create_layout_subcategory.setOnItemClickListener { _, _, position, _ ->
            presenter.setSubcategory(list.getOrNull(position))
        }
    }

    override fun forceSubcategory(subcategory: Category) {
        budget_create_layout_subcategory.setText(subcategory.name, false)
    }

    override fun clearCategory() {
        budget_create_layout_category?.clearListSelection()
        budget_create_layout_category.text.clear()
        budget_create_layout_subcategory_container.visibility = View.GONE
    }

    override fun clearSubcategory() {
        budget_create_layout_subcategory?.clearListSelection()
        budget_create_layout_subcategory.text.clear()
    }

    override fun clearLocation() {
        budget_create_layout_location?.clearListSelection()
    }

    override fun showEmailInvalid(enabled: Boolean) {
        budget_create_layout_email_container.isErrorEnabled = enabled
        budget_create_layout_email_container.error =
            if (enabled) getString(R.string.invalid_email) else ""
    }

    override fun showPhoneInvalid(enabled: Boolean) {
        budget_create_layout_phone_container.isErrorEnabled = enabled
        budget_create_layout_phone_container.error =
            if (enabled) getString(R.string.invalid_phone) else ""
    }

    override fun showNameInvalid() {
        showError(getString(R.string.invalid_name))
    }

    override fun showLocationInvalid() {
        showError(getString(R.string.invalid_location))
    }

    override fun showCategoryInvalid() {
        showError(getString(R.string.invalid_category))
    }

    override fun showSubcategoryInvalid() {
        showError(getString(R.string.invalid_subcategory))
    }

    override fun showDescriptionInvalid() {
        showError(getString(R.string.invalid_description))
    }
}