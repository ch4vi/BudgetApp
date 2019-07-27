package com.ch4vi.budgetlist.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.ch4vi.budgetlist.BaseActivity
import com.ch4vi.budgetlist.R
import com.ch4vi.budgetlist.adapter.BudgetAdapter
import com.ch4vi.budgetlist.extension.snackbar
import com.ch4vi.domain.entity.Budget
import com.ch4vi.presentation.budgetList.BudgetListPresenter
import com.ch4vi.presentation.budgetList.BudgetListView
import kotlinx.android.synthetic.main.activity_main.budget_layout_create
import kotlinx.android.synthetic.main.activity_main.budget_layout_list
import kotlinx.android.synthetic.main.activity_main.budget_layout_view
import kotlinx.android.synthetic.main.activity_main.toolbar
import org.koin.android.ext.android.inject


class MainActivity : BaseActivity(), BudgetListView {

    private val presenter by inject<BudgetListPresenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setPresenter(presenter)

        budget_layout_list.layoutManager = LinearLayoutManager(this)

        budget_layout_create.setOnClickListener {
            presenter.openCreateBudget()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.getBudgetList()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.update_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_refresh -> {
                presenter.getBudgetList()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showError(message: String?) {
        budget_layout_view.snackbar(message ?: getString(R.string.error_unknown_message))
    }

    override fun goToCreateBudget() {
        startActivity(Intent(this, CreateBudgetActivity::class.java))
    }

    override fun loadBudgetList(list: List<Budget>) {
        budget_layout_list.adapter = BudgetAdapter(list)
        budget_layout_list.adapter?.notifyDataSetChanged()
    }
}
