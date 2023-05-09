package com.mahipal.exchangecurrencyrates.view.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahipal.exchangecurrencyrates.databinding.ItemCurrencyConversionsBinding
import com.mahipal.exchangecurrencyrates.model.CurrencyData
import java.math.BigDecimal
import javax.inject.Inject

class RatesAdapter @Inject constructor() : RecyclerView.Adapter<RatesAdapter.MyViewHolder>() {

    var BASE_CURRENCY = "USD"
    private var TARGET_CURRENCY = "EUR"

    private var list: List<String> = mutableListOf()
    private var currencyData: CurrencyData? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemCurrencyConversionsBinding.
        inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(itemList: List<String>?, data: CurrencyData) {
        currencyData = data
        if (itemList != null) {
            list = itemList
            notifyDataSetChanged()
        }
    }

    inner class MyViewHolder(private val binding: ItemCurrencyConversionsBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            currencyData?.let {
                binding.tvCurrencyConversion.text = getCurrencyConversionRates(it,adapterPosition)
            }
        }

    }

    fun getCurrencyConversionRates(data: CurrencyData, position: Int): String {
        TARGET_CURRENCY = list[position]
        val baseRate = data.rates[BASE_CURRENCY]
        val targetRate = data.rates[TARGET_CURRENCY]

        val conversionRate = targetRate?.div(baseRate!!)
        val targetAmount = data.amount * conversionRate!!
        val roundedAmount =
            BigDecimal(targetAmount).setScale(2, BigDecimal.ROUND_HALF_UP)

        return "$TARGET_CURRENCY: \n$roundedAmount"
    }
}