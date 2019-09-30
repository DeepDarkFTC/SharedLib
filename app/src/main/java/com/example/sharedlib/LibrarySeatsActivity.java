package com.example.sharedlib;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LibrarySeatsActivity extends AppCompatActivity {

    private TextView userNameTextView;
    private TextView locationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_seats);

        Intent parentIntent = getIntent();
        String userName = parentIntent.getStringExtra("userName");
        String location = parentIntent.getStringExtra("location");

        userNameTextView = findViewById(R.id.text_username_libraryseats);
        userNameTextView.setText(userName);

        locationTextView = findViewById(R.id.information);
        locationTextView.setText(location);
    }
}
