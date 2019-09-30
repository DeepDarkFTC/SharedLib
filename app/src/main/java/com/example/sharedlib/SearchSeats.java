package com.example.sharedlib;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SearchSeats extends AppCompatActivity {

    private TextView userNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_seats);

        Intent parentIntent = getIntent();
        String userName = parentIntent.getStringExtra("userName");

        userNameTextView = findViewById(R.id.text_username_searchseats);
        userNameTextView.setText(userName);

        ImageButton architectureLibrary = findViewById(R.id.button_arc_searchseats);
        architectureLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchSeats.this, ArchitectureLibraryActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                startActivity(intent);
            }
        });

        ImageButton bailieuLibrary = findViewById(R.id.button_bai_searchseats);
        bailieuLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchSeats.this, BaillieuLibraryActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                startActivity(intent);
            }
        });

        ImageButton ercLibrary = findViewById(R.id.button_erc_searchseats);
        ercLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchSeats.this, ErcLibraryActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                startActivity(intent);
            }
        });

        ImageButton giblinLibrary = findViewById(R.id.button_giblin_searchseats);
        giblinLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchSeats.this, GiblinLibraryActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                startActivity(intent);
            }
        });
    }
}
