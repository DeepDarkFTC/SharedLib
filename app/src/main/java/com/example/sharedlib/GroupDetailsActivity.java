package com.example.sharedlib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GroupDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details);

        Intent parentIntent = getIntent();

        TextView userNameTextView = findViewById(R.id.text_username_groupdetail);
        userNameTextView.setText(parentIntent.getStringExtra("userName"));

        TextView groupNameTextView = findViewById(R.id.text_name_group);
        groupNameTextView.setText(parentIntent.getStringExtra("groupName"));

        TextView groupLocationTextView = findViewById(R.id.text_location_group);
        groupLocationTextView.setText(parentIntent.getStringExtra("groupLocation"));

        TextView studyTimeTextView = findViewById(R.id.text_studytime_group);
        studyTimeTextView.setText(parentIntent.getStringExtra("studyTime"));

        Button logoutButton = findViewById(R.id.button_logout_groupdetail);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutMethod(GroupDetailsActivity.this);
            }
        });
    }
}
