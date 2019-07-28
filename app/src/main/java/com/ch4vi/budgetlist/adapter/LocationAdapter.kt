package com.ch4vi.budgetlist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.ch4vi.budgetlist.R
import com.ch4vi.domain.entity.Location
import com.ch4vi.presentation.adapter.AdapterView
import com.ch4vi.presentation.adapter.LocationAdapterPresenter
import com.ch4vi.presentation.adapter.LocationAdapterRowView
import kotlinx.android.synthetic.main.dropdown_location_item.view.location_row_layout_name
import kotlinx.android.synthetic.main.dropdown_location_item.view.location_row_layout_zip


class LocationAdapter(
    context: Context,
    private val presenter: LocationAdapterPresenter,
    items: List<Location>
) : ArrayAdapter<Location>(context, R.layout.dropdown_location_item, items), AdapterView {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private val filter = LocationFilter()

    init {
        presenter.setAdapter(this)
        presenter.updateItems(items)
    }

    override fun notifyChanges() {
        if (presenter.items.isNotEmpty()) notifyDataSetChanged()
        else notifyDataSetInvalidated()
    }

    override fun getCount(): Int {
        return presenter.items.size
    }

    override fun getItem(position: Int): Location? {
        return presenter.items.getOrNull(position)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val holder: ViewHolder
        val resultView: View

        if (convertView == null) {
            resultView = layoutInflater.inflate(R.layout.dropdown_location_item, null)
            holder = ViewHolder(resultView)
            resultView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
            resultView = convertView
        }

        presenter.bindView(position, holder)

        return resultView
    }

    class ViewHolder(view: View) : LocationAdapterRowView {
        private val name: TextView? = view.location_row_layout_name
        private val zip: TextView? = view.location_row_layout_zip

        override fun setName(name: String) {
            this.name?.text = name
        }

        override fun setZip(zip: String) {
            this.zip?.text = zip
        }
    }

    override fun getFilter(): Filter {
        return filter
    }

    inner class LocationFilter : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults()
            presenter.findLocations(constraint?.toString()).apply {
                results.values = this
                results.count = this.size
            }
            return results
        }

        override fun convertResultToString(resultValue: Any?): CharSequence {
            return presenter.itemToString(resultValue)
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            presenter.updateItems(results?.values as? List<Location> ?: emptyList())
        }
    }
}