package com.example.currencyconverter.di

import android.app.Application
import com.example.currencyconverter.data.CurrencyRepository
import com.example.currencyconverter.data.CurrencyRepositoryImpl
import com.example.currencyconverter.data.remote.CurrencyConverterService
import com.example.currencyconverter.data.remote.RemoteCurrencyDataSource
import com.example.currencyconverter.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideCurrencyConverterService(retrofit: Retrofit): CurrencyConverterService =
        retrofit.create(CurrencyConverterService::class.java)

    @Singleton
    @Provides
    fun provideCurrencyConverterRepoImpl(
        application: Application,
        remoteCurrencyDataSource: RemoteCurrencyDataSource
    ) = CurrencyRepositoryImpl(application, remoteCurrencyDataSource) as CurrencyRepository
}