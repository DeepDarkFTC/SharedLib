package com.example.sharedlib.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.sharedlib.Object.ComWithDatabase;
import com.example.sharedlib.Object.ObtainCurrentDate;
import com.example.sharedlib.Object.SeatAdapter;
import com.example.sharedlib.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class LibrarySeatsActivity extends BaseActivity {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    LatLng erc_position = new LatLng(-37.799338, 144.962832);
    LatLng baillieu_position = new LatLng(-37.798391, 144.959406);
    LatLng architecture_position = new LatLng(-37.797412, 144.962939);
    LatLng giblin_position = new LatLng(-37.801277, 144.959312);
    private TextView percentageTextView;
    private DatabaseReference mDatabase;
    private ArrayList commentList = new ArrayList<ComWithDatabase>();
    private ObtainCurrentDate obtainCurrentDate = new ObtainCurrentDate();
    private int seatsPercentage;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private boolean mLocationPermissionGranted;
    private Location mLastKnownLocation;

    public static long timeDifference(String time1, String time2) {

        // set date format
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        // calculate the difference of the two dates
        try {
            Date d1 = df.parse(time1);
            Date d2 = df.parse(time2);
            long diff = d1.getTime() - d2.getTime();
            long hour = diff / (1000 * 60 * 60);
            return hour;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_seats);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent parentIntent = getIntent();
        final String userName = parentIntent.getStringExtra("userName");
        String location = parentIntent.getStringExtra("location");

        TextView userNameTextView = findViewById(R.id.text_username_libraryseats);
        userNameTextView.setText(userName);

        final TextView locationTextView = findViewById(R.id.information);
        locationTextView.setText(location);

        percentageTextView = findViewById(R.id.progressNum);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Prompt the user for permission.
        getLocationPermission();
        // Get the current location of the device and set the position of the map.
        getDeviceLocation();

        //Read from database
        DatabaseReference ref = mDatabase.child("searchSeats").child("location").child(locationTextView.getText().toString());
        ref.limitToLast(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ComWithDatabase comment = postSnapshot.getValue(ComWithDatabase.class);
                    commentList.add(0, comment);
                }
                Log.d("Database content", commentList.toString());
                Collections.reverse(commentList);   // displayed by upload date
                ArrayList<String> data = new ArrayList<String>();   // record from firebase

                ArrayList<String> displayData = new ArrayList<>();  // set the record to be displayed

                for (int i = 0; i < commentList.size(); i++) {
                    ComWithDatabase tempObj = (ComWithDatabase) commentList.get(i);
                    String record = "location: " + locationTextView.getText().toString() + "____"
                            + "Seat occupancy: " + tempObj.getComment() + "%" + "____"
                            + "upload date: " + tempObj.getDate() + "____"
                            + "uploaded by: " + tempObj.getUser() + "____"
                            + "thumb num: " + tempObj.getThumbNumber() + "____"
                            + "key: " + tempObj.getId();

                    String displayRecord = "Seat occupancy: " + tempObj.getComment() + "%" + "\n"
                            + "upload date: " + tempObj.getDate() + "\n"
                            + "uploaded by: " + tempObj.getUser() + "\n";

                    if (timeDifference(obtainCurrentDate.getDateAndTime(), tempObj.getDate()) < 1) {
                        data.add(0, record);
                        displayData.add(0, displayRecord);
                    }
                }
                int len = data.size();
                Log.d("seat", String.valueOf(len));

                ListView listView = findViewById(R.id.seatsComments);
                SeatAdapter adapter = new SeatAdapter(data, displayData, listView);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Database error", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

        SeekBar seekBar = findViewById(R.id.seekBar_seatsavailable_libraryseats);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                percentageTextView.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seatsPercentage = seekBar.getProgress();
            }
        });

        // upload button implementation
        Button upload = findViewById(R.id.button_update_libraryseats);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String libraryCommand = locationTextView.getText().toString().split(" ")[0];

                if (checkInLib(libraryCommand)) {
                    String key = mDatabase.child("searchSeats").child("location").child(locationTextView.getText().toString()).push().getKey();
                    String date = obtainCurrentDate.getDateAndTime();
                    ComWithDatabase comment = new
                            ComWithDatabase(key, userName, String.valueOf(seatsPercentage), date, "0");

                    mDatabase.child("searchSeats").child("location").child(locationTextView.getText().toString()).child(key).setValue(comment);
                    Toast.makeText(LibrarySeatsActivity.this, "Upload successfully.",
                            Toast.LENGTH_SHORT).show();

                } else {
                    new AlertDialog.Builder(LibrarySeatsActivity.this)
                            .setTitle("Upload failed")
                            .setMessage("You are not located in this library.")
                            .setPositiveButton("ok", null)
                            .show();
                }
            }

        });

        Button logoutButton = findViewById(R.id.button_logout_libraryseats);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutMethod(LibrarySeatsActivity.this);
            }
        });

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

    // get the distance between current location and library
    private float getDistance(LatLng goalLocation) {
        float[] results = new float[1];
        if (mLastKnownLocation != null) {
            Location.distanceBetween(goalLocation.latitude, goalLocation.longitude,
                    mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude(), results);
        }
        return results[0];
    }

    // check the current location
    private boolean checkInLib(String command) {
        int distance_to_lib;
        switch (command) {
            case "Erc":
                distance_to_lib = (int) getDistance(erc_position);
                if (distance_to_lib <= 30)
                    return true;
                break;
            case "Baillieu":
                distance_to_lib = (int) getDistance(baillieu_position);
                if (distance_to_lib <= 30)
                    return true;
                break;
            case "Architecture":
                distance_to_lib = (int) getDistance(architecture_position);
                if (distance_to_lib <= 30)
                    return true;
                break;
            case "Giblin":
                distance_to_lib = (int) getDistance(giblin_position);
                if (distance_to_lib <= 30)
                    return true;
                break;
            default:
                break;
        }
        return false;
    }
}
