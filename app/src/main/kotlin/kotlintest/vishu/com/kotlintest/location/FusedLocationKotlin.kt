package kotlintest.vishu.com.kotlintest.location

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

import java.util.HashMap

class FusedLocationKotlin private constructor(context: Context) : LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    var isGoogleServicesAvailable: Boolean = false
    internal var mCurrentLocation: Location? = null
    private var mFusedLocationInterface: FusedLocationInterface? = null
    private var mGoogleApiClient: GoogleApiClient? = null
    internal var mLocationRequest: LocationRequest? = null

    init {
        mGoogleApiClient = null
        isGoogleServicesAvailable = false
        createLocationRequest()
        initGoogleApiClient(context)
    }

    private fun initGoogleApiClient(context: Context) {
        if (!isGooglePlayServicesAvailable(context)) {
            Toast.makeText(context, "Cannot Auto Locate", Toast.LENGTH_SHORT).show()
            isGoogleServicesAvailable = false
            return
        } else {
            isGoogleServicesAvailable = true
            mGoogleApiClient = GoogleApiClient.Builder(context).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build()
            return
        }
    }

    private fun isGooglePlayServicesAvailable(context: Context): Boolean {
        return GooglePlayServicesUtil.isGooglePlayServicesAvailable(context) == 0
    }

    protected fun createLocationRequest() {
        mLocationRequest = LocationRequest()
        mLocationRequest?.setInterval(1000L)
        mLocationRequest?.setFastestInterval(500L)
        mLocationRequest?.setPriority(100)
    }

    fun getFusedLocation(fusedlocationinterface: FusedLocationInterface) {
        mFusedLocationInterface = fusedlocationinterface
        if (mGoogleApiClient != null) {
            mGoogleApiClient!!.connect()
            return
        } else {
            fusedlocationinterface.HashLocation(null)
            return
        }
    }

    override fun onConnected(bundle: Bundle?) {
        startLocationUpdates()
    }

    override fun onConnectionFailed(connectionresult: ConnectionResult) {
    }

    override fun onConnectionSuspended(i: Int) {
    }

    override fun onLocationChanged(location: Location) {
        mCurrentLocation = location
        val newLocation = HashMap<String, String>()
        if (mCurrentLocation != null) {
            newLocation.put("lat", mCurrentLocation!!.latitude.toString())
            newLocation.put("lng", mCurrentLocation!!.longitude.toString())
        }
        mFusedLocationInterface!!.HashLocation(newLocation)
        stopLocationUpdates()
    }

    protected fun startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)
    }

    protected fun stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this)
        if (mGoogleApiClient != null && mGoogleApiClient!!.isConnected) {
            mGoogleApiClient!!.disconnect()
        }
    }

    companion object {

        private val FASTEST_INTERVAL: Long = 500
        private val INTERVAL: Long = 1000
        private var mInstance: FusedLocationKotlin? = null

        fun getInstance(context: Context): FusedLocationKotlin? {
            if (mInstance == null) {
                mInstance = FusedLocationKotlin(context)
            }
            return mInstance
        }
    }

}
