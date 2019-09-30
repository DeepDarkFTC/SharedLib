package com.example.sharedlib;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomePageActivity extends AppCompatActivity {

    private TextView userNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Intent parentIntent = getIntent();
        String userName = parentIntent.getStringExtra("userName");

        userNameTextView = findViewById(R.id.text_username_homepage);
        userNameTextView.setText(userName);

        Button searchSeat = findViewById(R.id.button_search_homepage);
        searchSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, SearchSeats.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                startActivity(intent);
            }
        });

        Button formGroups = findViewById(R.id.button_form_homepage);
        formGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, FormGroups.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                startActivity(intent);
            }
        });

        Button studyTime = findViewById(R.id.button_time_homepage);
        studyTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, StudyTime.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                startActivity(intent);
            }
        });

        Button nearestLibrary = findViewById(R.id.button_nearest_homepage);
        nearestLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePageActivity.this, NearestLibrary.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                startActivity(intent);
            }
        });
    }
}
