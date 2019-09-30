package com.example.sharedlib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ArchitectureLibraryActivity extends AppCompatActivity {

    private TextView userNameTextView;
    private Button arcLevel1;
    private Button arcLevel2;
    private Button arcLevel3;
    private Button arcLevel4;
    private Button arcLevel5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arc_library);

        Intent parentIntent = getIntent();
        String userName = parentIntent.getStringExtra("userName");

        userNameTextView = findViewById(R.id.text_username_arc);
        userNameTextView.setText(userName);

        arcLevel1 = findViewById(R.id.arc_l1);
        arcLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArchitectureLibraryActivity.this, LibrarySeatsActivity.class);
                intent.putExtra("userName",userNameTextView.getText().toString());
                startActivity(intent);
            }
        });
    }
}
