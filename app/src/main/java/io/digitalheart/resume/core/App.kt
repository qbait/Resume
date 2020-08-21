package io.digitalheart.resume.core

import android.app.Application
import io.digitalheart.resume.di.appModule
import io.digitalheart.resume.di.networkModule
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, networkModule))
    }
}