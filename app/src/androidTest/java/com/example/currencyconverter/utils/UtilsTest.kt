package com.example.currencyconverter.utils

import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.example.currencyconverter.utils.Utils.readJsonAsset
import org.junit.Assert.*
import org.junit.Test

class UtilsTest {

    @Test
    fun hasInternetConnection() {
     val ctx=  getInstrumentation().context
        val result=  Utils.hasInternetConnection(ctx)
         assertTrue(result)

    }
    @Test
    fun readJsonAsset() {
        val ctx=  getInstrumentation().context
        val result=   ctx.readJsonAsset("currencies.json")
         assertEquals(true,result.length>0)
    }
}