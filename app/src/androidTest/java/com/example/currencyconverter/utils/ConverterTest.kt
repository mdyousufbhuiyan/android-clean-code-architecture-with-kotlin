package com.example.currencyconverter.utils

import androidx.test.platform.app.InstrumentationRegistry
import com.example.currencyconverter.utils.Utils.readJsonAsset
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import org.junit.Assert.*

import org.junit.Test

class ConverterTest {

    @Test
    fun convertCurrencies() {
        val ctx=  InstrumentationRegistry.getInstrumentation().context
        val currenciesJson=   ctx.readJsonAsset("currencies.json")
        val currencyWithExchangeRateJson=   ctx.readJsonAsset("currencies_with_exchange_rate.json")

        val gson = Gson()
        val currencyResponse: LinkedTreeMap<Any, Any> = gson.fromJson(currenciesJson, object : TypeToken<LinkedTreeMap<Any, Any>>() {}.type)
        val currencyWithExchangeRateResponse: LinkedTreeMap<Any, Any> = gson.fromJson(currencyWithExchangeRateJson, object : TypeToken<LinkedTreeMap<Any, Any>>() {}.type)
        val dataList = Converter.convertCurrencies(
            "AED",
           "2000",
            currencyWithExchangeRateResponse,
            currencyResponse
        )
        assertTrue(dataList.size>0)
    }
}