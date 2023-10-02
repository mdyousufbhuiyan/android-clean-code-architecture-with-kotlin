package com.example.currencyconverter.utils

object InputChecker {

    fun isValidInput(amount: String): Boolean {
        if (amount.isNullOrEmpty())
            return false
        if (amount.toDouble() <= 0)
            return false
        return true
    }
}