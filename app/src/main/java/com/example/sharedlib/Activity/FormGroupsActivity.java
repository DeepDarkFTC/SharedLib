package com.example.sharedlib.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.sharedlib.Object.ComWithDatabase;
import com.example.sharedlib.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FormGroupsActivity extends BaseActivity {

    private ArrayList commentList = new ArrayList<ComWithDatabase>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_groups);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent parentIntent = getIntent();
        String userName = parentIntent.getStringExtra("userName");

        final TextView userNameTextView = findViewById(R.id.text_username_formgroups);
        userNameTextView.setText(userName);

        Button createButton = findViewById(R.id.button_startnew_formgroup);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormGroupsActivity.this, CreateNewGroup.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                startActivity(intent);
                finish();
            }
        });

        DatabaseReference ref = mDatabase.child("studyGroup");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentList.clear();
                // get the data from firebase
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ComWithDatabase groupInfo = postSnapshot.getValue(ComWithDatabase.class);
                    commentList.add(groupInfo);
                }
                Log.d("Database content", commentList.toString());

                ArrayList temp = new ArrayList();
                final ArrayList objectList = new ArrayList<ComWithDatabase>();

                // change to String to display
                for (int i = 0; i < commentList.size(); i++) {
                    ComWithDatabase tempObj = (ComWithDatabase) commentList.get(i);
                    String record = "Group Name: " + tempObj.getGroupName() + "\n" +
                            "Library: " + tempObj.getLibraryName() + "\n" +
                            "Level:" + tempObj.getLibraryLevel() + "\n" +
                            "Study Topic: " + tempObj.getStudyTopic() + "\n" +
                            "Start Time: " + tempObj.getStartTime() + "\n" +
                            "End Time: " + tempObj.getEndTime() + "\n" +
                            "Created by: " + tempObj.getTeamLeader() + "\n";
                    temp.add(record);
                    objectList.add(tempObj);
                }

                // set the adapter for the listView
                ArrayAdapter<String> adapter = new ArrayAdapter<>(FormGroupsActivity.this,
                        android.R.layout.simple_list_item_1, temp);
                ListView listView = findViewById(R.id.listview_infolist_formgroup);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(FormGroupsActivity.this,
                                GroupDetailsActivity.class);
                        ComWithDatabase info = (ComWithDatabase) objectList.get(i);

                        intent.putExtra("userName", userNameTextView.getText().toString());
                        intent.putExtra("groupName", info.getGroupName());
                        intent.putExtra("groupLocation", info.getLibraryName() + " "
                                + info.getLibraryLevel());
                        intent.putExtra("studyTime", "From: " + info.getStartTime()
                                + "\nTo  : " + info.getEndTime());
                        intent.putExtra("key", info.getId());

                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Database error", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

        Button logoutButton = findViewById(R.id.button_logout_formgroups);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutMethod(FormGroupsActivity.this);
            }
        });
    }

}