package com.example.sharedlib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class GroupDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details);

        Intent parentIntent = getIntent();

        TextView groupNameTextView = findViewById(R.id.text_name_group);
        groupNameTextView.setText(parentIntent.getStringExtra("groupName"));

        TextView groupLocationTextView = findViewById(R.id.text_location_group);
        groupLocationTextView.setText(parentIntent.getStringExtra("groupLocation"));

        TextView studyTimeTextView = findViewById(R.id.text_studytime_group);
        studyTimeTextView.setText(parentIntent.getStringExtra("studyTime"));

    }
}
