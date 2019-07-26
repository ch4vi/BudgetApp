@file:Suppress("unused")

package com.ch4vi.budgetlist

import android.app.Application
import com.ch4vi.data.di.dataModule
import com.ch4vi.domain.di.domainModule
import com.ch4vi.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree


class App : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        startKoin {
            // Use Koin Android Logger
            androidLogger()
            // declare Android context
            androidContext(this@App)
            // declare modules to use
            modules(listOf(domainModule, dataModule, presentationModule))
        }
    }
}