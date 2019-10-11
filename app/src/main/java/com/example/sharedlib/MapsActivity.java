package com.example.sharedlib;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;


public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        DiscreteScrollView.ScrollStateChangeListener<LibraryAdapter_forMapView.ViewHolder>,
        DiscreteScrollView.OnItemChangedListener<LibraryAdapter_forMapView.ViewHolder>{

    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private CameraPosition mCameraPosition;

    private ArrayList<Library_forMapView> lib_list = new ArrayList<>();
    private ArrayList<Marker> marker_list;
    private DiscreteScrollView scrollView;
    Marker marker_current;
    LatLng erc_position = new LatLng(-37.799338, 144.962832);
    LatLng baillieu_position = new LatLng(-37.798391, 144.959406);
    LatLng architecture_position = new LatLng(-37.797412, 144.962939);
    LatLng giblin_position = new LatLng(-37.801277, 144.959312);

    String userName_me;



    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(-37.796771, 144.961306);
    // 获取不到地点时，默认地点在unihouse
    private static final int DEFAULT_ZOOM = 16;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    private Location mLastKnownLocation;

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_map);


        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Build the map.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_activityMap);
        mapFragment.getMapAsync(this);

        Intent parentIntent = getIntent();
        userName_me = parentIntent.getStringExtra("userName");


    }

    @Override
    public void onCurrentItemChanged(@Nullable LibraryAdapter_forMapView.ViewHolder holder, int position) {
        //viewHolder will never be null, because we never remove items from adapter's list
        if (holder != null) {
//            forecastView.setForecast(forecasts.get(position));
//            holder.showText();
            marker_current = mMap.addMarker(new MarkerOptions()
                    .position(lib_list.get(position).getLocation())
                    .title(lib_list.get(position).getName())
                    .draggable(false));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lib_list.get(position).getLocation(), DEFAULT_ZOOM));
        }
    }

    @Override
    public void onScrollStart(@NonNull LibraryAdapter_forMapView.ViewHolder holder, int position) {
        marker_current.remove();
    }

    @Override
    public void onScroll(
            float position,
            int currentIndex, int newIndex,
            @Nullable LibraryAdapter_forMapView.ViewHolder currentHolder,
            @Nullable LibraryAdapter_forMapView.ViewHolder newHolder) {
//        Library_forMapView current = lib_list.get(currentIndex);
//        if (newIndex >= 0 && newIndex < scrollView.getAdapter().getItemCount()) {
//            Library_forMapView next = lib_list.get(newIndex);
//            System.out.println("这个地方地图上应该要变化了");
//            forecastView.onScroll(1f - Math.abs(position), current, next);
//        }
    }

    @Override
    public void onScrollEnd(@NonNull LibraryAdapter_forMapView.ViewHolder holder, int position) {
//        marker_current.remove();
//        marker_current = mMap.addMarker(new MarkerOptions()
//                .position(lib_list.get(position).getLocation())
//                .title(lib_list.get(position).getName())
//                .draggable(false));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lib_list.get(position).getLocation(), DEFAULT_ZOOM));
    }


    /**
     * Saves the state of the map when the activity is paused.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }

    private void showScrollView()
    {
        scrollView = findViewById(R.id.libPicker_activityMap);
        scrollView.setSlideOnFling(true);
        scrollView.addOnItemChangedListener(this);
        scrollView.addScrollStateChangeListener(this);
        scrollView.scrollToPosition(1);
        scrollView.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());

        marker_list = new ArrayList<>();
        Library_forMapView lib_erc = new Library_forMapView("ERC", 50, R.drawable.erc, getDistance(erc_position), erc_position);
        Library_forMapView lib_86 = new Library_forMapView("Baillieu", 60, R.drawable.bailieu, getDistance(baillieu_position), baillieu_position);
        Library_forMapView lib_architecture = new Library_forMapView("Architecture", 50, R.drawable.architecture, getDistance(architecture_position), architecture_position);
        Library_forMapView lib_giblin = new Library_forMapView("Giblin", 50, R.drawable.giblin, getDistance(giblin_position), giblin_position);

        lib_list.add(lib_erc);
        lib_list.add(lib_86);
        lib_list.add(lib_architecture);
        lib_list.add(lib_giblin);

        scrollView.setAdapter(new LibraryAdapter_forMapView(lib_list, MapsActivity.this, userName_me));
    }


    /**
     * Manipulates the map when it's available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        // Prompt the user for permission.
        getLocationPermission();

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();


    }

    private float getDistance(LatLng goalLocation)
    {
        float[] results = new float[1];
        if (mLastKnownLocation != null) {
            System.out.println("知道现在的位置了，正常工作");
            Location.distanceBetween(goalLocation.latitude, goalLocation.longitude,
                    mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude(), results);
        }
        else
        {
            System.out.println("没得到现在的位置，不知道为啥，之后再排查吧");
            Location.distanceBetween(goalLocation.latitude, goalLocation.longitude,
                    mDefaultLocation.latitude, mDefaultLocation.longitude, results);
        }
        return results[0];
    }

    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            showScrollView();
                        } else {
                            System.out.println("虽然已经获得权限，但获取不到当前位置");
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    /**
     * Prompts the user for permission to use the device location.
     */
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }


    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

}
