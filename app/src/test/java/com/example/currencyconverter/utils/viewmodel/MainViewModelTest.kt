package com.example.currencyconverter.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.currencyconverter.data.FakeCurrencyRepositoryImplTest
import com.example.currencyconverter.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class) // New addition
internal class MainViewModelTest {

    lateinit var mainViewModel: MainViewModel
    lateinit var repo: FakeCurrencyRepositoryImplTest

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule() // New addition

    @Before
    fun setUp(){
        mainViewModel = MainViewModel(FakeCurrencyRepositoryImplTest())
        repo = FakeCurrencyRepositoryImplTest()

    }

    @Test
    fun `check currencies response for network request failed`(){
        runTest { // New addition
            mainViewModel = MainViewModel(repo)
            repo.shouldNetworkError(true)
            mainViewModel.fetchCurrencyResponse()
            advanceUntilIdle()  // New addition
            val res= mainViewModel.currencyResponse.value
            assertEquals("Failed",res?.message)
        }
    }
    @Test
    fun `check currencies response from local storage for network request success`(){
        runTest { // New addition
            mainViewModel = MainViewModel(repo)
            repo.shouldNetworkError(false)
            repo.lastUpdatedTime= System.currentTimeMillis()
            mainViewModel.fetchCurrencyResponse()
            advanceUntilIdle()  // New addition
            val res= mainViewModel.currencyResponse.value
            assertEquals("Data from local storage",res?.data)
        }
    }
    @Test
    fun `check currencies response from remote server for network request success`(){
        runTest { // New addition
            mainViewModel = MainViewModel(repo)
            repo.shouldNetworkError(false)
            repo.lastUpdatedTime= System.currentTimeMillis()-(31*60*1000)
            mainViewModel.fetchCurrencyResponse()
            advanceUntilIdle()  // New addition
            val res= mainViewModel.currencyResponse.value
            assertEquals("Data from remote server",res?.data)
        }
    }
    @Test
    fun `check currencies with exchange rate response for network request failed`(){
        runTest { // New addition
            mainViewModel = MainViewModel(repo)
            repo.shouldNetworkError(true)
            mainViewModel.fetchCurrenciesWithExchangeRate()
            advanceUntilIdle()  // New addition
            val res= mainViewModel.currencyWithExchangeRateResponse.value
            assertEquals("Failed",res?.message)
        }
    }
    @Test
    fun `check currencies with exchange rate response from local storage for network request success`(){
        runTest { // New addition
            mainViewModel = MainViewModel(repo)
            repo.shouldNetworkError(false)
            repo.lastUpdatedTime= System.currentTimeMillis()
            mainViewModel.fetchCurrenciesWithExchangeRate()
            advanceUntilIdle()  // New addition
            val res= mainViewModel.currencyWithExchangeRateResponse.value
            assertEquals("Data from local storage",res?.data)
        }
    }
    @Test
    fun `check currencies with exchange rate response from remote server for network request success`(){
        runTest { // New addition
            mainViewModel = MainViewModel(repo)
            repo.shouldNetworkError(false)
            repo.lastUpdatedTime= System.currentTimeMillis()-(31*60*1000)
            mainViewModel.fetchCurrenciesWithExchangeRate()
            advanceUntilIdle()  // New addition
            val res= mainViewModel.currencyWithExchangeRateResponse.value
            assertEquals("Data from remote server",res?.data)
        }
    }
}