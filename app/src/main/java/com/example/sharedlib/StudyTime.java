package com.example.sharedlib;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class StudyTime extends BaseActivity {

    private Chronometer timer;
    private Button start;
    private Button pause;
    private Button restart;
    private Boolean stopFlag = false;
    private long mRecordTime;

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_time);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent parentIntent = getIntent();
        final String userName = parentIntent.getStringExtra("userName");

        final TextView userNameTextView = findViewById(R.id.text_username_studytime);
        userNameTextView.setText(userName);

        // Get timer component
        timer = findViewById(R.id.study_time_screen);
        // Get startTimeTextView button
        start = findViewById(R.id.button_start_studytime);
        // Get pause button
        pause = findViewById(R.id.button_pause_studytime);
        // Get continue button
        restart = findViewById(R.id.button_goon_studytime);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // startTimeTextView timer
                if (!stopFlag) {
                    timer.setBase(SystemClock.elapsedRealtime());
                    timer.start();
                    pause.setEnabled(true);
                    restart.setEnabled(false);
                    start.setEnabled(false);

                    stopFlag = true;
                } else {
                    Log.v("current time", timer.getText().toString());
                    int hour = Integer.parseInt(timer.getText().toString().split(":")[0]);
                    int minute = Integer.parseInt(timer.getText().toString().split(":")[1]);
                    int second = Integer.parseInt(timer.getText().toString().split(":")[2]);

                    int totalTime = hour * 3600 + minute * 60 + second;
                    Log.v("total time", String.valueOf(totalTime));// sent message to firebase

                    timer.stop();
                    timer.setBase(SystemClock.elapsedRealtime());
                    pause.setEnabled(false);
                    restart.setEnabled(false);
                    start.setEnabled(true);
                    start.setText("Start");

                    // send record to firebase
                    stopFlag = false;

                    ComWithDatabase comment = new
                            ComWithDatabase(String.valueOf(totalTime), String.valueOf(totalTime));

                    String userId = emailToUid(userName);
                    mDatabase.child("studyTime").child(userId).setValue(comment);
                }

            }
        });

        // Pause button listener
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("pause time", timer.getText().toString());
                start.setText("End");
                timer.stop();
                mRecordTime = SystemClock.elapsedRealtime();
                start.setEnabled(true);
                restart.setEnabled(true);
                pause.setEnabled(false);
            }
        });

        // Continue button listener
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setText("End");
                if (mRecordTime != 0) {
                    timer.setBase(timer.getBase() + (SystemClock.elapsedRealtime() - mRecordTime));
                } else {
                    timer.setBase(SystemClock.elapsedRealtime());
                }
                timer.start();
                start.setEnabled(true);
                pause.setEnabled(true);
                restart.setEnabled(false);
            }
        });

        // Binding event listeners for Chronomter
        timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                setFormat(timer);
            }
        });

        Button timeRank = findViewById(R.id.button_rankings_studytime);
        timeRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudyTime.this, Ranking.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                startActivity(intent);
            }
        });
    }

    private void setFormat(Chronometer chronometer) {
        int hour = (int) ((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000 / 60 / 60);
        int minute = (int) ((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000 / 60) % 60;
        int second = (int) ((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000) % 60;
        if (hour < 1) {
            if (minute == 59 && second == 59) {
                chronometer.setFormat("0" + "%s");
            } else {
                chronometer.setFormat("0" + String.valueOf(hour) + ":%s");
            }
        } else if (hour < 10) {
            chronometer.setFormat("0" + "%s");
        } else {
            chronometer.setFormat("%s");
        }
    }

}