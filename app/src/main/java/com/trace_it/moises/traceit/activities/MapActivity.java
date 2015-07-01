package com.trace_it.moises.traceit.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.trace_it.moises.traceit.R;
import com.trace_it.moises.traceit.business.RouteWsHelper;
import com.trace_it.moises.traceit.domain.Coordinate;

import java.sql.Time;
import java.util.ArrayList;


public class MapActivity extends FragmentActivity implements LocationListener {
    private GoogleMap map;
    private LocationManager locationManager;
    private ArrayList<LatLng> route = new ArrayList<>();
    private ArrayList<Coordinate> coords = new ArrayList<>();
    private PolylineOptions polylineOptions;
    private Location lc;
    private boolean RUNNING = false;
    private RouteWsHelper routeWsHelper = new RouteWsHelper();
    private double distance;
    private TextView txtDistance;
    private Chronometer chronometer;
    private long speed;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        txtDistance = (TextView) findViewById(R.id.txtDistance);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        map = mapFragment.getMap();
        map.setMyLocationEnabled(true);
        polylineOptions = new PolylineOptions();
        lc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        getCurrentLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void getCurrentLocation() {

        map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Location loc = map.getMyLocation();
                if (loc != null)
                    Log.i("Current loc", loc.getLatitude() + " " + loc.getLongitude());
                else
                    Log.i("Current loc", " cant get");
                return false;
            }
        });
    }

    public void centerMapOnMyLocation() {
        LatLng current = route.get(0);
        CameraPosition myPosition = new CameraPosition.Builder()
                .target(current).zoom(17).bearing(90).tilt(30).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(myPosition));
    }

    public void verRuta(View v) {
        polylineOptions.addAll(route);
        polylineOptions.width(12);
        polylineOptions.color(Color.RED);
        map.addPolyline(polylineOptions);
        LatLng start = route.get(0);
        LatLng end = route.get(route.size() - 1);
        distance = getDistance(start.latitude, end.latitude, start.longitude, end.longitude);
        float[] distance2=new float[1];
                Location.distanceBetween(start.latitude,start.longitude,end.latitude,end.longitude,distance2);
        txtDistance.setText("Distancia: " + distance/1000+" otra:"+distance2[0]/1000);
    }

    public double getDistance(double startOne, double endOne, double startTwo, double endTwo) {
        Location locationA = new Location("");
        locationA.setLatitude(startOne);
        locationA.setLongitude(startTwo);
        Location locationB = new Location("");
        locationB.setLatitude(endOne);
        locationB.setLongitude(endTwo);
        double distance = locationA.distanceTo(locationB);
        return distance;
    }

    public void sendData(View v) {
        routeWsHelper.execute(coords);
    }

    public void startTrace(View v) {
        RUNNING = true;
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

    }

    public void stopTrace(View v) {
        RUNNING = false;
        chronometer.stop();
        Log.i("Tiempo ", "" + chronometer.getBase());
        speed = ((long) distance / chronometer.getBase()) / 1000;
        Log.i("Velocidad ", "" + speed);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    @Override
    public void onLocationChanged(Location location) {
        if (RUNNING) {
            route.add(new LatLng(location.getLatitude(), location.getLongitude()));
            coords.add(new Coordinate(location.getLatitude(), location.getLongitude()));
        }
        if (route.size() == 1) {
            centerMapOnMyLocation();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 5, this);
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
