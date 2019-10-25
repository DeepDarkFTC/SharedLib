package com.example.sharedlib.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.sharedlib.Object.ComWithDatabase;
import com.example.sharedlib.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class StudyTimeActivity extends BaseActivity {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    LatLng erc_position = new LatLng(-37.799338, 144.962832);
    LatLng baillieu_position = new LatLng(-37.798391, 144.959406);
    LatLng architecture_position = new LatLng(-37.797412, 144.962939);
    LatLng giblin_position = new LatLng(-37.801277, 144.959312);
    private Chronometer timer;
    private Button start;
    private Button pause;
    private Button restart;
    private Boolean stopFlag = false;
    private long mRecordTime;
    private int lastTime;
    private int totalTime;
    private DatabaseReference mDatabase;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private boolean mLocationPermissionGranted;
    private Location mLastKnownLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_time);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        // Prompt the user for permission.
        getLocationPermission();
        // Get the current location of the device and set the position of the map.
        getDeviceLocation();


        Intent parentIntent = getIntent();
        final String userName = parentIntent.getStringExtra("userName");

        final TextView userNameTextView = findViewById(R.id.text_username_studytime);
        userNameTextView.setText(userName);

        DatabaseReference ref = mDatabase.child("studyTime").child(emailToUid(user.getEmail()));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    lastTime = Integer.parseInt(dataSnapshot.getValue(ComWithDatabase.class).getDate());
                    totalTime = lastTime;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Database error", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

        // Get timer component
        timer = findViewById(R.id.study_time_screen);
        // Get startTimeTextView button
        start = findViewById(R.id.button_start_studytime);
        // Get pause button
        pause = findViewById(R.id.button_pause_studytime);
        // Get continue button
        restart = findViewById(R.id.button_goon_studytime);
        restart.setEnabled(false);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInAnyLib()) {
                    // startTimeTextView timer
                    if (!stopFlag) {
                        timer.setBase(SystemClock.elapsedRealtime());
                        timer.start();
                        pause.setEnabled(true);
                        restart.setEnabled(false);
                        start.setEnabled(false);

                        stopFlag = true;
                    } else {
                        Log.v("current time", timer.getText().toString());
                        int hour = Integer.parseInt(timer.getText().toString().split(":")[0]);
                        int minute = Integer.parseInt(timer.getText().toString().split(":")[1]);
                        int second = Integer.parseInt(timer.getText().toString().split(":")[2]);

                        int currentTime = hour * 3600 + minute * 60 + second;
                        totalTime = lastTime + currentTime;

                        timer.stop();
                        timer.setBase(SystemClock.elapsedRealtime());
                        pause.setEnabled(false);
                        restart.setEnabled(false);
                        start.setEnabled(true);
                        start.setText("Start");

                        stopFlag = false;

                        ComWithDatabase comment = new ComWithDatabase(userName, String.valueOf(totalTime));

                        String userId = emailToUid(user.getEmail());
                        mDatabase.child("studyTime").child(userId).setValue(comment);
                    }
                } else {
                    new AlertDialog.Builder(StudyTimeActivity.this)
                            .setTitle("Warning")
                            .setMessage("You are not located in any library.")
                            .setPositiveButton("ok", null)
                            .show();
                }
            }
        });

        // Pause button listener
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("pause time", timer.getText().toString());
                start.setText("End");
                timer.stop();
                mRecordTime = SystemClock.elapsedRealtime();
                start.setEnabled(true);
                restart.setEnabled(true);
                pause.setEnabled(false);
            }
        });

        // Continue button listener
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setText("End");
                if (mRecordTime != 0) {
                    timer.setBase(timer.getBase() + (SystemClock.elapsedRealtime() - mRecordTime));
                } else {
                    timer.setBase(SystemClock.elapsedRealtime());
                }
                timer.start();
                start.setEnabled(true);
                pause.setEnabled(true);
                restart.setEnabled(false);
            }
        });

        // Binding event listeners for Chronomter
        timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                setFormat(timer);
            }
        });

        Button timeRankButton = findViewById(R.id.button_rankings_studytime);
        timeRankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudyTimeActivity.this, RankingActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("studyTime", String.valueOf(totalTime));
                startActivity(intent);
            }
        });

        Button logoutButton = findViewById(R.id.button_logout_studytime);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutMethod(StudyTimeActivity.this);
            }
        });
    }

    private void setFormat(Chronometer chronometer) {
        int hour = (int) ((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000 / 60 / 60);
        int minute = (int) ((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000 / 60) % 60;
        int second = (int) ((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000) % 60;
        if (hour < 1) {
            if (minute == 59 && second == 59) {
                chronometer.setFormat("0" + "%s");
            } else {
                chronometer.setFormat("0" + String.valueOf(hour) + ":%s");
            }
        } else if (hour < 10) {
            chronometer.setFormat("0" + "%s");
        } else {
            chronometer.setFormat("%s");
        }
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
                        }
                    }
                });
            } else {
                getLocationPermission();
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
    }

    private float getDistance(LatLng goalLocation) {
        float[] results = new float[1];
        if (mLastKnownLocation != null) {
            Location.distanceBetween(goalLocation.latitude, goalLocation.longitude,
                    mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude(), results);
        }
        return results[0];
    }

    private boolean checkInAnyLib() {
        int distance_to_lib;

        distance_to_lib = (int) getDistance(erc_position);
        if (distance_to_lib <= 30)
            return true;
        distance_to_lib = (int) getDistance(baillieu_position);
        if (distance_to_lib <= 30)
            return true;
        distance_to_lib = (int) getDistance(architecture_position);
        if (distance_to_lib <= 30)
            return true;
        distance_to_lib = (int) getDistance(giblin_position);
        if (distance_to_lib <= 30)
            return true;
        return false;
    }

}