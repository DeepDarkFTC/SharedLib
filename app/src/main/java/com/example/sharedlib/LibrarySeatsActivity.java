package com.example.sharedlib;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LibrarySeatsActivity extends AppCompatActivity {

    private TextView userNameTextView;
    private TextView locationTextView;
    private EditText seatsPercentage;
    private Button upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_seats);

        String[] data = { "comment1", "comment2", "comment3", "comment4","comment5"};// these data should from firebase


        Intent parentIntent = getIntent();
        String userName = parentIntent.getStringExtra("userName");
        String location = parentIntent.getStringExtra("location");

        userNameTextView = findViewById(R.id.text_username_libraryseats);
        userNameTextView.setText(userName);

        locationTextView = findViewById(R.id.information);
        locationTextView.setText(location);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(LibrarySeatsActivity.this, android.R.layout.simple_list_item_1, data);
        ListView listView = findViewById(R.id.seatsComments);
        listView.setAdapter(adapter);

        seatsPercentage = findViewById(R.id.text_seats_available_libraryseats);

        upload = findViewById(R.id.button_update_libraryseats);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String seatsPercentageData = seatsPercentage.getText().toString();
                Log.v("上传数据",seatsPercentageData);
            }
        });


    }
}
