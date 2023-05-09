package com.mahipal.exchangecurrencyrates

import com.mahipal.exchangecurrencyrates.di.component.DaggerAppComponent
import com.mahipal.exchangecurrencyrates.di.database.CurrencyDatabase
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App: DaggerApplication() {

    companion object {
        lateinit var database: CurrencyDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = CurrencyDatabase.invoke(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
            .builder()
            .attachApplication(this)
            .build()
    }
}