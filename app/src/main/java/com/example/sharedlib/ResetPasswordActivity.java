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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ResetPasswordActivity extends BaseActivity {

    private DatabaseReference mDatabase;
    Boolean resetFlag = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        final EditText emailEditText = findViewById(R.id.text_Mail_reset);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference ref = mDatabase.child("email");



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

        final EditText securityAnswerEditText = findViewById(R.id.text_answer_reset);
        final EditText newPasswordEditText = findViewById(R.id.text_password_reset);

//        final String email = emailEditText.getText().toString();
        final String securityQuestion = securityQuestionSpinner.getSelectedItem().toString();
        final String securityAnswer = securityAnswerEditText.getText().toString();
        final String newPassword = newPasswordEditText.getText().toString();


        Button resetButton = findViewById(R.id.button_reset_reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkFormat(emailEditText.getText().toString(),securityAnswerEditText.getText().toString(),newPasswordEditText.getText().toString())){
                    // send message to firebase
//                    Log.v("Info",email+"_"+securityQuestion+"_"+securityAnswer+"_"+newPassword);
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    String emailAddress = emailEditText.getText().toString();

                    auth.sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("Reset password", "Email sent.");
                                        Toast.makeText(ResetPasswordActivity.this, "The email has been sent.",
                                                Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(ResetPasswordActivity.this, "Cannot find this email.",
                                        Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
//                    Query phoneQuery = ref;
//                    phoneQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
//                                Log.d("Reset password", singleSnapshot.toString());
//                                if (singleSnapshot.getValue().equals(emailEditText.getText().toString())){
//                                    resetFlag = true;
//                                }
//                            }
//                            if(resetFlag){
//                                Toast.makeText(ResetPasswordActivity.this, "Find email.",
//                                        Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//                            Log.e("Reset error", "onCancelled", databaseError.toException());
//                        }
//                    });
                }
            }
        });
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
