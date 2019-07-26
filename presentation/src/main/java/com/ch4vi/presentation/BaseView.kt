package com.ch4vi.presentation

interface BaseView {
    fun <V : BaseView> setPresenter(basePresenter: BasePresenter<V>)
}