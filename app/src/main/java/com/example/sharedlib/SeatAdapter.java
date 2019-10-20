package com.example.sharedlib;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SeatAdapter extends BaseAdapter {

    private Context context;
    private List<String> data;

    private ListView listView;
//    private int number;
//    private String key;

    public SeatAdapter(List<String> data, ListView listView){
        this.data = data;
        this.listView = listView;
    }
//    private String location;
    private DatabaseReference mDatabase;


    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
                //String information = getItem(position);
                //return super.getView(position, convertView, parent);

        if (context == null)
            context = viewGroup.getContext();
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.seat_listview, null);
            viewHolder = new ViewHolder();
            viewHolder.mTv = view.findViewById(R.id.text_seats_listview);
            viewHolder.numberView = view.findViewById(R.id.text_number_listview);
            viewHolder.good = view.findViewById(R.id.button_good_listview);
            viewHolder.bad = view.findViewById(R.id.button_bad_listview);

            view.setTag(viewHolder);
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();

        //获取viewHolder实例
        viewHolder = (ViewHolder)view.getTag();
        //设置数据
        viewHolder.mTv.setText(data.get(position));
        viewHolder.mTv.setTag("mTv"+position);
        //设置监听事件

        viewHolder.good.setOnClickListener(new InnerOnClickListener(position));
//        viewHolder.good.setTag(position);


        viewHolder.bad.setOnClickListener(new InnerOnClickListener(position));
//        viewHolder.bad.setTag(position);

//                    viewHolder.numberView.setText(thumbData1[0]);
        String[] thumbData = data.get(position).split("thumb num: ");
        String[] thumbData1 = thumbData[1].split("____");
        String[] keyData = data.get(position).split("key: ");
        String[] locationData = data.get(position).split("location: ");
        String[] locationData1 = locationData[1].split("____");

        viewHolder.numberView.setText(thumbData1[0]);
        viewHolder.numberView.setTag("numberView"+position);

        viewHolder.number = Integer.parseInt(thumbData1[0]);
        viewHolder.key = keyData[1];



        return view;

    }

  /*  public void function(ViewHolder viewHolder){
        viewHolder.numberView.setText((viewHolder.number+1)+"");
        mDatabase.child("searchSeats").child("location").child(location).child(viewHolder.key).child("thumbNumber").setValue(String.valueOf(viewHolder.number+1));
        Log.v("查看key",viewHolder.key);
    }*/

/*
    @Override
    public void onClick(View view) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        int position;
        position = (Integer)view.getTag();
        Log.v("位置:", position+"");

        switch (view.getId()){
            case R.id.button_good_listview:
                Log.d("tag", "Btn_onClick: " + "view = " + view);
                Toast.makeText(context,"+1",Toast.LENGTH_SHORT).show();
                viewHolder.numberView.setText((viewHolder.number+1)+"");
                mDatabase.child("searchSeats").child("location").child(location).child(viewHolder.key).child("thumbNumber").setValue(String.valueOf(viewHolder.number+1));
                Log.v("查看key",viewHolder.key);
                break;
            case R.id.button_bad_listview:
                Log.d("tag", "Tv_onClick: " + "view = " + view);
                Toast.makeText(context,"-1",Toast.LENGTH_SHORT).show();
                viewHolder.numberView.setText((viewHolder.number-1)+"");
                mDatabase.child("searchSeats").child("location").child(location).child(viewHolder.key).child("thumbNumber").setValue(String.valueOf(viewHolder.number-1));
                Log.v("查看key",viewHolder.key);
                break;
        }
    }
*/

    public class ViewHolder {
        TextView mTv;
        TextView numberView;
        Button good;
        Button bad;
        int number;
        String key;
    }

    public class InnerOnClickListener implements View.OnClickListener{
        int position;
        public  InnerOnClickListener(int position){
            super();
            this.position = position;
        }

        @Override
        public void onClick(View v){

            TextView thumbCountTextView = listView.findViewWithTag("numberView"+position);
            TextView dataTextView = listView.findViewWithTag("mTv"+position);
            Log.v("查看",dataTextView.getText().toString());

            String[] thumbData = dataTextView.getText().toString().split("thumb num: ");
            String[] thumbData1 = thumbData[1].split("____");
            String[] keyData = dataTextView.getText().toString().split("key: ");
            String[] locationData = dataTextView.getText().toString().split("location: ");
            String[] locationData1 = locationData[1].split("____");

//            viewHolder.numberView.setText(thumbData1[0]);
//            viewHolder.numberView.setTag("numberView"+position);
//
//            viewHolder.number = Integer.parseInt(thumbData1[0]);
//            viewHolder.key = keyData[1];

            String location = locationData1[0];

            int number = Integer.parseInt(thumbData1[0]);
            String key = keyData[1];

            Log.v("查看数据",data.get(position));
            Log.v("查看具体数据",number+"||||"+ key+"||||"+location);


            switch (v.getId()){
                case R.id.button_good_listview:
                    thumbCountTextView.setText((number+1)+"");
                    mDatabase.child("searchSeats").child("location").child(location).child(key).child("thumbNumber").setValue(String.valueOf(number+1));
                    Log.v("查看key",key);
                    break;
                case R.id.button_bad_listview:
                    thumbCountTextView.setText((number-1)+"");
                    mDatabase.child("searchSeats").child("location").child(location).child(key).child("thumbNumber").setValue(String.valueOf(number-1));
                    Log.v("查看key",key);
            }
        }
    }
}
