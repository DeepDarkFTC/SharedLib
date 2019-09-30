package com.example.sharedlib;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BaillieuLibraryActivity extends AppCompatActivity {

    private TextView userNameTextView;

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
    }
}
