package com.example.sharedlib.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sharedlib.R;

public class GiblinLibraryActivity extends BaseActivity {

    private TextView userNameTextView;
    private ArchitectureLibraryActivity arch = new ArchitectureLibraryActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giblin_library);

        Intent parentIntent = getIntent();
        String userName = parentIntent.getStringExtra("userName");

        final String libraryName = "Giblin Library";
        final String[] libraryLevel = {"Level 1", "Level 2", "Level 3", "Level 4", "Level 5"};

        userNameTextView = findViewById(R.id.text_username_giblin);
        userNameTextView.setText(userName);

        Button gibLevel1 = findViewById(R.id.gib_l1);
        gibLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GiblinLibraryActivity.this, LibrarySeatsActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("location", libraryName + " " + libraryLevel[0]);
                startActivity(intent);
            }
        });

        TextView gibLevel1TextView = findViewById(R.id.text_gibl11_seats);
        arch.calculatePercentage(libraryName + " " + libraryLevel[0], gibLevel1TextView);

        Button gibLevel2 = findViewById(R.id.gib_l2);
        gibLevel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GiblinLibraryActivity.this, LibrarySeatsActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("location", libraryName + " " + libraryLevel[1]);
                startActivity(intent);
            }
        });

        TextView gibLevel2TextView = findViewById(R.id.text_gibl12_seats);
        arch.calculatePercentage(libraryName + " " + libraryLevel[1], gibLevel2TextView);

        Button gibLevel3 = findViewById(R.id.gib_l3);
        gibLevel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GiblinLibraryActivity.this, LibrarySeatsActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("location", libraryName + " " + libraryLevel[2]);
                startActivity(intent);
            }
        });

        TextView gibLevel3TextView = findViewById(R.id.text_gibl13_seats);
        arch.calculatePercentage(libraryName + " " + libraryLevel[2], gibLevel3TextView);

        Button gibLevel4 = findViewById(R.id.gib_l4);
        gibLevel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GiblinLibraryActivity.this, LibrarySeatsActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("location", libraryName + " " + libraryLevel[3]);
                startActivity(intent);
            }
        });

        TextView gibLevel4TextView = findViewById(R.id.text_gibl14_seats);
        arch.calculatePercentage(libraryName + " " + libraryLevel[3], gibLevel4TextView);

        Button gibLevel5 = findViewById(R.id.gib_l5);
        gibLevel5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GiblinLibraryActivity.this, LibrarySeatsActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("location", libraryName + " " + libraryLevel[4]);
                startActivity(intent);
            }
        });

        TextView gibLevel5TextView = findViewById(R.id.text_gibl15_seats);
        arch.calculatePercentage(libraryName + " " + libraryLevel[4], gibLevel5TextView);

        Button logoutButton = findViewById(R.id.button_logout_giblin);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutMethod(GiblinLibraryActivity.this);
            }
        });
    }
}
