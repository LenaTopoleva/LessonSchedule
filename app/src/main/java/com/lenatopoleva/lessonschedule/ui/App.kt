package com.lenatopoleva.lessonschedule.ui

import android.app.Application
import com.lenatopoleva.lessonschedule.di.AppComponent
import com.lenatopoleva.lessonschedule.di.DaggerAppComponent
import com.lenatopoleva.lessonschedule.di.modules.AppModule

class App: Application() {

    companion object{
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent =  DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}