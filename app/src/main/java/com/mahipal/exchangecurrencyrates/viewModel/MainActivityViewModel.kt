package com.mahipal.exchangecurrencyrates.viewModel

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mahipal.exchangecurrencyrates.App
import com.mahipal.exchangecurrencyrates.di.scope.AppScoped
import com.mahipal.exchangecurrencyrates.model.CurrencyRateEntity
import com.mahipal.exchangecurrencyrates.model.CurrencySymbol
import com.mahipal.exchangecurrencyrates.model.toDataEntity
import com.mahipal.exchangecurrencyrates.repository.APIRepository
import com.mahipal.exchangecurrencyrates.repository.DatabaseRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


@AppScoped
class MainActivityViewModel @Inject constructor(
    private val mainRepository: APIRepository,
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private val _progressData = MutableLiveData<Boolean>().apply {
        value = false
    }
    val progressData: LiveData<Boolean>
        get() = _progressData

    private val _responseData = MutableLiveData<CurrencySymbol>().apply {
        value = CurrencySymbol(emptyList(), emptyList())
    }
    val responseData: LiveData<CurrencySymbol>
        get() = _responseData

    private val _ratesData = MutableLiveData<Map<String, Double>>().apply {
        value = mapOf()
    }
    val ratesData: LiveData<Map<String, Double>>
        get() = _ratesData

    private val _currencyRateData = MutableLiveData<CurrencyRateEntity>().apply {
        value = CurrencyRateEntity(0, mapOf())
    }
    val currencyRateData: LiveData<CurrencyRateEntity>
        get() = _currencyRateData

    private val _error = MutableLiveData<String>().apply {
        value = ""
    }
    val error: LiveData<String>
        get() = _error

    private val compositeDisposable by lazy { CompositeDisposable() }

    fun getCurrencyList() {
        compositeDisposable.add(
            mainRepository.getCurrencyList()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { _progressData.postValue(true) }
                .doAfterTerminate { _progressData.postValue(false) }
                .map {
                    CurrencySymbol(it.keys.toList(), it.values.toList())
                }
                .subscribe({ list ->
                    list?.let {
                        _responseData.postValue(it)
                    }
                }, {
                    _error.postValue("Something went wrong")
                    it.printStackTrace()
                })
        )
    }

    fun getCurrencyRates(context: Context) {
        if (isInternetOn(context)) {
            compositeDisposable.add(
                mainRepository.getCurrencyRates()
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe { _progressData.postValue(true) }
                    .doAfterTerminate { _progressData.postValue(false) }
                    .subscribe({ currencyrate ->
//                        _ratesData.postValue(currencyrate.rates)
                        insertCurrencyDataInDB(currencyrate.rates)
                    }, {
                        _error.postValue("Something went wrong")
                        it.printStackTrace()
                        fetchCurrencyDataFromDB()
                    })
            )
        } else {
            fetchCurrencyDataFromDB()
        }
    }

    private fun insertCurrencyDataInDB(rates: Map<String, Double>) {
        App.database.apply {
            compositeDisposable.add(
                Single.just(databaseRepository.insertCurrencyData(currencyRateDao(),rates.toDataEntity()))
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe { _progressData.postValue(true) }
                    .doAfterTerminate { _progressData.postValue(false) }
                    .subscribe({
                        fetchCurrencyDataFromDB()
                    }, {
                        _error.postValue("Something went wrong")
                        it.printStackTrace()
                    })
            )
        }
    }

    fun fetchCurrencyDataFromDB() {
        compositeDisposable.add(
            databaseRepository.getCurrencyRateData(App.database.currencyRateDao())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { _progressData.postValue(true) }
                .doAfterTerminate { _progressData.postValue(false) }
                .subscribe({
                    _currencyRateData.postValue(it)
                },{
                    _error.postValue("Something went wrong")
                    it.printStackTrace()
                })
        )
    }

    private fun isInternetOn(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }
}