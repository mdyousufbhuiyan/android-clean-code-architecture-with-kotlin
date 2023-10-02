package com.example.currencyconverter.utils

import android.util.Log
import com.example.currencyconverter.model.ItemsModel
import com.google.gson.internal.LinkedTreeMap

object Converter {

    fun convertCurrencies(
        currency: String, amount: String, currencyWithExchangeRateResponse:
        LinkedTreeMap<Any, Any>, currencyResponse: LinkedTreeMap<Any, Any>
    ): ArrayList<ItemsModel> {
        val dataList = ArrayList<ItemsModel>()

        if (amount.isNotEmpty()) {
            val amount = amount.let {
                if (it.length > 0) it.toDouble() else 1.0
            }
            val usdValue = currencyWithExchangeRateResponse.get(key = currency)?.let {
                it.toString().toDouble()
            }
            currencyResponse.forEach {
                val cc = it.key.toString()
                val countryName = it.value.toString()
                currencyWithExchangeRateResponse.get(key = cc)?.let {
                    val exchangeRate = it.toString().toDouble()!! / usdValue!!
                    dataList.add(
                        ItemsModel(
                            cc,
                            countryName,
                            String.format("%.4f", exchangeRate).toDouble(),
                            String.format("%.4f", exchangeRate * amount!!).toDouble(),
                        )
                    )
                }
            }


        }
        return dataList
    }
}