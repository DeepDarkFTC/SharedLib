package com.example.sharedlib.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.sharedlib.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroupDetailsActivity extends BaseActivity {

    private boolean flag = true;
    private DatabaseReference mDatabase;
    private ArrayList groupMember = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        final Intent parentIntent = getIntent();

        TextView userNameTextView = findViewById(R.id.text_username_groupdetail);
        userNameTextView.setText(parentIntent.getStringExtra("userName"));

        TextView groupNameTextView = findViewById(R.id.text_name_group);
        groupNameTextView.setText(parentIntent.getStringExtra("groupName"));

        TextView groupLocationTextView = findViewById(R.id.text_location_group);
        groupLocationTextView.setText(parentIntent.getStringExtra("groupLocation"));

        TextView studyTimeTextView = findViewById(R.id.text_studytime_group);
        studyTimeTextView.setText(parentIntent.getStringExtra("studyTime"));

        final TextView memberTextView = findViewById(R.id.text_member_group);

        Button logoutButton = findViewById(R.id.button_logout_groupdetail);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutMethod(GroupDetailsActivity.this);
            }
        });

        final Button groupButton = findViewById(R.id.button_join_group);

        DatabaseReference ref1 = mDatabase.child("groupMember").child(parentIntent.getStringExtra("key")).child(emailToUid(user.getEmail()));
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    flag = false;
                    groupButton.setText("Quit This Group");
                }

                groupButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (flag) {   // join button
                            mDatabase.child("groupMember").child(parentIntent.getStringExtra("key")).child(emailToUid(user.getEmail())).setValue(true);
                            flag = false;
                            groupButton.setText("Quit This Group");
                        } else {   // quit button
                            mDatabase.child("groupMember").child(parentIntent.getStringExtra("key")).child(emailToUid(user.getEmail())).removeValue();
                            flag = true;
                            groupButton.setText("Join This Group");
                        }
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

        DatabaseReference ref2 = mDatabase.child("groupMember").child(parentIntent.getStringExtra("key"));
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupMember.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String member = uidToEmail(postSnapshot.getKey());
                    groupMember.add(member);
                }

                String members = "Group Members: " + "\n";
                for (int i = 0; i < groupMember.size(); i++) {
                    members += groupMember.get(i) + "\n";
                }
                memberTextView.setText(members);
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
