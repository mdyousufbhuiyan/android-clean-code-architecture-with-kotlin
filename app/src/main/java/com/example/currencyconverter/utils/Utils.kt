package com.example.currencyconverter.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.currencyconverter.data.PreferenceHelper
import com.example.currencyconverter.data.PreferenceHelper.lastUpdateTime
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

object Utils {

    fun hasInternetConnection(context: Context?): Boolean {
        if (context == null)
            return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    @Throws(IOException::class)
    fun Context.readJsonAsset(fileName: String): String {
        val inputStream = assets.open(fileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return String(buffer, Charsets.UTF_8)
    }

    fun shouldUpdateData(
        lastUpdateTimeInMillis: Long
    ): Boolean {
        val difference:Long = System.currentTimeMillis() - lastUpdateTimeInMillis
        val mins = (difference / (1000 * 60) % 60)
        return mins > 30
    }
}