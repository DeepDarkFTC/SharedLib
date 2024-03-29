package com.example.sharedlib.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sharedlib.R;

public class BaillieuLibraryActivity extends BaseActivity {

    private TextView userNameTextView;  // display username
    private ArchitectureLibraryActivity arch = new ArchitectureLibraryActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bailieu_library);

        final String libraryName = "Baillieu Library";
        final String[] libraryLevel = {"Level 1", "Level 2", "Level 3", "Level 4", "Level 5"};

        Intent parentIntent = getIntent();
        String userName = parentIntent.getStringExtra("userName");

        userNameTextView = findViewById(R.id.text_username_bailieu);
        userNameTextView.setText(userName);

        // implement 5 library buttons
        Button baiLevel1 = findViewById(R.id.bai_l1);
        baiLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaillieuLibraryActivity.this, LibrarySeatsActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("location", libraryName + " " + libraryLevel[0]);
                startActivity(intent);
            }
        });

        TextView baiLevel1TextView = findViewById(R.id.text_bai1_seats);
        arch.calculatePercentage(libraryName + " " + libraryLevel[0], baiLevel1TextView);

        Button baiLevel2 = findViewById(R.id.bai_l2);
        baiLevel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaillieuLibraryActivity.this, LibrarySeatsActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("location", libraryName + " " + libraryLevel[1]);
                startActivity(intent);
            }
        });

        TextView baiLevel2TextView = findViewById(R.id.text_bai2_seats);
        arch.calculatePercentage(libraryName + " " + libraryLevel[1], baiLevel2TextView);

        Button baiLevel3 = findViewById(R.id.bai_l3);
        baiLevel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaillieuLibraryActivity.this, LibrarySeatsActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("location", libraryName + " " + libraryLevel[2]);
                startActivity(intent);
            }
        });

        TextView baiLevel3TextView = findViewById(R.id.text_bai3_seats);
        arch.calculatePercentage(libraryName + " " + libraryLevel[2], baiLevel3TextView);

        Button baiLevel4 = findViewById(R.id.bai_l4);
        baiLevel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaillieuLibraryActivity.this, LibrarySeatsActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("location", libraryName + " " + libraryLevel[3]);
                startActivity(intent);
            }
        });

        TextView baiLevel4TextView = findViewById(R.id.text_bai4_seats);
        arch.calculatePercentage(libraryName + " " + libraryLevel[3], baiLevel4TextView);

        Button baiLevel5 = findViewById(R.id.bai_l5);
        baiLevel5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaillieuLibraryActivity.this, LibrarySeatsActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("location", libraryName + " " + libraryLevel[4]);
                startActivity(intent);
            }
        });

        TextView baiLevel5TextView = findViewById(R.id.text_bai5_seats);
        arch.calculatePercentage(libraryName + " " + libraryLevel[4], baiLevel5TextView);

        Button logoutButton = findViewById(R.id.button_logout_bailieu);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutMethod(BaillieuLibraryActivity.this);
            }
        });
    }
}
