package kr.co.song1126.myproject_01;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyRecommendAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<MyBookItems> items;

    public MyRecommendAdapter() {
    }

    public MyRecommendAdapter(Context context, ArrayList<MyBookItems> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(context).inflate(R.layout.myrecommend_items, parent, false);
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
        vh.tvD.setText(items.get(position).delete);
        vh.line1.setText(items.get(position).line1);
        vh.line2.setText(items.get(position).line2);
        vh.line3.setText(items.get(position).line3);
        vh.views01.setText(items.get(position).views01);
        vh.tvD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //다이얼로그

                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("삭제하시겠습니까?");

                builder.setNegativeButton("NO",null);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // todo 다이얼로그로 확인 버튼 누르면 삭제하기
                    }
                });

                AlertDialog dialog=builder.create();
                dialog.show();
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
        TextView tvD;
        TextView line1,line2,line3;
        TextView views01;

        public VH(@NonNull View itemView) {
            super(itemView);

            bookImg=itemView.findViewById(R.id.my_item_iv);
            title=itemView.findViewById(R.id.my_item_title);
            kategorie=itemView.findViewById(R.id.my_DB_Kategorie);
            bookName=itemView.findViewById(R.id.my_DB_bookName);
            date=itemView.findViewById(R.id.my_DB_date);
            views=itemView.findViewById(R.id.my_DB_views);
            tvD=itemView.findViewById(R.id.my_delete);
            line1=itemView.findViewById(R.id.tv_line01);
            line2=itemView.findViewById(R.id.tv_line02);
            line3=itemView.findViewById(R.id.tv_line03);
            views01=itemView.findViewById(R.id.my_views);

        }
    }

}
