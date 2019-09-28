package com.example.sharedlib;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;


public class RegisterActivity extends AppCompatActivity{

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private TextView userName;
    private TextView password;
    private Spinner securityQuestions;
    private Button registerButton;
    private TextView securityAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        userName = findViewById(R.id.logon_name);
        password = findViewById(R.id.logon_password);
        securityAnswer = findViewById(R.id.logon_answer);

        securityQuestions = findViewById(R.id.spinner);
        final String[] list = {"What's your favourite number","What's your favourite colour"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,list);

        securityQuestions.setAdapter(adapter );
        securityQuestions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String value = list [pos];  // get selected content
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        
        registerButton = findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkFormat(userName.getText().toString(),password.getText().toString())){
                    // send message to firebase include [userName,Psd,SecurityQ,answer]

                }
            }
        });

    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //Can add updateUI function
        //updateUI(currentUser);
    }
    // [END on_start_check_user]

    private void createAccount(String email, String password) {
        Log.d("createAccount","createAccount" + email);

        //Check the validation here
//        if (!validateForm()) {
//            return;
//        }

        //showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("ccreateUserWithEmail", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("ccreateUserWithEmail", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    public Boolean checkFormat(String userName, String password) {
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
        if (password.equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("warning")
                    .setMessage("Password cannot be empty")
                    .setPositiveButton("ok", null)
                    .show();
            return false;
        }
        if (password.length() < 6) {
            new AlertDialog.Builder(this)
                    .setTitle("warning")
                    .setMessage("The length of Password should larger than 6")
                    .setPositiveButton("ok", null)
                    .show();
            return false;
        }
        return true;
    }
}
