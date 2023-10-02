package com.example.currencyconverter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.CurrencyRepository
import com.example.currencyconverter.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor
    (
    private val repository: CurrencyRepository,
) : ViewModel() {

    private val _currencyResponse: MutableLiveData<NetworkResult<Any>> = MutableLiveData()
    val currencyResponse: MutableLiveData<NetworkResult<Any>> = _currencyResponse
    private val _currencyWithExchangeRateResponse: MutableLiveData<NetworkResult<Any>> =
        MutableLiveData()
    val currencyWithExchangeRateResponse: LiveData<NetworkResult<Any>> =
        _currencyWithExchangeRateResponse


    fun fetchCurrencyResponse() = viewModelScope.launch {
        repository.getCurrencies().collect { values ->
            _currencyResponse.value= values
        }
    }
    fun fetchCurrenciesWithExchangeRate() = viewModelScope.launch {
        repository.getCurrenciesWithExchangeRate().collect { values ->
            _currencyWithExchangeRateResponse.value= values
        }
    }

}