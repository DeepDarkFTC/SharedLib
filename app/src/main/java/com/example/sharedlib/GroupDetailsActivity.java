package com.example.sharedlib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GroupDetailsActivity extends BaseActivity {

    private boolean flag = true;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
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

        Button logoutButton = findViewById(R.id.button_logout_groupdetail);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutMethod(GroupDetailsActivity.this);
            }
        });

        final Button groupButton = findViewById(R.id.button_join_group);

        DatabaseReference ref = mDatabase.child("groupMember").child(emailToUid(user.getEmail()));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    Log.v("先手","111111");
                    flag = false;
                }

                groupButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.v("后手","22222"+flag);
                        if(flag){   // join button
                            mDatabase.child("groupMember").child(parentIntent.getStringExtra("key")).child(emailToUid(user.getEmail())).setValue(true);
                            flag = false;
                            groupButton.setText("Quit This Group");
                        }
                        else{   // quit button
                            flag = true;
                            groupButton.setText("Join This Group");
                        }
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
