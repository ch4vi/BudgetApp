package com.ch4vi.budgetlist.ui

import android.os.Bundle
import com.ch4vi.budgetlist.BaseActivity
import com.ch4vi.budgetlist.R
import com.ch4vi.budgetlist.extension.snackbar
import com.ch4vi.domain.entity.Category
import com.ch4vi.domain.entity.Location
import com.ch4vi.presentation.budgetList.BudgetCreateView
import com.ch4vi.presentation.budgetList.BudgetListPresenter
import kotlinx.android.synthetic.main.activity_main.budget_layout_view
import kotlinx.android.synthetic.main.activity_main.toolbar
import org.koin.android.ext.android.inject

class CreateBudgetActivity : BaseActivity(), BudgetCreateView {

    private val presenter by inject<BudgetListPresenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setPresenter(presenter)

    }

    override fun showError(message: String?) {
        budget_layout_view.snackbar(message ?: getString(R.string.error_unknown_message))
    }

    override fun goToListBudget() {
        //TODO("not implemented")
    }

    override fun loadLocationsList(list: List<Location>) {
        //TODO("not implemented")
    }

    override fun loadCategoryList(list: List<Category>) {
        //TODO("not implemented")
    }

    override fun loadSubcategoryist(list: List<Category>) {
        //TODO("not implemented")
    }
}