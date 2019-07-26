package com.ch4vi.budgetlist

import androidx.appcompat.app.AppCompatActivity
import com.ch4vi.presentation.BasePresenter
import com.ch4vi.presentation.BaseView

abstract class BaseActivity : AppCompatActivity(), BaseView {
    private var basePresenter: BasePresenter<BaseView>? = null

    override fun <V : BaseView> setPresenter(basePresenter: BasePresenter<V>) {
        @Suppress("UNCHECKED_CAST")
        this.basePresenter = basePresenter as BasePresenter<BaseView>
        this.basePresenter?.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        basePresenter?.detachView()
    }
}