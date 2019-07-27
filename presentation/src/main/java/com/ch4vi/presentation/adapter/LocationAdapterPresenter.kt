package com.ch4vi.presentation.adapter

import com.ch4vi.domain.entity.Location
import com.ch4vi.domain.usecase.FindLocations

class LocationAdapterPresenter(
    private val findLocations: FindLocations
) : BaseAdapterPresenter<Location, LocationAdapterRowView>() {

    override fun bindView(position: Int, rowView: LocationAdapterRowView) {
        items.getOrNull(position)?.let {
            rowView.setName(it.name)
            rowView.setZip(it.zip)
        }
    }

    fun findLocations(filter: String?): List<Location> {
        if (filter == null || filter.isBlank()) return emptyList()
        val f = filter.trim().toLowerCase()
        return findLocations.runBlocking(FindLocations.Params(f))
            .either(error = { emptyList() }, success = { it })
    }

    fun itemToString(item: Any?): String {
        return when (item) {
            is Location -> "${item.name} ${item.zip}"
            else -> item?.toString() ?: ""
        }
    }
}

interface LocationAdapterRowView : RowView {
    fun setName(name: String)
    fun setZip(zip: String)
}