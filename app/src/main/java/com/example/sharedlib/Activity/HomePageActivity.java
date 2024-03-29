package com.example.sharedlib.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.sharedlib.Object.MapsActivity;
import com.example.sharedlib.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePageActivity extends BaseActivity {

    private Button personalInfoButton;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        final String userId = emailToUid(currentUser.getEmail());
        Log.d("Database content111", userId);

        // get the username from firebase
        DatabaseReference ref = mDatabase.child("userName");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userName = dataSnapshot.child(userId).getValue().toString();
                personalInfoButton.setText(userName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Database error", "loadPost:onCancelled",
                        databaseError.toException());
                // ...
            }
        });

        // search seats feature
        Button searchSeat = findViewById(R.id.button_search_homepage);
        searchSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this,
                        SearchSeatsActivity.class);
                intent.putExtra("userName", personalInfoButton.getText().toString());
                startActivity(intent);
            }
        });

        // form group feature
        Button formGroups = findViewById(R.id.button_form_homepage);
        formGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this,
                        FormGroupsActivity.class);
                intent.putExtra("userName", personalInfoButton.getText().toString());
                startActivity(intent);
            }
        });

        // study time feature
        Button studyTime = findViewById(R.id.button_time_homepage);
        studyTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this,
                        StudyTimeActivity.class);
                intent.putExtra("userName", personalInfoButton.getText().toString());
                startActivity(intent);
            }
        });

        // nearest library feature
        Button nearestLibrary = findViewById(R.id.button_nearest_homepage);
        nearestLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this,
                        MapsActivity.class);
                intent.putExtra("userName", personalInfoButton.getText().toString());
                startActivity(intent);
            }
        });

        // check person details feature
        personalInfoButton = findViewById(R.id.button_username_homepage);
        personalInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this,
                        PersonalDetailsActivity.class);
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
