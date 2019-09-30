package com.example.sharedlib;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ArchitectureLibraryActivity extends AppCompatActivity {

    private TextView userNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arc_library);

        final String libraryName = "Architecture Library";
        final String[] libraryLevel = {"Level 1", "Level 2", "Level 3", "Level 4", "Level 5"};

        Intent parentIntent = getIntent();
        String userName = parentIntent.getStringExtra("userName");

        userNameTextView = findViewById(R.id.text_username_arc);
        userNameTextView.setText(userName);

        Button arcLevel1 = findViewById(R.id.arc_l1);
        arcLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArchitectureLibraryActivity.this, LibrarySeatsActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("location", libraryName + " " + libraryLevel[0]);
                startActivity(intent);
            }
        });

        Button arcLevel2 = findViewById(R.id.arc_l2);
        arcLevel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArchitectureLibraryActivity.this, LibrarySeatsActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("location", libraryName + " " + libraryLevel[1]);
                startActivity(intent);
            }
        });

        Button arcLevel3 = findViewById(R.id.arc_l3);
        arcLevel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArchitectureLibraryActivity.this, LibrarySeatsActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("location", libraryName + " " + libraryLevel[2]);
                startActivity(intent);
            }
        });

        Button arcLevel4 = findViewById(R.id.arc_l4);
        arcLevel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArchitectureLibraryActivity.this, LibrarySeatsActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("location", libraryName + " " + libraryLevel[3]);
                startActivity(intent);
            }
        });

        Button arcLevel5 = findViewById(R.id.arc_l5);
        arcLevel5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArchitectureLibraryActivity.this, LibrarySeatsActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                intent.putExtra("location", libraryName + " " + libraryLevel[4]);
                startActivity(intent);
            }
        });
    }
}
