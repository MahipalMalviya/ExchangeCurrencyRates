package com.mahipal.exchangecurrencyrates.di.component

import android.app.Application
import com.mahipal.exchangecurrencyrates.App
import com.mahipal.exchangecurrencyrates.di.module.ActivityBindingModule
import com.mahipal.exchangecurrencyrates.di.module.ViewModelModule
import com.mahipal.exchangecurrencyrates.di.scope.AppScoped
import com.mahipal.exchangecurrencyrates.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@AppScoped
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        NetworkModule::class,
        ActivityBindingModule::class,
        ViewModelModule::class,
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun attachApplication(application: Application): Builder

        fun build(): AppComponent
    }



}