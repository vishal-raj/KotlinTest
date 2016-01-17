package kotlintest.vishu.com.kotlintest.location;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.HashMap;

public class FusedLocation
        implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final long FASTEST_INTERVAL = 500;
    private static final long INTERVAL = 1000;
    private static FusedLocation mInstance = null;
    public boolean isGoogleServicesAvailable;
    Location mCurrentLocation;
    private FusedLocationInterface mFusedLocationInterface;
    private GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;

    private FusedLocation(Context context) {
        mGoogleApiClient = null;
        isGoogleServicesAvailable = false;
        createLocationRequest();
        initGoogleApiClient(context);
    }

    public static FusedLocation getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new FusedLocation(context);
        }
        return mInstance;
    }

    private void initGoogleApiClient(Context context) {
        if (!isGooglePlayServicesAvailable(context)) {
            Toast.makeText(context, "Cannot Auto Locate", Toast.LENGTH_SHORT).show();
            isGoogleServicesAvailable = false;
            return;
        } else {
            isGoogleServicesAvailable = true;
            mGoogleApiClient = (new GoogleApiClient.Builder(context)).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
            return;
        }
    }

    private boolean isGooglePlayServicesAvailable(Context context) {
        return GooglePlayServicesUtil.isGooglePlayServicesAvailable(context) == 0;
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000L);
        mLocationRequest.setFastestInterval(500L);
        mLocationRequest.setPriority(100);
    }

    public void getFusedLocation(FusedLocationInterface fusedlocationinterface) {
        mFusedLocationInterface = fusedlocationinterface;
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
            return;
        } else {
            fusedlocationinterface.HashLocation(null);
            return;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionresult) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        HashMap<String, String> newLocation = new HashMap();
        if (mCurrentLocation != null) {
            newLocation.put("lat", String.valueOf(mCurrentLocation.getLatitude()));
            newLocation.put("lng", String.valueOf(mCurrentLocation.getLongitude()));
        }
        mFusedLocationInterface.HashLocation(newLocation);
        stopLocationUpdates();
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

}
