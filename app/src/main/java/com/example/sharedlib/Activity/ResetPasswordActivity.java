package com.example.sharedlib.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.sharedlib.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ResetPasswordActivity extends BaseActivity {

    Boolean resetFlag = false;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


        final EditText emailEditText = findViewById(R.id.text_Mail_reset);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference ref = mDatabase.child("email");


        Button resetButton = findViewById(R.id.button_reset_reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFormat(emailEditText.getText().toString())) {
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
                                        Intent intent = new Intent(ResetPasswordActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    } else {
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

    public Boolean checkFormat(String userName) {
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
        return true;
    }
}
