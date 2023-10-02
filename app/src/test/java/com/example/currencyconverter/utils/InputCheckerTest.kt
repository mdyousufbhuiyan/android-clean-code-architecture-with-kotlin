package com.example.currencyconverter.utils

import org.junit.Assert.*

import org.junit.Test

class InputCheckerTest {

    @Test
    fun `check  input for empty string`() {
        val result = InputChecker.isValidInput("")
        assertFalse(result)
    }
    @Test
    fun `check  input for zero`() {
        val result = InputChecker.isValidInput("0")
        assertFalse(result)
    }
    @Test
    fun `check  input for valid number`() {
        val result = InputChecker.isValidInput("1000")
        assertTrue(result)
    }
}