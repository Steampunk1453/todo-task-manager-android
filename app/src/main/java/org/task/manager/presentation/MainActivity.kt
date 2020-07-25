package org.task.manager.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.task.manager.BuildConfig
import org.task.manager.R
import timber.log.Timber
import timber.log.Timber.DebugTree

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        setContentView(R.layout.activity_main)
    }
}