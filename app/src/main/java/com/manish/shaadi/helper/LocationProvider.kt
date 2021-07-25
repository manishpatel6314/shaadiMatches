package com.manish.shaadi.helper

import android.annotation.SuppressLint
import android.app.Activity
import android.content.IntentSender
import android.location.Address
import android.location.Location
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import android.location.Geocoder
import java.util.*


@Suppress("DEPRECATION")
class LocationProvider(var activity: Activity) : LifecycleObserver,
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener,
    LocationListener {
    private var prefManager: PrefManager? = null


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        //..
        Logger.e(Thread.currentThread(), "On Create")
        prefManager = PrefManager.getInstance(activity)
        latLonTask(activity)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Logger.e(Thread.currentThread(), "On Start")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        Logger.e(Thread.currentThread(), "On Stop")

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Logger.e(Thread.currentThread(), "On Pause")
        if (mGoogleApiClient?.isConnected!!) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this)
            mGoogleApiClient?.disconnect()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Logger.e(Thread.currentThread(), "On Resume")
        mGoogleApiClient?.connect()

    }

    private fun latLonTask(activity: Activity) {
        mGoogleApiClient = GoogleApiClient.Builder(activity)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval((10 * 1000).toLong())        // 10 seconds, in milliseconds
            .setFastestInterval(1000) // 1 second, in milliseconds
    }


    @SuppressLint("MissingPermission")
    override fun onConnected(bundle: Bundle?) {

        try {

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)
            if (mLastLocation != null)
                prefManager?.setLocation(
                    mLastLocation?.latitude.toString(),
                    mLastLocation?.longitude.toString()
                )
            LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient,
                mLocationRequest,
                this
            )

        } catch (e: Exception) {
            e.printStackTrace()
            val location = Location("dummyprovider")
            location.latitude = 28.507235
            location.longitude = 77.083144
            onLocationChanged(location)
        }
    }

    override fun onConnectionSuspended(code: Int) {
        /** do nothing **/
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Logger.e(Thread.currentThread(), "Location - onConnectionFailed")

        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(
                    activity,
                    CONNECTION_FAILURE_RESOLUTION_REQUEST
                )
            } catch (e: IntentSender.SendIntentException) {
                e.printStackTrace()
            }
        } else {
            when {
                connectionResult.errorCode == 2 -> GooglePlayServicesUtil.getErrorDialog(
                    2,
                    activity,
                    0
                ).show()
                connectionResult.errorCode == 1 -> {
                    val location = Location("locationProvider")
                    location.latitude = 22.574000
                    location.longitude = 75.744300
                    onLocationChanged(location)
                }
                connectionResult.errorCode == 3 -> GooglePlayServicesUtil.getErrorDialog(
                    3,
                    activity,
                    0
                ).show()
            }
        }
    }

    override fun onLocationChanged(location: Location?) {
        mLastLocation = location


        if (location != null) {
            prefManager?.setLocation(location.latitude.toString(), location.longitude.toString())
            if (Constants.isNetworkAvailable(activity)) {
                try {
                    val geocode = Geocoder(activity, Locale.getDefault())
                    val addresses: List<Address> =
                        geocode.getFromLocation(location.latitude, location.longitude, 1)
                    if (addresses.isNotEmpty()) {
                        val address: String? = addresses[0].getAddressLine(0)
                        val country: String? = addresses[0].countryName

                        prefManager?.userAddressAndCountry(address = address, country = country)

                        Logger.e(
                            Thread.currentThread(),
                            " address $address \ncountry $country "
                        )
                    }
                } catch (e: Exception) {
                    if (Constants.DEBUG_MODE)
                        Logger.e(Thread.currentThread(), "exception $e")
                }
            }
        }
    }

    companion object {
        private var mLastLocation: Location? = null
        private var mGoogleApiClient: GoogleApiClient? = null
        private var mLocationRequest: LocationRequest? = null
        private const val CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000
    }
}