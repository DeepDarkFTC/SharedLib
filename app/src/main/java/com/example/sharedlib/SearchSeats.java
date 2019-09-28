package com.example.sharedlib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class SearchSeats extends AppCompatActivity {

    private ImageButton architectureLibrary;
    private ImageButton bailieuLibrary;
    private ImageButton ercLibrary;
    private ImageButton giblinLibrary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_seats);

        architectureLibrary = findViewById(R.id.arc);
        architectureLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchSeats.this, ArchitectureLibraryActivity.class);
                startActivity(intent);
            }
        });

        bailieuLibrary = findViewById(R.id.bailieu);
        bailieuLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchSeats.this, BaillieuLibraryActivity.class);
                startActivity(intent);
            }
        });

        ercLibrary = findViewById(R.id.erc);
        ercLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchSeats.this, ErcLibraryActivity.class);
                startActivity(intent);
            }
        });

        giblinLibrary = findViewById(R.id.giblin);
        giblinLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchSeats.this, GiblinLibraryActivity.class);
                startActivity(intent);
            }
        });
    }
}
