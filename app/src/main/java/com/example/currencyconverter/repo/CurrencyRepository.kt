package com.example.currencyconverter.repo

import com.example.currencyconverter.data.remote.RemoteCurrencyDataSource
import com.example.currencyconverter.model.BaseApiResponse
import com.example.currencyconverter.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


@ActivityRetainedScoped
interface CurrencyRepository {
    suspend fun getCurrencies(): Flow<NetworkResult<Any>>
    suspend fun getCurrenciesWithExchangeRate(): Flow<NetworkResult<Any>>
}

