package com.mahipal.exchangecurrencyrates.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahipal.exchangecurrencyrates.di.scope.AppScoped
import com.mahipal.exchangecurrencyrates.viewModel.MainActivityViewModel
import com.mahipal.exchangecurrencyrates.viewModel.ViewModelFactory
import com.mahipal.exchangecurrencyrates.viewModel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @AppScoped
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewModel(viewModel: MainActivityViewModel): ViewModel


}