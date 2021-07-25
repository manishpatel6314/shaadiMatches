package com.manish.shaadi.helper

import android.content.Context
import android.net.ConnectivityManager

@Suppress("DEPRECATION")
class Constants {
    companion object {
        const val DEBUG_MODE = false
        const val SENSOR_DELAY_IN_MS = 1000
        const val MAX_IMAGE_DIMENSION = 1280
        const val QUALITY_FACTOR = 40
        const val noImagePath = "no-path-found"
        const val APP_NAME = "interview"
        const val DUMMY_EMP_ID = "EI201500001"
        const val TOKEN = "HA7MJZVQXL4e2u8TfELwVxDg65THxNMz"

        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }
}