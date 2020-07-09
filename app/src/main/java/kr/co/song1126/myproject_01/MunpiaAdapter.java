package kr.co.song1126.myproject_01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MunpiaAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<MyBookItems> items;

    public MunpiaAdapter(Context context, ArrayList<MyBookItems> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(context).inflate(R.layout.munpia_item, parent, false);
        VH holder=new VH(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh=(VH) holder;

        Glide.with(context).load(items.get(position).imgUrl).into(vh.bookImg);
        vh.title.setText(items.get(position).title);
        vh.kategorie.setText(items.get(position).kategorie);
        vh.bookName.setText(items.get(position).bookName);
        vh.date.setText(items.get(position).date);
        vh.views.setText(items.get(position).views);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        ImageView bookImg;
        TextView title;
        TextView kategorie;
        TextView bookName;
        TextView date;
        TextView views;


        public VH(@NonNull View itemView) {
            super(itemView);

            bookImg=itemView.findViewById(R.id.munpia_item_iv);
            title=itemView.findViewById(R.id.munpia_item_title);
            kategorie=itemView.findViewById(R.id.munpia_DB_Kategorie);
            bookName=itemView.findViewById(R.id.munpia_DB_bookName);
            date=itemView.findViewById(R.id.munpia_DB_date);
            views=itemView.findViewById(R.id.munpia_DB_views);
        }
    }

}


