package com.example.task7

import android.app.Application
import com.example.task7.di.AppComponent
import com.example.task7.di.ContextModule
import com.example.task7.di.DaggerAppComponent

class ClevertecApp: Application() {

    val appComponent: AppComponent by lazy { DaggerAppComponent.builder().contextModule(ContextModule(this)).build() }

}
