package com.example.testapp

import android.app.Application
import com.example.testapp.di.module.appModule
import com.example.testapp.di.module.repoModule
import com.example.testapp.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            modules(listOf(appModule, viewModelModule, repoModule))
        }
    }
}

