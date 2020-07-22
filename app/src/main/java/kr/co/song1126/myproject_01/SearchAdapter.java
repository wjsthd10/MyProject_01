package kr.co.song1126.myproject_01;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<RecyclerViewitems> items;

    public SearchAdapter(Context context, ArrayList<RecyclerViewitems> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(context).inflate(R.layout.search_item, parent,false);
        VH holder=new VH(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh=(VH)holder;
        String imgUrl="http://wjsthd10.dothome.co.kr/MyProject01/"+items.get(position).imgUrl;

        vh.title.setText(items.get(position).title);
        Glide.with(context).load(imgUrl).into(vh.bookImg);
        vh.kategorie.setText(items.get(position).kategorie);
        vh.bookName.setText(items.get(position).bookName);
        vh.date.setText(items.get(position).date);
        vh.views.setText(items.get(position).views);

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ClickRecyclerItemActivity.class);
                intent.putExtra("viewNum", items.get(position).num);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView title;
        ImageView bookImg;
        TextView kategorie;
        TextView bookName;
        TextView date;
        TextView views;


        public VH(@NonNull View itemView) {
            super(itemView);

            bookImg=itemView.findViewById(R.id.search_item_iv);
            title=itemView.findViewById(R.id.search_item_title);
            kategorie=itemView.findViewById(R.id.search_DB_Kategorie);
            bookName=itemView.findViewById(R.id.search_DB_bookName);
            date=itemView.findViewById(R.id.search_DB_date);
            views=itemView.findViewById(R.id.search_DB_views);
        }
    }

}
