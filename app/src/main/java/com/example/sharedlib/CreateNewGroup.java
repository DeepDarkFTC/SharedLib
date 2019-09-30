package com.example.sharedlib;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class CreateNewGroup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);

        final EditText groupName = findViewById(R.id.text_name_newgroup);

        final Spinner library = findViewById(R.id.spinner_library_newgroup);
        final String[] librayList = {"Architecture Library", "Baillieu Library", "ERC Library", "Giblin Library"};
        ArrayAdapter<String> libraryAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, librayList);
        library.setAdapter(libraryAdapter);
        library.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String value = librayList[pos];  // get selected content
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        final Spinner level = findViewById(R.id.spinner_level_newgroup);
        final String[] levelList = {"Level 1", "Level 2", "Level 3", "Level 4", "Level 5"};
        ArrayAdapter<String> levelAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, levelList);
        level.setAdapter(levelAdapter);
        level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String value = levelList[pos];  // get selected content
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        final EditText studyTheme = findViewById(R.id.text_content_newgroup);

        final EditText studyTime = findViewById(R.id.text_time_newgroup);

        Button createButton = findViewById(R.id.button_form_newgroup);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send message to firebase
                if (checkFormat(groupName.getText().toString(), studyTheme.getText().toString(), studyTime.getText().toString())) {
                    Log.v("Info", groupName.getText().toString() + "_" + library.getSelectedItem().toString() + "_" + level.getSelectedItem().toString() + "_" + studyTheme.getText().toString() + "_" + studyTime.getText().toString());
                }
            }
        });

    }

    public Boolean checkFormat(String groupName, String studyTheme, String studyTime) {
        if (groupName.equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("warning")
                    .setMessage("Group name cannot be empty")
                    .setPositiveButton("ok", null)
                    .show();
            return false;
        }
        if (studyTheme.equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("warning")
                    .setMessage("You should point out a study theme")
                    .setPositiveButton("ok", null)
                    .show();
            return false;
        }
        if (studyTime.equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("warning")
                    .setMessage("You should input the study time")
                    .setPositiveButton("ok", null)
                    .show();
            return false;
        }
        return true;
    }
}
