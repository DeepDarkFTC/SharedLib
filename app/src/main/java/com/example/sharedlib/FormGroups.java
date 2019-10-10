package com.example.sharedlib;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FormGroups extends BaseActivity {
    private DatabaseReference mDatabase;
    private ArrayList commentList = new ArrayList<ComWithDatabase>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_groups);

        mDatabase = FirebaseDatabase.getInstance().getReference();


        String[] data = {"group1", "group2", "group3", "group4", "group5"};// these data should from firebase

        Intent parentIntent = getIntent();
        String userName = parentIntent.getStringExtra("userName");

        TextView userNameTextView = findViewById(R.id.text_username_formgroups);
        userNameTextView.setText(userName);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(FormGroups.this, android.R.layout.simple_list_item_1, data);
        ListView listView = findViewById(R.id.listview_infolist_formgroup);
        listView.setAdapter(adapter);

        Button createButton = findViewById(R.id.button_startnew_formgroup);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormGroups.this, CreateNewGroup.class);
                startActivity(intent);
            }
        });

        DatabaseReference ref = mDatabase.child("studyGroup");
        ref.addValueEventListener(new ValueEventListener() {
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
                ArrayList temp = new ArrayList();

                for (int i = 0; i < commentList.size(); i++) {
                    ComWithDatabase tempObj = (ComWithDatabase) commentList.get(i);
                    String record = "Seat occupancy: " + tempObj.getComment() + "%" + "\t" + "upload date: " + tempObj.getDate() + "\t" + "uploaded by: " + tempObj.getUser();
                    /*
                    if (timeDifference(obtainCurrentDate.getDateAndTime(), tempObj.getDate()) < 1) {
                        temp.add(0,record);
                    }*/
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(FormGroups.this, android.R.layout.simple_list_item_1, temp);
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


    }

}