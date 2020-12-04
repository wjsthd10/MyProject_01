package kr.co.song1126.myproject_01;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class KakaoAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<RecyclerViewitems> items;

    public KakaoAdapter() {
    }

    public KakaoAdapter(Context context, ArrayList<RecyclerViewitems> items) {
        this.context = context;
        this.items = items;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(context).inflate(R.layout.kakao_item, parent, false);
        VH holder=new VH(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh=(VH) holder;

        RecyclerViewitems item=items.get(position);
        String imgUrl="http://wjsthd10.dothome.co.kr/MyProject01/"+item.imgUrl;


        Log.w("URLLLL",items.get(position).num);
        vh.title.setText(items.get(position).title);
        Glide.with(context).load(imgUrl).into(vh.bookImg);
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

//    public void notifyDataSetChanged() {
//    }

    class VH extends RecyclerView.ViewHolder{

        TextView title;
        ImageView bookImg;
        TextView kategorie;
        TextView bookName;
        TextView date;
        TextView views;

        public VH(@NonNull View itemView) {
            super(itemView);

            bookImg=itemView.findViewById(R.id.kakao_item_iv);
            title=itemView.findViewById(R.id.kakao_item_title);
            kategorie=itemView.findViewById(R.id.kakao_DB_Kategorie);
            bookName=itemView.findViewById(R.id.kakao_DB_bookName);
            date=itemView.findViewById(R.id.kakao_DB_date);
            views=itemView.findViewById(R.id.kakao_DB_views);



        }
    }
}

