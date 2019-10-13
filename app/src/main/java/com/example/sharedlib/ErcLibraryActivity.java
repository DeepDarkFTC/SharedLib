package com.example.sharedlib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ErcLibraryActivity extends BaseActivity {

    private TextView userNameTextView;
    private ArchitectureLibraryActivity arch = new ArchitectureLibraryActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_erc_library);

        Intent parentIntent = getIntent();
        String userName = parentIntent.getStringExtra("userName");

        final String libraryName = "Erc Library";
        final String[] libraryLevel = {"Level 1", "Level 2", "Level 3", "Level 4", "Level 5"};

        userNameTextView = findViewById(R.id.text_username_erc);
        userNameTextView.setText(userName);

        Button ercLevel1 = findViewById(R.id.erc_l1);
        ercLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ErcLibraryActivity.this, LibrarySeatsActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("location", libraryName + " " + libraryLevel[0]);
                startActivity(intent);
            }
        });

        TextView ercLevel1TextView = findViewById(R.id.text_ercl1_seats);
        arch.calculatePersentage(libraryName + " " + libraryLevel[0], ercLevel1TextView);

        Button ercLevel2 = findViewById(R.id.erc_l2);
        ercLevel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ErcLibraryActivity.this, LibrarySeatsActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("location", libraryName + " " + libraryLevel[1]);
                startActivity(intent);
            }
        });

        TextView ercLevel2TextView = findViewById(R.id.text_ercl2_seats);
        arch.calculatePersentage(libraryName + " " + libraryLevel[1], ercLevel2TextView);

        Button ercLevel3 = findViewById(R.id.erc_l3);
        ercLevel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ErcLibraryActivity.this, LibrarySeatsActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("location", libraryName + " " + libraryLevel[2]);
                startActivity(intent);
            }
        });

        TextView ercLevel3TextView = findViewById(R.id.text_ercl3_seats);
        arch.calculatePersentage(libraryName + " " + libraryLevel[2], ercLevel3TextView);

        Button ercLevel4 = findViewById(R.id.erc_l4);
        ercLevel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ErcLibraryActivity.this, LibrarySeatsActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("location", libraryName + " " + libraryLevel[3]);
                startActivity(intent);
            }
        });

        TextView ercLevel4TextView = findViewById(R.id.text_ercl4_seats);
        arch.calculatePersentage(libraryName + " " + libraryLevel[3], ercLevel4TextView);

        Button ercLevel5 = findViewById(R.id.erc_l5);
        ercLevel5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ErcLibraryActivity.this, LibrarySeatsActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("location", libraryName + " " + libraryLevel[4]);
                startActivity(intent);
            }
        });

        TextView ercLevel5TextView = findViewById(R.id.text_ercl5_seats);
        arch.calculatePersentage(libraryName + " " + libraryLevel[4], ercLevel5TextView);

        Button logoutButton = findViewById(R.id.button_logout_erc);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutMethod(ErcLibraryActivity.this);
            }
        });
    }
}
