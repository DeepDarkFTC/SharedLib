package com.example.sharedlib;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class RegisterActivity extends BaseActivity {

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]


    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        mDatabase = FirebaseDatabase.getInstance().getReference();


        final TextView emailTextView = findViewById(R.id.register_email);
        final TextView userNameTextView = findViewById(R.id.register_username);
        final TextView passwordTextView = findViewById(R.id.register_password);
        passwordTextView.setInputType(129);//不可见

        final String[] list = {"What's your favourite numberView", "What's your favourite colour"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, list);


        Button registerButton = findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFormat(emailTextView.getText().toString(), userNameTextView.getText().toString(), passwordTextView.getText().toString())) {
                    // send message to firebase include [userName,Psd,SecurityQ,answer]

                    createAccount(emailTextView.getText().toString(), userNameTextView.getText().toString(), passwordTextView.getText().toString());
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

    private void createAccount(String email, final String userName, String password) {
        Log.d("createAccount", "createAccount" + email);

        //Check the validation here
//        if (!validateForm()) {
//            return;
//        }

        showProgressDialog();


        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("createUserWithEmail", "createUserWithEmail:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(RegisterActivity.this, "Registration successful.",
                                    Toast.LENGTH_SHORT).show();

                            writeToDatabase(task, userName);


                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("createUserWithEmail", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Registration failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]




    }

    public Boolean checkFormat(String email, String userName, String password) {
        String emailFormat = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        if (email.equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("warning")
                    .setMessage("Email address cannot be empty")
                    .setPositiveButton("ok", null)
                    .show();
            return false;
        }
        if (!email.matches(emailFormat)) {
            new AlertDialog.Builder(this)
                    .setTitle("warning")
                    .setMessage("You should input a correct email address")
                    .setPositiveButton("ok", null)
                    .show();
            return false;
        }
        if (userName.equals("")) {
            new AlertDialog.Builder(this)
                    .setTitle("warning")
                    .setMessage("Username cannot be empty")
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

    private void writeToDatabase(Task<AuthResult> task, String userName) {
        FirebaseUser user = task.getResult().getUser();
        Log.d("createUserWithEmail", user.toString());
        String userId = emailToUid(user.getEmail());
        mDatabase.child("userName").child(userId).setValue(userName);
    }


}
