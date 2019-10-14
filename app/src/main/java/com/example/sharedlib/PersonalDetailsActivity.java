package com.example.sharedlib;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PersonalDetailsActivity extends BaseActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String personalUsername;
    private String personalEmail;
    private int personalStudyTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);

        final TextView userNameTextView = findViewById(R.id.text_name_person);
        final TextView emailTextView = findViewById(R.id.text_mail_person);
        final TextView studyTimeTextView = findViewById(R.id.text_studytime_person);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        personalEmail = user.getEmail();
        emailTextView.setText(personalEmail);

        DatabaseReference ref1 = mDatabase.child("studyTime").child(emailToUid(user.getEmail()));
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    personalStudyTime = Integer.parseInt(dataSnapshot.getValue(ComWithDatabase.class).getDate());
                    Log.d("上次时间", String.valueOf(personalStudyTime));
                    studyTimeTextView.setText(personalStudyTime);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Database error", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

        DatabaseReference ref2 = mDatabase.child("userName");
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                personalUsername = dataSnapshot.child(emailToUid(user.getEmail())).getValue().toString();
                Log.d("Database content222", personalUsername);
                userNameTextView.setText(personalUsername);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Database error", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

    }
}
