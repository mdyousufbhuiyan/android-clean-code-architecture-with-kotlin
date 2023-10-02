package com.example.currencyconverter.utils

import org.junit.Assert.*

import org.junit.Test

class UtilsTest {

    @Test
    fun `check if time difference is less then or equal 30`() {
        var lastUpdatedTIme = System.currentTimeMillis()
        var result = Utils.shouldUpdateData(lastUpdatedTIme)
        assertFalse(result)
    }
    @Test
    fun `check if time difference is getter  then 30`() {
        var lastUpdatedTIme = System.currentTimeMillis() - (31 * 60 * 1000)
        var result = Utils.shouldUpdateData(lastUpdatedTIme)
        assertTrue(result)
    }
}