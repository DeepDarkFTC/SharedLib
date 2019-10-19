package com.example.sharedlib;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SeatAdapter extends BaseAdapter implements View.OnClickListener {
    //private static final SeatAdapter ourInstance = new SeatAdapter();

//    public static SeatAdapter getInstance() {
    //       return ourInstance;
    // }

    //上下文
    private Context context;
    //数据项
    private List<String> data;
    private String key;
    public SeatAdapter(List<String> data){
        this.data = data;
    }
    private String location;
    private DatabaseReference mDatabase;
    private int thumbNumer = 0;

    private ViewHolder viewHolder;


//    private SeatAdapter(Context context, int textViewReourceId, List<String> objects) {
//        super(context, textViewReourceId, objects);
//        resourceid = textViewReourceId;
//
//    }


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
    public View getView(int i, @Nullable View view, @NonNull ViewGroup viewGroup) {
        viewHolder = null;
                //String information = getItem(position);
                //return super.getView(position, convertView, parent);

        if (context == null)
            context = viewGroup.getContext();
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.seat_listview, null);
            viewHolder = new ViewHolder();
            viewHolder.mTv = (TextView) view.findViewById(R.id.text_seats_listview);
            viewHolder.number = (TextView) view.findViewById(R.id.text_number_listview);
            viewHolder.good = (Button) view.findViewById(R.id.button_good_listview);
            viewHolder.bad = (Button) view.findViewById(R.id.button_bad_listview);

            view.setTag(viewHolder);
        }
        //获取viewHolder实例
        viewHolder = (ViewHolder)view.getTag();
        //设置数据
        viewHolder.mTv.setText(data.get(i));
        //设置监听事件
        viewHolder.mTv.setOnClickListener(this);
        //设置数据
        //viewHolder.good.setText("good");
        viewHolder.good.setOnClickListener(this);

        //viewHolder.bad.setText("bad");
        viewHolder.bad.setOnClickListener(this);

        String[] thumbData = data.get(i).split("thumb num: ");
        String[] thumbData1 = thumbData[1].split("____");
        String[] keyData = data.get(i).split("key: ");
        String[] locationData = data.get(i).split("location: ");
        String[] locationData1 = locationData[1].split("____");
        viewHolder.number.setText(thumbData1[0]);
        thumbNumer = Integer.parseInt(thumbData1[0]);
        key = keyData[1];
        location = locationData1[0];
        Log.v("查看数据",data.get(i));
        Log.v("查看具体数据",thumbNumer+"||||"+key+"||||"+location);


        return view;

    }

    @Override
    public void onClick(View view) {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        switch (view.getId()){
            case R.id.button_good_listview:
                Log.d("tag", "Btn_onClick: " + "view = " + view);
                Toast.makeText(context,"+1",Toast.LENGTH_SHORT).show();
                viewHolder.number.setText((thumbNumer+1)+"");
                mDatabase.child("searchSeats").child("location").child(location).child(key).child("thumbNumber").setValue(thumbNumer+1);
                break;
            case R.id.button_bad_listview:
                Log.d("tag", "Tv_onClick: " + "view = " + view);
                Toast.makeText(context,"-1",Toast.LENGTH_SHORT).show();
                break;
        }
    }


    static class ViewHolder {
        TextView mTv;
        TextView number;
        Button good;
        Button bad;
    }
}
