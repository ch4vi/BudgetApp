package com.ch4vi.budgetlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ch4vi.budgetlist.R
import com.ch4vi.domain.entity.Budget
import kotlinx.android.synthetic.main.row_budget.view.budget_row_layout_city
import kotlinx.android.synthetic.main.row_budget.view.budget_row_layout_description
import kotlinx.android.synthetic.main.row_budget.view.budget_row_layout_email
import kotlinx.android.synthetic.main.row_budget.view.budget_row_layout_name
import kotlinx.android.synthetic.main.row_budget.view.budget_row_layout_phone
import kotlinx.android.synthetic.main.row_budget.view.budget_row_layout_subcategory

class BudgetAdapter(private val items: List<Budget>) :
    RecyclerView.Adapter<BudgetAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_budget,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val budget = items.getOrNull(position) ?: return

        holder.name?.text = budget.name
        holder.subcategory?.text = budget.subcategory.name
        holder.phone?.text = budget.phone
        holder.email?.text = budget.email
        holder.city?.text = "${budget.location.name} ${budget.location.zip}"
        holder.description?.text = budget.description
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView? = view.budget_row_layout_name
        val subcategory: TextView? = view.budget_row_layout_subcategory
        val phone: TextView? = view.budget_row_layout_phone
        val email: TextView? = view.budget_row_layout_email
        val city: TextView? = view.budget_row_layout_city
        val description: TextView? = view.budget_row_layout_description
    }
}