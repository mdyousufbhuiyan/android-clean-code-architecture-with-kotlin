package com.example.currencyconverter.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import javax.inject.Inject

object PreferenceHelper {
    val LAST_UPDATE_TIME = "LAST_UPDATE_TIME"
    val CURRENCIES = "CURRENCIES"
    val CURRENCIES_WITH_EXCHANGE_RATE = "CURRENCIES_WITH_EXCHANGE_RATE"
    val PREFERENCES_NAME = "CURRENCY_CONVERTER"

    fun customPreference(context: Context): SharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    var SharedPreferences.lastUpdateTime
        get() = getLong(LAST_UPDATE_TIME, 0)
        set(value) {
            editMe {
                it.putLong(LAST_UPDATE_TIME, value)
            }
        }

    var SharedPreferences.currencies
        get() = getString(CURRENCIES, null)
        set(value) {
            editMe {
                it.putString(CURRENCIES, value)
            }
        }
    var SharedPreferences.currenciesWithExchangeRate
        get() = getString(CURRENCIES_WITH_EXCHANGE_RATE, null)
        set(value) {
            editMe {
                it.putString(CURRENCIES_WITH_EXCHANGE_RATE, value)
            }
        }

    var SharedPreferences.clearValues
        get() = { }
        set(value) {
            editMe {
                it.clear()
            }
        }
}