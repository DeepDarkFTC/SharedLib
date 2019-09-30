package com.example.sharedlib;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class ResetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        EditText emailEditText = findViewById(R.id.text_Mail_reset);

        Spinner securityQuestionSpinner = findViewById(R.id.text_questions_reset);
        final String[] list = {"What's your favourite number", "What's your favourite colour"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, list);

        securityQuestionSpinner.setAdapter(adapter);
        securityQuestionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String value = list[pos];  // get selected content
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        EditText securityAnswerEditText = findViewById(R.id.text_answer_reset);
        EditText newPasswordEditText = findViewById(R.id.text_password_reset);

        String email = emailEditText.getText().toString();
        String securityQuestion = securityQuestionSpinner.getSelectedItem().toString();
        String securityAnswer = securityAnswerEditText.getText().toString();
        String newPassword = newPasswordEditText.getText().toString();

        if(checkFormat(email,securityAnswer,newPassword)){
            // send message to firebase
            Log.v("Info",email+"_"+securityQuestion+"_"+securityAnswer+"_"+newPassword);
        }
    }

    public Boolean checkFormat(String userName, String securityAnswer, String newPassword) {
        String email = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        if (userName.equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("warning")
                    .setMessage("Username cannot be empty")
                    .setPositiveButton("ok", null)
                    .show();
            return false;
        }
        if (!userName.matches(email)) {
            new AlertDialog.Builder(this)
                    .setTitle("warning")
                    .setMessage("Username should be an email")
                    .setPositiveButton("ok", null)
                    .show();
            return false;
        }
        if (newPassword.equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("warning")
                    .setMessage("Password cannot be empty")
                    .setPositiveButton("ok", null)
                    .show();
            return false;
        }
        if (newPassword.length() < 6) {
            new AlertDialog.Builder(this)
                    .setTitle("warning")
                    .setMessage("The length of Password should larger than 6")
                    .setPositiveButton("ok", null)
                    .show();
            return false;
        }
        if (securityAnswer.equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("warning")
                    .setMessage("Security answer cannot be empty")
                    .setPositiveButton("ok", null)
                    .show();
            return false;
        }
        return true;
    }
}
