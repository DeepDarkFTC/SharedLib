package com.example.sharedlib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomePageActivity extends AppCompatActivity {

    private Button searchSeat;
    private Button formGroups;
    private Button studyTime;
    private Button nearestLibrary;
    private TextView userNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Intent parentIntent = getIntent();
        String userName = parentIntent.getStringExtra("userName");

        userNameTextView = findViewById(R.id.text_username_homepage);
        userNameTextView.setText(userName);

        searchSeat = findViewById(R.id.button_search_homepage);
        searchSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, SearchSeats.class);
                startActivity(intent);
            }
        });

        formGroups = findViewById(R.id.button_form_homepage);
        formGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, FormGroups.class);
                startActivity(intent);
            }
        });

        studyTime = findViewById(R.id.button_time_homepage);
        studyTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, StudyTime.class);
                startActivity(intent);
            }
        });

        nearestLibrary = findViewById(R.id.button_nearest_homepage);
        nearestLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, NearestLibrary.class);
                startActivity(intent);
            }
        });
    }
}
