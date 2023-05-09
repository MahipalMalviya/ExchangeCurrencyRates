package com.mahipal.exchangecurrencyrates.di.module

import com.mahipal.exchangecurrencyrates.di.scope.ActivityScoped
import com.mahipal.exchangecurrencyrates.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}