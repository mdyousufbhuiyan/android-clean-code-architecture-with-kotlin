package com.example.currencyconverter.repo

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import com.example.currencyconverter.data.PreferenceHelper
import com.example.currencyconverter.data.PreferenceHelper.currencies
import com.example.currencyconverter.data.PreferenceHelper.currenciesWithExchangeRate
import com.example.currencyconverter.data.PreferenceHelper.lastUpdateTime
import com.example.currencyconverter.data.remote.RemoteCurrencyDataSource
import com.example.currencyconverter.model.BaseApiResponse
import com.example.currencyconverter.utils.NetworkResult
import com.example.currencyconverter.utils.Utils
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


@ActivityRetainedScoped
class CurrencyRepositoryImpl @Inject constructor(
    private val application: Application,
    private val remoteCurrencyDataSource: RemoteCurrencyDataSource

) : BaseApiResponse(), CurrencyRepository {
    var preferences: SharedPreferences
    val gson = Gson()

    init {
        preferences = PreferenceHelper.customPreference(application)
    }

    override suspend fun getCurrencies(): Flow<NetworkResult<Any>> {

        if ((Utils.shouldUpdateData(preferences.lastUpdateTime) || preferences.currencies == null)&& Utils.hasInternetConnection(
                application
            )) {
            Log.e("Repository","................from remote...................")
          //  preferences.lastUpdateTime = System.currentTimeMillis()
            return flow<NetworkResult<Any>> {
                emit(safeApiCall { remoteCurrencyDataSource.getCurrencies() })
            }.flowOn(Dispatchers.IO)
        } else {
            Log.e("Repository","................from offline...................")
            return if (preferences.currencies != null) {
                val currencyResponse: LinkedTreeMap<Any, Any> = gson.fromJson(
                    preferences.currencies,
                    object : TypeToken<LinkedTreeMap<Any, Any>>() {}.type
                )
                flow<NetworkResult<Any>> {
                    emit(NetworkResult.Success(currencyResponse))
                }.flowOn(Dispatchers.IO)
            } else {
                flow<NetworkResult<Any>> {
                    emit(NetworkResult.Error("Failed"))
                }.flowOn(Dispatchers.IO)
            }
        }
    }

    override suspend fun getCurrenciesWithExchangeRate(): Flow<NetworkResult<Any>> {
        if ((Utils.shouldUpdateData(preferences.lastUpdateTime) || preferences.currenciesWithExchangeRate == null) && Utils.hasInternetConnection(
                application
            )
        ) {
            Log.e("Repository","................from remote...................")
            preferences.lastUpdateTime = System.currentTimeMillis()
            return flow<NetworkResult<Any>> {
                emit(safeApiCall { remoteCurrencyDataSource.getCurrenciesWithExchangeRate() })
            }.flowOn(Dispatchers.IO)
        } else {
            Log.e("Repository","................from offline...................")
            return if (preferences.currenciesWithExchangeRate != null) {
                val currenciesWithExchangeRate: LinkedTreeMap<Any, Any> = gson.fromJson(
                    preferences.currenciesWithExchangeRate,
                    object : TypeToken<LinkedTreeMap<Any, Any>>() {}.type
                )
                flow<NetworkResult<Any>> {
                    emit(NetworkResult.Success(currenciesWithExchangeRate))
                }.flowOn(Dispatchers.IO)
            } else {
                flow<NetworkResult<Any>> {
                    emit(NetworkResult.Error("Failed"))
                }.flowOn(Dispatchers.IO)
            }
        }
    }
}

