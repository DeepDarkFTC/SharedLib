package com.example.sharedlib;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ArchitectureLibraryActivity extends AppCompatActivity {

    private TextView userNameTextView;
    private DatabaseReference mDatabase;
    private ArrayList commentList = new ArrayList<ComWithDatabase>();
    private double calResult = 0.0;
    private TextView arcLevel1TextView;

    private ObtainCurrentDate obtainCurrentDate = new ObtainCurrentDate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arc_library);

        final String libraryName = "Architecture Library";
        final String[] libraryLevel = {"Level 1", "Level 2", "Level 3", "Level 4", "Level 5"};

        Intent parentIntent = getIntent();
        String userName = parentIntent.getStringExtra("userName");

        userNameTextView = findViewById(R.id.text_username_arc);
        userNameTextView.setText(userName);

        Button arcLevel1 = findViewById(R.id.arc_l1);
        arcLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArchitectureLibraryActivity.this, LibrarySeatsActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("location", libraryName + " " + libraryLevel[0]);
                startActivity(intent);
            }
        });

        arcLevel1TextView = findViewById(R.id.text_ercl1_setas);
        calculatePersentage(libraryName + " " + libraryLevel[0],arcLevel1TextView);


        Button arcLevel2 = findViewById(R.id.arc_l2);
        arcLevel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArchitectureLibraryActivity.this, LibrarySeatsActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("location", libraryName + " " + libraryLevel[1]);
                startActivity(intent);
            }
        });

        Button arcLevel3 = findViewById(R.id.arc_l3);
        arcLevel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArchitectureLibraryActivity.this, LibrarySeatsActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("location", libraryName + " " + libraryLevel[2]);
                startActivity(intent);
            }
        });

        Button arcLevel4 = findViewById(R.id.arc_l4);
        arcLevel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArchitectureLibraryActivity.this, LibrarySeatsActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("location", libraryName + " " + libraryLevel[3]);
                startActivity(intent);
            }
        });

        Button arcLevel5 = findViewById(R.id.arc_l5);
        arcLevel5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArchitectureLibraryActivity.this, LibrarySeatsActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("location", libraryName + " " + libraryLevel[4]);
                startActivity(intent);
            }
        });

    }

    public void calculatePersentage(String location, final TextView textView) {
        final ArrayList<Integer> result = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        DatabaseReference ref = mDatabase.child("searchSeats").child("location").child(location);
        ref.limitToLast(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                commentList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ComWithDatabase comment = postSnapshot.getValue(ComWithDatabase.class);
                    commentList.add(0, comment);
                }
                Log.d("Database content", commentList.toString());
                Collections.reverse(commentList);   // displayed by upload date
                Log.v("commentList size",commentList.size()+"");
                for (int i = 0; i < commentList.size(); i++) {
                    ComWithDatabase tempObj = (ComWithDatabase) commentList.get(i);
                    if (LibrarySeatsActivity.timeDifference(obtainCurrentDate.getDateAndTime(), tempObj.getDate()) < 1) {
                        result.add(0, Integer.parseInt(tempObj.getComment()));
                    }
                }
                Log.v("result size:",result.size()+"");
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
                } else{
                    Log.v("总比例",sum+"");
                    Log.v("总数",num+"");
                    calResult = sum/num;
                }
                textView.setText("Seat occupancy: "+ calResult+"%");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Database error", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
    }
}
