package com.example.sharedlib;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class SearchSeatsActivity extends BaseActivity {

    private TextView userNameTextView;
    private OverallSeatsSituation overallSeatsSituation = new OverallSeatsSituation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_seats);

        Intent parentIntent = getIntent();
        String userName = parentIntent.getStringExtra("userName");

        userNameTextView = findViewById(R.id.text_username_searchseats);
        userNameTextView.setText(userName);

        ImageButton architectureLibrary = findViewById(R.id.button_arc_searchseats);
        architectureLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchSeatsActivity.this, ArchitectureLibraryActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                startActivity(intent);
            }
        });

        TextView arcSeatsTextView = findViewById(R.id.text_arc_seats);
        arcSeatsTextView.setText(overallSeatsSituation.getArcSeats());

        ImageButton bailieuLibrary = findViewById(R.id.button_bai_searchseats);
        bailieuLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchSeatsActivity.this, BaillieuLibraryActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                startActivity(intent);
            }
        });

        TextView baiSeatsTextView = findViewById(R.id.text_bai_seats);
        baiSeatsTextView.setText(overallSeatsSituation.getBaiSeats());

        ImageButton ercLibrary = findViewById(R.id.button_erc_searchseats);
        ercLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchSeatsActivity.this, ErcLibraryActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                startActivity(intent);
            }
        });

        TextView ercSeatsTextView = findViewById(R.id.text_erc_seats);
        ercSeatsTextView.setText(overallSeatsSituation.getErcSeats());

        ImageButton giblinLibrary = findViewById(R.id.button_giblin_searchseats);
        giblinLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchSeatsActivity.this, GiblinLibraryActivity.class);
                intent.putExtra("userName", userNameTextView.getText().toString());
                startActivity(intent);
            }
        });

        TextView gibSeatsTextView = findViewById(R.id.text_gib_seats);
        gibSeatsTextView.setText(overallSeatsSituation.getGibSeats());

        Button logoutButton = findViewById(R.id.button_logout_searchseats);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutMethod(SearchSeatsActivity.this);
            }
        });
    }
}
