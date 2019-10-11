package com.example.sharedlib;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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


//        String[] data = {"group1", "group2", "group3", "group4", "group5"};// these data should from firebase

        Intent parentIntent = getIntent();
        String userName = parentIntent.getStringExtra("userName");

        TextView userNameTextView = findViewById(R.id.text_username_formgroups);
        userNameTextView.setText(userName);

//        ArrayAdapter<String> adapter = new ArrayAdapter<>(FormGroups.this, android.R.layout.simple_list_item_1, data);
//        ListView listView = findViewById(R.id.listview_infolist_formgroup);
//        listView.setAdapter(adapter);

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
                    ComWithDatabase groupInfo = postSnapshot.getValue(ComWithDatabase.class);
                    commentList.add(groupInfo);
                }
                Log.d("Database content", commentList.toString());
                
                ArrayList temp = new ArrayList();
                final ArrayList objectList = new ArrayList<ComWithDatabase>();

                for (int i = 0; i < commentList.size(); i++) {
                    ComWithDatabase tempObj = (ComWithDatabase) commentList.get(i);
                    String record = "Group Name: " + tempObj.getGroupName() +
                            "Library Name:" + tempObj.getLibraryName() +
                            "Level:" + tempObj.getLibraryLevel() +
                            "Study Topic: " + tempObj.getStudyTopic()+
                            "Start Time: " + tempObj.getStartTime()+
                            "End Time: " + tempObj.getEndTime() +
                            "Created by: " + tempObj.getTeamLeader();
                    temp.add(record);
                    objectList.add(tempObj);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(FormGroups.this, android.R.layout.simple_list_item_1, temp);
                ListView listView = findViewById(R.id.listview_infolist_formgroup);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(FormGroups.this, GroupDetailsActivity.class);
                        ComWithDatabase info = (ComWithDatabase)objectList.get(i);

                        intent.putExtra("groupName",info.getGroupName());
                        intent.putExtra("groupLocation",info.getLibraryName()+" "+info.getLibraryLevel());
                        intent.putExtra("studyTime","From: "+info.getStartTime()+"To: "+info.getEndTime());

                        startActivity(intent);
                    }
                });
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