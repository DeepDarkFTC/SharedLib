package com.example.sharedlib.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.sharedlib.Object.ComWithDatabase;
import com.example.sharedlib.Object.ObtainCurrentDate;
import com.example.sharedlib.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ArchitectureLibraryActivity extends BaseActivity {

    private TextView userNameTextView;  // display username
    private DatabaseReference mDatabase;
    private ArrayList commentList = new ArrayList<ComWithDatabase>();   // store the firebase data
    private double calResult = 0.0;
    private ObtainCurrentDate obtainCurrentDate = new ObtainCurrentDate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arc_library);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final String libraryName = "Architecture Library";
        final String[] libraryLevel = {"Level 1", "Level 2", "Level 3", "Level 4", "Level 5"};

        Intent parentIntent = getIntent();
        String userName = parentIntent.getStringExtra("userName");

        // set the username
        userNameTextView = findViewById(R.id.text_username_arc);
        userNameTextView.setText(userName);

        // implement 5 library buttons
        Button arcLevel1 = findViewById(R.id.arc_l1);
        arcLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArchitectureLibraryActivity.this,
                        LibrarySeatsActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("location", libraryName + " " + libraryLevel[0]);
                startActivity(intent);
            }
        });

        TextView arcLevel1TextView = findViewById(R.id.text_arcl1_seats);
        calculatePercentage(libraryName + " " + libraryLevel[0], arcLevel1TextView);

        Button arcLevel2 = findViewById(R.id.arc_l2);
        arcLevel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArchitectureLibraryActivity.this,
                        LibrarySeatsActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("location", libraryName + " " + libraryLevel[1]);
                startActivity(intent);
            }
        });

        TextView arcLevel2TextView = findViewById(R.id.text_arcl2_seats);
        calculatePercentage(libraryName + " " + libraryLevel[1], arcLevel2TextView);

        Button arcLevel3 = findViewById(R.id.arc_l3);
        arcLevel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArchitectureLibraryActivity.this,
                        LibrarySeatsActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("location", libraryName + " " + libraryLevel[2]);
                startActivity(intent);
            }
        });

        TextView arcLevel3TextView = findViewById(R.id.text_arcl3_seats);
        calculatePercentage(libraryName + " " + libraryLevel[2], arcLevel3TextView);

        Button arcLevel4 = findViewById(R.id.arc_l4);
        arcLevel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArchitectureLibraryActivity.this,
                        LibrarySeatsActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("location", libraryName + " " + libraryLevel[3]);
                startActivity(intent);
            }
        });

        TextView arcLevel4TextView = findViewById(R.id.text_arcl4_seats);
        calculatePercentage(libraryName + " " + libraryLevel[3], arcLevel4TextView);

        Button arcLevel5 = findViewById(R.id.arc_l5);
        arcLevel5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArchitectureLibraryActivity.this,
                        LibrarySeatsActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("location", libraryName + " " + libraryLevel[4]);
                startActivity(intent);
            }
        });

        TextView arcLevel5TextView = findViewById(R.id.text_arcl5_seats);
        calculatePercentage(libraryName + " " + libraryLevel[4], arcLevel5TextView);

        Button logoutButton = findViewById(R.id.button_logout_arc);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutMethod(ArchitectureLibraryActivity.this);
            }
        });
    }

    // calculate the seats occupancy and seats vacancy
    public void calculatePercentage(final String location, final TextView textView) {
        final ArrayList<Integer> result = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        DatabaseReference ref = mDatabase.child("searchSeats").child("location").child(location);
        ref.limitToLast(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentList.clear();
                result.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ComWithDatabase comment = postSnapshot.getValue(ComWithDatabase.class);
                    commentList.add(0, comment);
                }
                Log.d("Database content", commentList.toString());
                Collections.reverse(commentList);   // displayed by upload date
                Log.v("commentList size", commentList.size() + "");
                for (int i = 0; i < commentList.size(); i++) {
                    ComWithDatabase tempObj = (ComWithDatabase) commentList.get(i);
                    if (LibrarySeatsActivity.timeDifference(obtainCurrentDate.getDateAndTime(),
                            tempObj.getDate()) < 1) {
                        result.add(0, Integer.parseInt(tempObj.getComment()));
                    }
                }
                int sum = 0;
                int num = 0;
                for (int i = 0; i < result.size(); i++) {
                    if (i <= 10) {
                        sum += result.get(i);
                        num++;
                    } else {
                        break;
                    }
                }
                if (num == 0) {
                    calResult = 0.0;
                } else {
                    calResult = sum / num;
                }
                textView.setText("Occupancy: " + calResult + "%" + "     " +
                        "Vacancy: " + (100 - calResult) + "%");
                mDatabase.child("libraryOccupation").child(location).setValue(calResult);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Database error", "loadPost:onCancelled",
                        databaseError.toException());
                // ...
            }
        });
    }
}
