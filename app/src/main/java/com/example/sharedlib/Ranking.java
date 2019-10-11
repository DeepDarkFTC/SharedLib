package com.example.sharedlib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Ranking extends BaseActivity {

    private DatabaseReference mDatabase;
    private ArrayList commentList = new ArrayList();
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studytime_ranking);

        Intent parentIntent = getIntent();
        final String userName = parentIntent.getStringExtra("userName");

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        TextView userNameTextView = findViewById(R.id.text_username_ranking);
        userNameTextView.setText(userName);

        final TextView studyTimeTextView = findViewById(R.id.text_studytime_ranking);
        studyTimeTextView.setText(parentIntent.getStringExtra("studyTime"));

        DatabaseReference ref = mDatabase.child("studyTime");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                commentList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    ComWithDatabase childComment = postSnapshot.getValue(ComWithDatabase.class);
                    ComWithDatabase comment = new ComWithDatabase(postSnapshot.getKey(), childComment.getComment(), childComment.getDate());
                    commentList.add(comment);
                    Log.d("Database content", commentList.toString());

                    ArrayList temp = new ArrayList();

                    for (int i = 0; i < commentList.size(); i++) {
                        ComWithDatabase tempObj = (ComWithDatabase) commentList.get(i);
                        temp.add(tempObj);
                        sortByTime(temp);
                    }

                    String[] result = new String[temp.size()];
                    for (int i = 0; i < result.length; i++) {
                        ComWithDatabase tempObj = (ComWithDatabase) temp.get(i);
                        String record = "No."+ (i+1) +" "+"user: " + tempObj.getComment() + "  " + "study time: " + tempObj.getDate();
                        result[i] = record;
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Ranking.this, android.R.layout.simple_list_item_1, result);
                    ListView listView = findViewById(R.id.listview_ranklist_ranking);
                    listView.setAdapter(adapter);
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

    public void sortByTime(ArrayList list){
        Collections.sort(list, new Comparator(){
            @Override
            public int compare(Object o1, Object o2) {
                ComWithDatabase obj1 = (ComWithDatabase)o1;
                ComWithDatabase obj2 = (ComWithDatabase)o2;
                if(Integer.parseInt(obj1.getDate()) < Integer.parseInt(obj2.getDate())){
                    return 1;
                }else if(Integer.parseInt(obj1.getDate()) == Integer.parseInt(obj2.getDate())){
                    return 0;
                }else{
                    return -1;
                }
            }
        });
    }

}
