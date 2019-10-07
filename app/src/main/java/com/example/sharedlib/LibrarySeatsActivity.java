package com.example.sharedlib;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class LibrarySeatsActivity extends BaseActivity {

    private EditText seatsPercentageEditText;
    private Calendar calendars;

    private DatabaseReference mDatabase;
    private ArrayList commentList = new ArrayList<ComWithDatabase>();

    private ObtainCurrentDate obtainCurrentDate = new ObtainCurrentDate();
    ;

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

        //Read from database
        mDatabase = FirebaseDatabase.getInstance().getReference();
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
        ref.limitToFirst(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                commentList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    String content = postSnapshot.getValue().toString();
//                    commentList.add(content);
                    ComWithDatabase comment = postSnapshot.getValue(ComWithDatabase.class);
                    commentList.add(comment);
                }
                Log.d("Database content", commentList.toString());
                Collections.reverse(commentList);   // displayed by upload date
                ArrayList temp = new ArrayList();

                for (int i = 0; i < commentList.size(); i++) {
                    ComWithDatabase tempObj = (ComWithDatabase) commentList.get(i);
                    String record = "Seat occupancy: " + tempObj.getComment() + "%" + "\t" + "upload date: " + tempObj.getDate() + "\t" + "uploaded by: " + tempObj.getUser();
                    if (timeDifference(obtainCurrentDate.getDateAndTime(), tempObj.getDate()) < 1) {
                        temp.add(record);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(LibrarySeatsActivity.this, android.R.layout.simple_list_item_1, temp);
                ListView listView = findViewById(R.id.seatsComments);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Database error", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

        seatsPercentageEditText = findViewById(R.id.text_seats_available_libraryseats);

        Button upload = findViewById(R.id.button_update_libraryseats);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String seatsPercentage = seatsPercentageEditText.getText().toString();
                int percentage = Integer.parseInt(seatsPercentage);
                if (percentage >= 0 && percentage <= 100) {
                    seatsPercentageEditText.setText("");

                    String date = obtainCurrentDate.getDateAndTime();
                    ComWithDatabase comment = new
                            ComWithDatabase(userName, String.valueOf(percentage), date);

                    mDatabase.child("searchSeats").child("location").child(locationTextView.getText().toString()).push().setValue(comment);
                    Toast.makeText(LibrarySeatsActivity.this, "Upload successfully.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LibrarySeatsActivity.this, "The input data should between 0 and 100",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public long timeDifference(String time1, String time2) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        try {
            Date d1 = df.parse(time1);
            Date d2 = df.parse(time2);
            long diff = d1.getTime() - d2.getTime();
            long hour = diff / (1000 * 60 * 60);
            Log.v("时间差", String.valueOf(diff));
            Log.v("时间差2", String.valueOf(hour));
            return hour;
        } catch (Exception e) {
            return -1;
        }
    }
}
