package com.example.currencyconverter.data


import com.example.currencyconverter.utils.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import com.example.currencyconverter.model.BaseApiResponse
import com.example.currencyconverter.repo.CurrencyRepository
import com.example.currencyconverter.utils.Utils

class FakeCurrencyRepositoryImplTest : BaseApiResponse(), CurrencyRepository {
    var isNetworkError = false
     var lastUpdatedTime: Long = 0

    fun shouldNetworkError(isNetworkError: Boolean) {
        this.isNetworkError = isNetworkError
    }

    override suspend fun getCurrencies(): Flow<NetworkResult<Any>> {
        return if (isNetworkError) {
            flow<NetworkResult<Any>> {
                emit(NetworkResult.Error("Failed"))
            }.flowOn(Dispatchers.Main)
        } else {
            if(Utils.shouldUpdateData(lastUpdatedTime))
            flow<NetworkResult<Any>> {
                emit(NetworkResult.Success("Data from remote server"))
            }.flowOn(Dispatchers.Main)
            else
                flow<NetworkResult<Any>> {
                    emit(NetworkResult.Success("Data from local storage"))
                }.flowOn(Dispatchers.Main)
        }

    }

    override suspend fun getCurrenciesWithExchangeRate(): Flow<NetworkResult<Any>> {
        return if (isNetworkError) {
            flow<NetworkResult<Any>> {
                emit(NetworkResult.Error("Failed"))
            }.flowOn(Dispatchers.Main)
        } else {
            if(Utils.shouldUpdateData(lastUpdatedTime))
                flow<NetworkResult<Any>> {
                    emit(NetworkResult.Success("Data from remote server"))
                }.flowOn(Dispatchers.Main)
            else
                flow<NetworkResult<Any>> {
                    emit(NetworkResult.Success("Data from local storage"))
                }.flowOn(Dispatchers.Main)
        }
    }
}