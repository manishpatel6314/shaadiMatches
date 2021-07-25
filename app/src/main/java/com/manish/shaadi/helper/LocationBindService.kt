package com.manish.shaadi.helper

import android.Manifest
import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import java.util.*



@Suppress("DEPRECATION")
class LocationBindService : Service(),
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener,
    LocationListener {

    private val binder = LocalBinder()
    private var prefManager: PrefManager? = null

    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): LocationBindService = this@LocationBindService
    }

    override fun onBind(intent: Intent): IBinder {
        Logger.e(Thread.currentThread(), "On Bind")
        mGoogleApiClient?.connect()
        return binder
    }


    override fun onConnected(p0: Bundle?) {
        try {

            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
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

    override fun onConnectionSuspended(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Logger.e(Thread.currentThread(), "Location - onConnectionFailed")

        if (p0.hasResolution()) {
            try {
                p0.startResolutionForResult(
                    applicationContext as Activity,
                    CONNECTION_FAILURE_RESOLUTION_REQUEST
                )
            } catch (e: IntentSender.SendIntentException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()

            }
        } else {
            when {
                p0.errorCode == 1 -> {
                    val location = Location("locationProvider")
                    location.latitude = 22.574000
                    location.longitude = 75.744300
                    onLocationChanged(location)
                }
            }
        }
    }


    override fun onLocationChanged(p0: Location?) {
        mLastLocation = p0


        if (p0 != null) {
            prefManager?.setLocation(p0.latitude.toString(), p0.longitude.toString())

            Logger.e(
                Thread.currentThread(),
                "LocationBindService latitude ${p0.latitude} longitude ${p0.longitude}"
            )

            if (Constants.isNetworkAvailable(application)) {
                try {
                    val geocode = Geocoder(application, Locale.getDefault())
                    val addresses: List<Address> =
                        geocode.getFromLocation(p0.latitude, p0.longitude, 1)
                    if (addresses.isNotEmpty()) {
                        val address: String? = addresses[0].getAddressLine(0)
                        val country: String? = addresses[0].countryName

                        prefManager?.userAddressAndCountry(address = address, country = country)

                        Logger.e(
                            Thread.currentThread(),
                            "LocationBindService address $address \ncountry $country "
                        )
                    }
                } catch (e: Exception) {
                    if (Constants.DEBUG_MODE)
                        Logger.e(Thread.currentThread(), "exception $e")
                }
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        Logger.e(Thread.currentThread(), "onCreate")

        prefManager = PrefManager.getInstance(applicationContext)
        latLonTask(applicationContext)
    }

    override fun unbindService(conn: ServiceConnection) {
        super.unbindService(conn)
        Logger.e(Thread.currentThread(), "unbindService")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.e(Thread.currentThread(), "onDestroyService")
    }


    private fun latLonTask(activity: Context) {
        Logger.e(Thread.currentThread(), " ThreadName ${Thread().name}")

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


    companion object {

        private var mLastLocation: Location? = null
        private var mGoogleApiClient: GoogleApiClient? = null
        private var mLocationRequest: LocationRequest? = null
        private const val CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000
    }

}