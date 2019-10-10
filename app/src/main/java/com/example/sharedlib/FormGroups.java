package com.example.sharedlib;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class FormGroups extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_groups);

        String[] data = {"group1", "group2", "group3", "group4", "group5"};// these data should from firebase

        Intent parentIntent = getIntent();
        String userName = parentIntent.getStringExtra("userName");

        TextView userNameTextView = findViewById(R.id.text_username_formgroups);
        userNameTextView.setText(userName);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(FormGroups.this, android.R.layout.simple_list_item_1, data);
        ListView listView = findViewById(R.id.listview_infolist_formgroup);
        listView.setAdapter(adapter);

        Button createButton = findViewById(R.id.button_startnew_formgroup);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormGroups.this, CreateNewGroup.class);
                startActivity(intent);
            }
        });


    }

}