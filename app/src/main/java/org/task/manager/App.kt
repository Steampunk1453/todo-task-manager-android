package org.task.manager

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.task.manager.data.di.dataModules
import org.task.manager.domain.di.domainModules
//import org.task.manager.presentation.di.presentationModules

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(domainModules + dataModules)
        }

    }
}