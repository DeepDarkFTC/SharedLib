package com.example.sharedlib;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.TimeZone;

public class LibrarySeatsActivity extends BaseActivity {

    private EditText seatsPercentageEditText;
    private Calendar calendars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_seats);

        String[] data = {"comment1", "comment2", "comment3", "comment4", "comment5"};// these data should from firebase

        Intent parentIntent = getIntent();
        String userName = parentIntent.getStringExtra("userName");
        String location = parentIntent.getStringExtra("location");

        TextView userNameTextView = findViewById(R.id.text_username_libraryseats);
        userNameTextView.setText(userName);

        final TextView locationTextView = findViewById(R.id.information);
        locationTextView.setText(location);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(LibrarySeatsActivity.this, android.R.layout.simple_list_item_1, data);
        ListView listView = findViewById(R.id.seatsComments);
        listView.setAdapter(adapter);

        seatsPercentageEditText = findViewById(R.id.text_seats_available_libraryseats);

        Button upload = findViewById(R.id.button_update_libraryseats);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String seatsPercentage = seatsPercentageEditText.getText().toString();
                int percentage = Integer.parseInt(seatsPercentage);
                if (percentage >= 0 && percentage <= 100) {
                    Log.v("upload data to firebase", seatsPercentage);
                    calendars = Calendar.getInstance();

                    calendars.setTimeZone(TimeZone.getTimeZone("GMT+10:00"));

                    String year = String.valueOf(calendars.get(Calendar.YEAR));

                    String month = String.valueOf(calendars.get(Calendar.MONTH)+1);

                    String day = String.valueOf(calendars.get(Calendar.DATE));

                    String hour = String.valueOf(calendars.get(Calendar.HOUR));

                    String min = String.valueOf(calendars.get(Calendar.MINUTE));

                    String second = String.valueOf(calendars.get(Calendar.SECOND));

                    String isAm = calendars.get(Calendar.AM_PM)==1 ? "PM":"AM";

                    Boolean is24 = DateFormat.is24HourFormat(getApplication()) ?true:false;


                    Log.v("date", day+"/"+month+"/"+year+" "+hour+":"+min+":"+second+" "+isAm);
                    Log.v("location",locationTextView.getText().toString());

                } else {
                    Toast.makeText(LibrarySeatsActivity.this, "The input data should between 0 and 100",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
