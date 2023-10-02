package com.example.currencyconverter.data.remote

import javax.inject.Inject

class RemoteCurrencyDataSource @Inject constructor(private val currencyConverterService: CurrencyConverterService) {

    suspend fun getCurrencies() = currencyConverterService.getCurrencies()
    suspend fun getCurrenciesWithExchangeRate() = currencyConverterService.getCurrenciesWithExchangeRate()

}