package com.example.sharedlib;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ErcLibraryActivity extends AppCompatActivity {

    private TextView userNameTextView;

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
    }
}
