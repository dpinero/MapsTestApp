package com.mycompany.mapstestapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

public class MapsActivity extends FragmentActivity {
    final private int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION=123;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //request permissions, because google wants to look less shady when they ask for a billion permissions when you download an app
        if (ContextCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {


                ActivityCompat.requestPermissions(MapsActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    setUpMapIfNeeded();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        Double latitude;
        Double longitude;
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = lm.getBestProvider(criteria, false);
        Location loc = lm.getLastKnownLocation(bestProvider);
        latitude = loc.getLatitude();
        longitude = loc.getLongitude();
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(latitude,
                longitude));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(4);
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Marker"));
        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
    }


    public void getAntipode(View view) {
        Double latitude;
        Double longitude;
        Double antilat;
        Double antilong;
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = lm.getBestProvider(criteria, false);
        Location loc = lm.getLastKnownLocation(bestProvider);
        latitude = loc.getLatitude();
        longitude = loc.getLongitude();
        antilat=-latitude;
        if(longitude>=0){
            antilong=longitude-180;
        }
        else{
            antilong=longitude+180;
        }
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(antilat,
                antilong));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(4);
        mMap.addMarker(new MarkerOptions().position(new LatLng(antilat, antilong)).title("Marker"));
        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
    }
}
