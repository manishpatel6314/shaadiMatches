package com.manish.shaadi.helper

import android.content.Context
import android.content.SharedPreferences

class PrefManager private constructor(context: Context) {

    private val pref: SharedPreferences = context.getSharedPreferences(PREF_NAME, 0)

    var lat: String?
        get() = pref.getString(ARG_LAT, null)
        set(value) = pref.edit().putString(ARG_LAT, value).apply()

    var lon: String?
        get() = pref.getString(ARG_LON, null)
        set(value) = pref.edit().putString(ARG_LON, value).apply()

    var userAddress: String?
        get() = pref.getString(ARG_ADDRESS, null)
        set(value) = pref.edit().putString(ARG_ADDRESS, value).apply()

    var userCountry: String?
        get() = pref.getString(ARG_COUNTRY, null)
        set(value) = pref.edit().putString(ARG_COUNTRY, value).apply()

    fun setLocation(latValue: String?, lonValue: String?) {
        lat = latValue
        lon = lonValue
    }

    fun userAddressAndCountry(address: String?, country: String?) {
        userAddress = address
        userCountry = country
    }


    companion object {
        const val PREF_NAME = "prefManager"
        const val ARG_LAT = "lat"
        const val ARG_LON = "lon"
        const val ARG_ADDRESS = "address"
        const val ARG_COUNTRY = "country"

        @Volatile
        var manager: PrefManager? = null

        @Synchronized
        fun getInstance(context: Context): PrefManager {
            if (manager == null)
                manager = PrefManager(context)
            return manager!!
        }
    }
}