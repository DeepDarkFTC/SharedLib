package com.example.sharedlib;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NearestLibrary extends AppCompatActivity {

    private TextView userNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_library);

        Intent parentIntent = getIntent();
        String userName = parentIntent.getStringExtra("userName");

//        userNameTextView = findViewById(R.id.);
//        userNameTextView.setText(userName);
    }
}
