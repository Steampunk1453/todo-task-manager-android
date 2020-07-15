package org.task.manager.shared.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

private val modules = module {
    factory { androidApplication().getSharedPreferences("PREFERENCES", android.content.Context.MODE_PRIVATE)
    }

}

val sharedModules = listOf(modules)