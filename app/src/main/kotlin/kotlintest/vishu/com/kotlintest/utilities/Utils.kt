package kotlintest.vishu.com.kotlintest.utilities

import android.content.Context
import android.content.SharedPreferences
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkInfo



class Utils(internal var mContext: Context) {
    internal var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = mContext.getSharedPreferences(MyConstants.MY_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun canGetLocation(): Boolean {
        val locationmanager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val gps = locationmanager.isProviderEnabled("gps")
        val network = locationmanager.isProviderEnabled("network")
        return java.lang.Boolean.valueOf(gps) || java.lang.Boolean.valueOf(network)
    }

    fun saveLocation(lat: String, lng: String) {
        val editor = sharedPreferences.edit()
        editor.putString(MyConstants.LAT, lat)
        editor.putString(MyConstants.LNG, lng)
        editor.commit()
    }

    fun saveDetails(cityId: String, zoneId: String) {
        val editor = sharedPreferences.edit()
        editor.putString(MyConstants.CITY_ID, cityId)
        editor.putString(MyConstants.ZONE_ID, zoneId)
        editor.commit()
    }

    /*fun tokenData(): String {
        return MyConstants.TOKEN_DATA = sharedPreferences.getString(MyConstants.TOKEN, "null")
    }*/

    fun saveCreds(mobile: String, token: String) {
        val editor = sharedPreferences.edit()
        editor.putString(MyConstants.MOBILE, mobile)
        editor.putString(MyConstants.TOKEN, token)
        editor.commit()
    }

    fun addBoolValue(s: String, boolean1: Boolean?) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(s, boolean1!!)
        editor.commit()
    }

    fun getBoolValue(s: String): Boolean {
        return sharedPreferences.getBoolean(s, false)
    }

    companion object {
        var mUtils: Utils? = null

        fun getInstance(context: Context): Utils? {
            if (mUtils == null) {
                mUtils = Utils(context)
            }
            return mUtils
        }

        fun checkforNetworkconnection(context: Context): Boolean {
            val connmgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mobile = connmgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            val wifinetwork = connmgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI)

            try {
                if (wifinetwork.isConnected && wifinetwork.isAvailable) {
                    return true
                } else {
                    if (mobile.isConnected && mobile.isAvailable) {
                        return true
                    }
                }
            } catch (npe: NullPointerException) {
                npe.printStackTrace()
            }

            return false
        }

        fun isValidMobileNumber(s: String): Boolean {
            var flag: Boolean
            flag = false
            if (s.length >= 1) {
                if (s[0] == '7' || s[0] == '8' || s[0] == '9') {
                    flag = true
                } else {
                    flag = false
                }
            }
            return flag
        }
    }

}
