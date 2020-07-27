package kr.co.song1126.myproject_01;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
    ArrayList<RecyclerViewitems> items;

    String viewNum;

    public MunpiaAdapter(Context context, ArrayList<RecyclerViewitems> items) {
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

        RecyclerViewitems item=items.get(position);
        String imgUri="http://wjsthd10.dothome.co.kr/MyProject01/"+item.imgUrl;
        viewNum=items.get(position).num;

        Log.w("URLSS", imgUri);

        Glide.with(context).load(imgUri).into(vh.bookImg);
        vh.title.setText(items.get(position).title);
        vh.kategorie.setText(items.get(position).kategorie);
        vh.bookName.setText(items.get(position).bookName);
        vh.date.setText(items.get(position).date);
        vh.views.setText(items.get(position).views);

        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //서버의 views ++하기
                int bookViewInt=Integer.parseInt(items.get(position).views);
                bookViewInt++;
                String bookViewStr=Integer.toString(bookViewInt);
                G.bookViewsUpdate(bookViewStr, items.get(position).num);

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

        ImageView bookImg;
        TextView title;
        TextView kategorie;
        TextView bookName;
        TextView date;
        TextView views;
        TextView line1,line2,line3;
        TextView views01;


        public VH(@NonNull View itemView) {
            super(itemView);

            bookImg=itemView.findViewById(R.id.munpia_item_iv);
            title=itemView.findViewById(R.id.munpia_item_title);
            kategorie=itemView.findViewById(R.id.munpia_DB_Kategorie);
            bookName=itemView.findViewById(R.id.munpia_DB_bookName);
            date=itemView.findViewById(R.id.munpia_DB_date);
            views=itemView.findViewById(R.id.munpia_DB_views);
            line1=itemView.findViewById(R.id.munpia_line1);
            line2=itemView.findViewById(R.id.munpia_line2);
            line3=itemView.findViewById(R.id.munpia_line3);
            views01=itemView.findViewById(R.id.munpia_views);

        }
    }

}


