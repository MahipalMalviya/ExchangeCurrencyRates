package com.mahipal.exchangecurrencyrates.di.module

import com.mahipal.exchangecurrencyrates.view.adapter.RatesAdapter
import dagger.Module
import dagger.Provides

@Module
class AdapterModule {

     @Provides
     fun provideMovieAdapter(ratesAdapter: RatesAdapter): RatesAdapter {
          return ratesAdapter
     }
}