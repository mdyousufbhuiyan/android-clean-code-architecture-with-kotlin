package com.example.currencyconverter.data.remote

import com.example.currencyconverter.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface CurrencyConverterService {
    @Headers("Accept: application/json")
    @GET("currencies.json?prettyprint=false&show_alternative=false&show_inactive=false&app_id=${BuildConfig.APP_ID}")
    suspend fun getCurrencies(): Response<Any>

    @Headers("Accept: application/json")
    @GET("latest.json?app_id=${BuildConfig.APP_ID}")
    suspend fun getCurrenciesWithExchangeRate(): Response<Any>
}
