package com.example.sharedlib.Object;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sharedlib.Activity.ArchitectureLibraryActivity;
import com.example.sharedlib.Activity.BaillieuLibraryActivity;
import com.example.sharedlib.Activity.ErcLibraryActivity;
import com.example.sharedlib.Activity.GiblinLibraryActivity;
import com.example.sharedlib.R;

import java.util.List;

public class LibraryAdapter_forMapView extends RecyclerView.Adapter<LibraryAdapter_forMapView.ViewHolder> {

    private RecyclerView parentRecycler;
    private List<Library_forMapView> data;
    private Context mContext;
    private String username_me;

    public LibraryAdapter_forMapView(List<Library_forMapView> data, Context mContext, String username_me) {
        this.data = data;
        this.mContext = mContext;
        this.username_me = username_me;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        parentRecycler = recyclerView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_library_for_mapview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Library_forMapView library = data.get(position);
        holder.imageView_lib.setImageResource(library.getPicture());
        holder.textView_name_lib.setText(library.getName());
        holder.textView_crowed_lib.setText("Crowded degree: " + String.valueOf(library.getCrowded()) + "%");
        holder.textView_distance_lib.setText("Distance: " + String.valueOf((int) library.getDistance()) + "m");
        holder.botton_detail_lib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext, "到时候点这个就跳到"+data.get(position).getName()+"图书馆",
//                        Toast.LENGTH_SHORT).show();
                Intent intent;
                switch (data.get(position).getName()) {
                    case "ERC":
                        intent = new Intent(mContext, ErcLibraryActivity.class);
                        intent.putExtra("userName", username_me);
                        mContext.startActivity(intent);
                        break;
                    case "Baillieu":
                        intent = new Intent(mContext, BaillieuLibraryActivity.class);
                        intent.putExtra("userName", username_me);
                        mContext.startActivity(intent);
                        break;
                    case "Architecture":
                        intent = new Intent(mContext, ArchitectureLibraryActivity.class);
                        intent.putExtra("userName", username_me);
                        mContext.startActivity(intent);
                        break;
                    case "Giblin":
                        intent = new Intent(mContext, GiblinLibraryActivity.class);
                        intent.putExtra("userName", username_me);
                        mContext.startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView_lib;
        private TextView textView_name_lib;
        private TextView textView_crowed_lib;
        private TextView textView_distance_lib;
        private Button botton_detail_lib;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView_lib = (ImageView) itemView.findViewById(R.id.library_image_mapView);
            textView_name_lib = (TextView) itemView.findViewById(R.id.library_name_mapView);
            textView_crowed_lib = (TextView) itemView.findViewById(R.id.library_crowed_mapView);
            textView_distance_lib = (TextView) itemView.findViewById(R.id.library_distance_mapview);
            botton_detail_lib = (Button) itemView.findViewById(R.id.button_libraryDetail_mapView);

            itemView.findViewById(R.id.relativeLayout_library_mapView).setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            System.out.println("你点击了第" + String.valueOf(getAdapterPosition()) + "个图");
            parentRecycler.smoothScrollToPosition(getAdapterPosition());
        }
    }
}
