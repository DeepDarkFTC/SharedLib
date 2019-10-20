package com.example.sharedlib;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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

    private SeekBar seekBar;
    private TextView percentageTextView;

    private DatabaseReference mDatabase;
    private ArrayList commentList = new ArrayList<ComWithDatabase>();

    private ObtainCurrentDate obtainCurrentDate = new ObtainCurrentDate();

    private int seatsPercentage;


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

        //Read from database
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        SnapshotParser<SearchSeatsComment> parser = new SnapshotParser<SearchSeatsComment>() {
//            @Override
//            public SearchSeatsComment parseSnapshot(DataSnapshot dataSnapshot) {
//                SearchSeatsComment content = dataSnapshot.getValue(SearchSeatsComment.class);
//                if (content != null) {
//                    content.setId(dataSnapshot.getKey());
//                }
//
//                Log.d("Database content", content.getComment());
//
//                return content;
//
//            }
//        };

        DatabaseReference ref = mDatabase.child("searchSeats").child("location").child(locationTextView.getText().toString());
        ref.limitToLast(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                commentList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    String content = postSnapshot.getValue().toString();
//                    commentList.add(content);
                    ComWithDatabase comment = postSnapshot.getValue(ComWithDatabase.class);
                    commentList.add(0,comment);
                }
                Log.d("Database content", commentList.toString());
                Collections.reverse(commentList);   // displayed by upload date
                ArrayList<String> temp = new ArrayList<String>();

                for (int i = 0; i < commentList.size(); i++) {
                    ComWithDatabase tempObj = (ComWithDatabase) commentList.get(i);
                    String record = "location: " + locationTextView.getText().toString() + "____"
                            + "Seat occupancy: " + tempObj.getComment() + "%" + "____"
                            + "upload date: " + tempObj.getDate() + "____"
                            + "uploaded by: " + tempObj.getUser() + "____"
                            + "thumb num: " + tempObj.getThumbNumber() + "____"
                            + "key: " + tempObj.getId();
                    if (timeDifference(obtainCurrentDate.getDateAndTime(), tempObj.getDate()) < 1) {
                        temp.add(0,record);
                    }
                }
                int len = temp.size();
                Log.d("seat", String.valueOf(len));

                ListView listView = findViewById(R.id.seatsComments);
                SeatAdapter adapter = new SeatAdapter(temp,listView);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Database error", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

//        seatsPercentageEditText = findViewById(R.id.text_seats_available_libraryseats);
        seekBar = findViewById(R.id.seekBar_seatsavailable_libraryseats);
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

        Button upload = findViewById(R.id.button_update_libraryseats);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = mDatabase.child("searchSeats").child("location").child(locationTextView.getText().toString()).push().getKey();
                String date = obtainCurrentDate.getDateAndTime();
                ComWithDatabase comment = new
                        ComWithDatabase(key, userName, String.valueOf(seatsPercentage), date, "0");

                mDatabase.child("searchSeats").child("location").child(locationTextView.getText().toString()).child(key).setValue(comment);
                Toast.makeText(LibrarySeatsActivity.this, "Upload successfully.",
                        Toast.LENGTH_SHORT).show();

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

    public static long timeDifference(String time1, String time2) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

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
}
