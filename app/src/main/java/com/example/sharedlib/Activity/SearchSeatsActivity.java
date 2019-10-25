package com.example.sharedlib.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.sharedlib.Object.ComWithDatabase;
import com.example.sharedlib.Object.OverallSeatsSituation;
import com.example.sharedlib.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchSeatsActivity extends BaseActivity {

    private TextView userNameTextView;
    private OverallSeatsSituation overallSeatsSituation = new OverallSeatsSituation();
    private ArrayList commentList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_seats);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent parentIntent = getIntent();
        String userName = parentIntent.getStringExtra("userName");

        userNameTextView = findViewById(R.id.text_username_searchseats);
        userNameTextView.setText(userName);

        // 4 library buttons
        ImageButton architectureLibrary = findViewById(R.id.button_arc_searchseats);
        architectureLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchSeatsActivity.this,
                        ArchitectureLibraryActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                startActivity(intent);
            }
        });

        final TextView arcSeatsTextView = findViewById(R.id.text_arc_seats);

        ImageButton bailieuLibrary = findViewById(R.id.button_bai_searchseats);
        bailieuLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchSeatsActivity.this,
                        BaillieuLibraryActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                startActivity(intent);
            }
        });

        final TextView baiSeatsTextView = findViewById(R.id.text_bai_seats);

        ImageButton ercLibrary = findViewById(R.id.button_erc_searchseats);
        ercLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchSeatsActivity.this,
                        ErcLibraryActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                startActivity(intent);
            }
        });

        final TextView ercSeatsTextView = findViewById(R.id.text_erc_seats);

        ImageButton giblinLibrary = findViewById(R.id.button_giblin_searchseats);
        giblinLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchSeatsActivity.this,
                        GiblinLibraryActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                startActivity(intent);
            }
        });

        final TextView gibSeatsTextView = findViewById(R.id.text_gib_seats);

        Button logoutButton = findViewById(R.id.button_logout_searchseats);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutMethod(SearchSeatsActivity.this);
            }
        });

        // exchange data to firebase
        DatabaseReference ref = mDatabase.child("libraryOccupation");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ComWithDatabase comment = new ComWithDatabase(postSnapshot.getKey(),
                            postSnapshot.getValue().toString());
                    commentList.add(0, comment);
                }
                Log.d("Database content", commentList.toString());
                ArrayList temp = new ArrayList();
                double arcData = 0.0;
                double baiData = 0.0;
                double ercData = 0.0;
                double gibData = 0.0;
                for (int i = 0; i < commentList.size(); i++) {
                    ComWithDatabase tempObj = (ComWithDatabase) commentList.get(i);
                    temp.add(tempObj.getComment() + " " + tempObj.getDate());
                    String record = temp.get(i).toString();
                    String[] infoSegment = record.split(" ");
                    if (infoSegment[0].equals("Architecture")) {
                        arcData += Integer.parseInt(infoSegment[4]);
                    }
                    if (infoSegment[0].equals("Baillieu")) {
                        baiData += Integer.parseInt(infoSegment[4]);
                    }
                    if (infoSegment[0].equals("Erc")) {
                        ercData += Integer.parseInt(infoSegment[4]);
                    }
                    if (infoSegment[0].equals("Giblin")) {
                        gibData += Integer.parseInt(infoSegment[4]);
                    }
                }

                // calculate the occupancy and vacancy
                overallSeatsSituation.setArcSeats(arcData / 5 + "");
                arcSeatsTextView.setText("Occupancy: " + overallSeatsSituation.getArcSeats() + "%"
                        + "     " + "Vacancy: " + (100 - Double.parseDouble(overallSeatsSituation.getArcSeats())) + "%");

                overallSeatsSituation.setBaiSeats(baiData / 5 + "");
                baiSeatsTextView.setText("Occupancy: " + overallSeatsSituation.getBaiSeats() + "%"
                        + "     " + "Vacancy: " + (100 - Double.parseDouble(overallSeatsSituation.getBaiSeats())) + "%");

                overallSeatsSituation.setErcSeats(ercData / 5 + "");
                ercSeatsTextView.setText("Occupancy: " + overallSeatsSituation.getErcSeats() + "%"
                        + "     " + "Vacancy: " + (100 - Double.parseDouble(overallSeatsSituation.getErcSeats())) + "%");

                overallSeatsSituation.setGibSeats(gibData / 5 + "");
                gibSeatsTextView.setText("Occupancy: " + overallSeatsSituation.getGibSeats() + "%"
                        + "     " + "Vacancy: " + (100 - Double.parseDouble(overallSeatsSituation.getGibSeats())) + "%");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Database error", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

    }
}
