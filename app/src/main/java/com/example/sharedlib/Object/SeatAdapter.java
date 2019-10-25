package com.example.sharedlib.Object;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sharedlib.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class SeatAdapter extends BaseAdapter {

    private Context context;
    private List<String> data;  // data from firebase
    private List<String> displayData;   // data used to display

    private ListView listView;
    private DatabaseReference mDatabase;

    public SeatAdapter(List<String> data, List<String> displayData, ListView listView) {
        this.data = data;
        this.displayData = displayData;
        this.listView = listView;
    }

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
        ViewHolder viewHolder;

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

        // get the viewHolder instance
        viewHolder = (ViewHolder) view.getTag();
        // set data
        viewHolder.mTv.setText(displayData.get(position));
        viewHolder.mTv.setTag("mTv" + position);

        // set listener
        viewHolder.good.setOnClickListener(new InnerOnClickListener(position));
        viewHolder.bad.setOnClickListener(new InnerOnClickListener(position));

        String[] thumbData = data.get(position).split("thumb num: ");
        String[] thumbData1 = thumbData[1].split("____");
        String[] keyData = data.get(position).split("key: ");

        viewHolder.numberView.setText(thumbData1[0]);
        viewHolder.numberView.setTag("numberView" + position);

        viewHolder.number = Integer.parseInt(thumbData1[0]);
        viewHolder.key = keyData[1];

        return view;

    }

    public class ViewHolder {
        TextView mTv;   // display specific information
        TextView numberView;    // overall thumb up/down
        Button good;
        Button bad;
        int number;
        String key;
    }

    public class InnerOnClickListener implements View.OnClickListener {
        int position;

        private InnerOnClickListener(int position) {
            super();
            this.position = position;
        }

        @Override
        public void onClick(View v) {

            TextView thumbCountTextView = listView.findViewWithTag("numberView" + position);

            String[] thumbData = data.get(position).split("thumb num: ");
            String[] thumbData1 = thumbData[1].split("____");
            String[] keyData = data.get(position).split("key: ");
            String[] locationData = data.get(position).split("location: ");
            String[] locationData1 = locationData[1].split("____");

            String location = locationData1[0];

            int number = Integer.parseInt(thumbData1[0]);
            String key = keyData[1];

            // thumb up and thumb down
            switch (v.getId()) {
                case R.id.button_good_listview:
                    thumbCountTextView.setText((number + 1) + "");
                    mDatabase.child("searchSeats").child("location").child(location).child(key).
                            child("thumbNumber").setValue(String.valueOf(number + 1));
                    break;
                case R.id.button_bad_listview:
                    thumbCountTextView.setText((number - 1) + "");
                    mDatabase.child("searchSeats").child("location").child(location).child(key).
                            child("thumbNumber").setValue(String.valueOf(number - 1));
            }
        }
    }
}
