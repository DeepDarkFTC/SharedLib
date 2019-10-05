package com.example.sharedlib;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.TimeZone;

public class LibrarySeatsActivity extends BaseActivity {

    private EditText seatsPercentageEditText;
    private Calendar calendars;

    private DatabaseReference mDatabase;
    private ArrayList commentList = new ArrayList<SearchSeatsComment>();


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
        ref.limitToLast(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                commentList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    String content = postSnapshot.getValue().toString();
//                    commentList.add(content);
                    SearchSeatsComment comment = postSnapshot.getValue(SearchSeatsComment.class);
                    commentList.add(comment);
                }
                Log.d("Database content", commentList.toString());
                Collections.reverse(commentList);   // displayed by upload date
                String[] data = new String[commentList.size()];
                for (int i = 0; i < data.length; i++) {
                    SearchSeatsComment tempObj = (SearchSeatsComment) commentList.get(i);
                    data[i] = "Seat occupancy: " + tempObj.getComment() + "%" + "\t" + "upload date: " + tempObj.getDate() + "\t" + "uploaded by: " + tempObj.getUser();
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(LibrarySeatsActivity.this, android.R.layout.simple_list_item_1, data);
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

                    calendars = Calendar.getInstance();
                    calendars.setTimeZone(TimeZone.getTimeZone("GMT+10:00"));

                    String year = String.valueOf(calendars.get(Calendar.YEAR));
                    String month = String.valueOf(calendars.get(Calendar.MONTH) + 1);
                    String day = String.valueOf(calendars.get(Calendar.DATE));

                    String hour = String.valueOf(calendars.get(Calendar.HOUR));
                    String min = String.valueOf(calendars.get(Calendar.MINUTE));
                    String second = String.valueOf(calendars.get(Calendar.SECOND));

                    String isAm = calendars.get(Calendar.AM_PM) == 1 ? "PM" : "AM";
                    Boolean is24 = DateFormat.is24HourFormat(getApplication()) ? true : false;

                    String date = day + "/" + month + "/" + year + " " + hour + ":" + min + ":" + second + " " + isAm;
                    SearchSeatsComment comment = new
                            SearchSeatsComment(userName, String.valueOf(percentage), date);

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
}
