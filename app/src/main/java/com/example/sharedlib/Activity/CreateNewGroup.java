package com.example.sharedlib.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.sharedlib.Object.ComWithDatabase;
import com.example.sharedlib.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateNewGroup extends BaseActivity {

    private Button startButton;
    private Button endButton;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        final Intent parentIntent = getIntent();
        String userName = parentIntent.getStringExtra("userName");

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

        Button createButton = findViewById(R.id.button_form_newgroup);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send message to firebase
                if (checkFormat(groupName.getText().toString(), studyTheme.getText().toString(),
                        startButton.getText().toString(), endButton.getText().toString())) {
                    Log.v("Info", groupName.getText().toString() + "_" +
                            library.getSelectedItem().toString() + "_" +
                            level.getSelectedItem().toString() + "_" +
                            studyTheme.getText().toString() + "_" +
                            startButton.getText().toString() + "_" +
                            endButton.getText().toString());

                    FirebaseUser user = mAuth.getCurrentUser();
                    String key = mDatabase.child("studyGroup").push().getKey();
                    ComWithDatabase comment = new ComWithDatabase(key,
                            groupName.getText().toString(),
                            library.getSelectedItem().toString(),
                            level.getSelectedItem().toString(),
                            studyTheme.getText().toString(),
                            startButton.getText().toString(),
                            endButton.getText().toString(), user.getEmail());

                    mDatabase.child("studyGroup").child(key).setValue(comment);
                    mDatabase.child("groupMember").child(key).child(emailToUid(user.getEmail())).setValue(true);

                    Intent intent = new Intent(CreateNewGroup.this, FormGroupsActivity.class);
                    intent.putExtra("userName", parentIntent.getStringExtra("userName"));
                    startActivity(intent);
                    finish();
                }
            }
        });

        startButton = findViewById(R.id.button_timestart);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartDatePicker();
            }
        });

        endButton = findViewById(R.id.button_timeend);
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEndDatePicker();
            }
        });

        TextView userNameTextView = findViewById(R.id.text_username_newgroup);
        userNameTextView.setText(parentIntent.getStringExtra("userName"));

        Button logoutButton = findViewById(R.id.button_logout_newgroup);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutMethod(CreateNewGroup.this);
            }
        });

    }

    public Boolean checkFormat(String groupName, String studyTheme, String studyTimeStart, String studyTimeEnd) {
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
        if (studyTimeStart.equals("Start Time")) {
            new AlertDialog.Builder(this)
                    .setTitle("warning")
                    .setMessage("Please select the start time")
                    .setPositiveButton("ok", null)
                    .show();
            return false;
        }
        if (studyTimeEnd.equals("End Time")) {
            new AlertDialog.Builder(this)
                    .setTitle("warning")
                    .setMessage("Please select the end time")
                    .setPositiveButton("ok", null)
                    .show();
            return false;
        }
        return true;
    }

    private void showStartDatePicker() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        //startDate.set(2013,1,1);
        Calendar endDate = Calendar.getInstance();
        //endDate.set(2020,1,1);

        //正确设置方式 原因：注意事项有说明
        startDate.set(2013, 0, 1);
        endDate.set(2020, 11, 31);

        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                startButton.setText(getTime(date));
            }
        })
                .setType(new boolean[]{false, true, true, true, true, false})// 默认全部显示
                .setCancelText("Cancel")//取消按钮文字
                .setSubmitText("OK")//确认按钮文字
                .setTitleSize(20)//标题文字大小
                .setTitleText("")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                //.setSubmitColor(getResources().getColor(R.color.fpyj_second_phase_color))//确定按钮文字颜色
                //.setCancelColor(getResources().getColor(R.color.fpyj_second_phase_color))//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("year", "Month", "Day", "Hour", "Min", "Sec")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(true)//是否显示为对话框样式
                .build();

        pvTime.show();
    }

    private void showEndDatePicker() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        //startDate.set(2013,1,1);
        Calendar endDate = Calendar.getInstance();
        //endDate.set(2020,1,1);

        //正确设置方式 原因：注意事项有说明
        startDate.set(2013, 0, 1);
        endDate.set(2020, 11, 31);

        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                endButton.setText(getTime(date));
            }
        })
                .setType(new boolean[]{false, true, true, true, true, false})// 默认全部显示
                .setCancelText("Cancel")//取消按钮文字
                .setSubmitText("OK")//确认按钮文字
                .setTitleSize(20)//标题文字大小
                .setTitleText("")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                //.setSubmitColor(getResources().getColor(R.color.fpyj_second_phase_color))//确定按钮文字颜色
                //.setCancelColor(getResources().getColor(R.color.fpyj_second_phase_color))//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("year", "Month", "Day", "Hour", "Min", "sec")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(true)//是否显示为对话框样式
                .build();

        pvTime.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        return format.format(date);
    }
}