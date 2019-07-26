package com.ch4vi.presentation

import com.ch4vi.domain.utils.Failure
import timber.log.Timber

open class BasePresenter<V : BaseView> {
    lateinit var view: V
    var isValid = false
        private set

    fun attachView(baseView: V) {
        this.view = baseView
        isValid = true
    }

    fun detachView() {
        isValid = false
    }

    fun onInternalError(failure: Failure) {
        Timber.e(failure.message)
    }
}

