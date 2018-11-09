package io.digitalheart.resume.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext

val appModule = applicationContext {
    bean { androidApplication().resources }
}