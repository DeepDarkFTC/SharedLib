package com.example.sharedlib;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePageActivity extends BaseActivity {

    private Button personalInfoButton;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        final String userId = emailToUid(currentUser.getEmail());
        Log.d("Database content111", userId);


        DatabaseReference ref = mDatabase.child("userName");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                userName = dataSnapshot.child(userId).getValue().toString();
                Log.d("Database content222", userName);
                personalInfoButton.setText(userName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Database error", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

        Button searchSeat = findViewById(R.id.button_search_homepage);
        searchSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, SearchSeatsActivity.class);
                intent.putExtra("userName", personalInfoButton.getText().toString());
                startActivity(intent);
            }
        });

        Button formGroups = findViewById(R.id.button_form_homepage);
        formGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, FormGroupsActivity.class);
                intent.putExtra("userName", personalInfoButton.getText().toString());
                startActivity(intent);
            }
        });

        Button studyTime = findViewById(R.id.button_time_homepage);
        studyTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, StudyTimeActivity.class);
                intent.putExtra("userName", personalInfoButton.getText().toString());
                startActivity(intent);
            }
        });

        Button nearestLibrary = findViewById(R.id.button_nearest_homepage);
        nearestLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, MapsActivity.class);
                intent.putExtra("userName", personalInfoButton.getText().toString());
                startActivity(intent);
            }
        });

        personalInfoButton = findViewById(R.id.button_username_homepage);
        personalInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, PersonalDetailsActivity.class);
                intent.putExtra("userName", personalInfoButton.getText().toString());
                startActivity(intent);
            }
        });

        Button logoutButton = findViewById(R.id.button_logout_homepage);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutMethod(HomePageActivity.this);
            }
        });

    }

}
