package com.mahipal.exchangecurrencyrates.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mahipal.exchangecurrencyrates.base.BaseActivity
import com.mahipal.exchangecurrencyrates.databinding.ActivityMainBinding
import com.mahipal.exchangecurrencyrates.model.CurrencyData
import com.mahipal.exchangecurrencyrates.model.CurrencyRateEntity
import com.mahipal.exchangecurrencyrates.view.adapter.RatesAdapter
import com.mahipal.exchangecurrencyrates.viewModel.MainActivityViewModel
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>() {

    @Inject
    lateinit var ratesAdapter: RatesAdapter

    private lateinit var timer: Timer

    private var currencyRateEntity: CurrencyRateEntity? = null

    private val timerTask = object : TimerTask() {
        override fun run() {
            fetchDataFromAPIs()
        }
    }

    override fun getViewModelClass() = MainActivityViewModel::class.java

    override fun getViewBinding(layoutInflater: LayoutInflater) =
        ActivityMainBinding.inflate(layoutInflater)

    override fun initializeViews() {
        timer = Timer()
        binding.etAmount.filters = arrayOf(DecimalFilter(10, 2))
        binding.rvCurrencyConversion.layoutManager = GridLayoutManager(this, 3)
        binding.rvCurrencyConversion.adapter = ratesAdapter

        timer.schedule(timerTask, 0L, 1000 * 30 * 60)
//        fetchDataFromAPIs()
        observerData()
    }

    private fun observerData() {
        viewModel.currencyRateData.observe(this) {
            currencyRateEntity = it
            val adapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, it.rates.keys.toList())
            binding.spinnerCurrency.adapter = adapter
            binding.spinnerCurrency.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        ratesAdapter.BASE_CURRENCY = p0?.selectedItem as String

                        setDataToList(it)
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {

                    }
                }
        }
    }

    private fun setDataToList(data: CurrencyRateEntity) {
        if (binding.etAmount.text.toString().isNotEmpty()) {
            val data = CurrencyData(binding.etAmount.text.toString().toDouble(), data.rates)
            ratesAdapter.setData(data.rates.keys.toList(), data)
        } else {
            Toast.makeText(
                this@MainActivity,
                "Please enter amount",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun fetchDataFromAPIs() {
        viewModel.getCurrencyRates(this)
    }

    private fun getFormattedAmount(amount: String): String? {
        if (amount.isNotEmpty()) {
            amount.toInt()
        }
        return NumberFormat.getNumberInstance(Locale.US).format(amount)
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
}