package com.ch4vi.presentation.adapter

abstract class BaseAdapterPresenter<T, V : RowView> {
    private var adapter: AdapterView? = null
    var items = emptyList<T>()
        private set

    abstract fun bindView(position: Int, rowView: V)

    fun setAdapter(adapter: AdapterView) {
        this.adapter = adapter
    }

    fun detachAdapter() {
        this.adapter = null
    }

    fun updateItems(items: List<T>) {
        this.items = items
        adapter?.notifyChanges()
    }
}

interface AdapterView {
    fun notifyChanges()
}

interface RowView
