package com.example.trainer_helper_and_eat_supplements.LiveData

import android.app.Application
import android.content.Context

// Для получения контекста приложения
class MyAppClass : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    companion object {

        lateinit  var instance: Context

    }
}