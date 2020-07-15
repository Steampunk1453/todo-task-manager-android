package org.task.manager

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.task.manager.data.di.dataModules
import org.task.manager.domain.di.domainModules
import org.task.manager.presentation.di.presentationModules
import org.task.manager.shared.di.sharedModules

class MyToDoTaskManager : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyToDoTaskManager)
            modules(presentationModules + domainModules + dataModules + sharedModules)
        }

    }
}