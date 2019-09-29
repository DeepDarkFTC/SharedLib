package com.example.sharedlib;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import androidx.appcompat.app.AppCompatActivity;


public class StudyTime extends AppCompatActivity {
    private Chronometer timer;
    private Button start;
    private Button pause;
    private Button restart;
    private Boolean stopFlag = false;
    private long mRecordTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_time);

        // Get timer component
        timer = findViewById(R.id.test);
        // Get start button
        start = findViewById(R.id.start);
        // Get pause button
        pause = findViewById(R.id.pause);
        // Get continue button
        restart = findViewById(R.id.go_on);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // start timer
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
                    Log.v("total time", String.valueOf(totalTime));

                    timer.stop();
                    pause.setEnabled(false);
                    restart.setEnabled(false);
                    start.setEnabled(true);
                    start.setText("Start");

                    // send record to firebase
                    stopFlag = false;
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