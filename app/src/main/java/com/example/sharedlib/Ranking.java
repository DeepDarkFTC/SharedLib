package com.example.sharedlib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class Ranking extends BaseActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private int lastTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studytime_ranking);

        Intent parentIntent = getIntent();
        final String userName = parentIntent.getStringExtra("userName");

        final FirebaseUser user = mAuth.getCurrentUser();

        TextView userNameTextView = findViewById(R.id.text_username_ranking);
        userNameTextView.setText(userName);

        final TextView studyTimeTextView = findViewById(R.id.text_studytime_ranking);

        DatabaseReference ref = mDatabase.child("studyTime").child(emailToUid(user.getEmail()));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    lastTime = Integer.parseInt(dataSnapshot.getValue().toString());
                    Log.d("上次时间", String.valueOf(lastTime));
                    studyTimeTextView.setText("Total study time: " + lastTime);
                }
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
