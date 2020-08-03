package kr.co.song1126.myproject_01;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
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
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyRecommendAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<MyRecommendItem> items;

    public MyRecommendAdapter() {
    }

    public MyRecommendAdapter(Context context, ArrayList<MyRecommendItem> items) {
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
                //나의 추천글 수정하는 화면으로 전환 새로운 액티비티 만들기
                Intent intent=new Intent(context, MyRecommendView_retouch_Activity.class);
                intent.putExtra("viewNum", items.get(position).num);
                context.startActivity(intent);
            }
        });

         vh.tvD.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 //다이얼로그
                 AlertDialog.Builder builder=new AlertDialog.Builder(context);
                 builder.setTitle("게시글을 삭제하시겠습니까?");
                 builder.setMessage("서버의 데이터를 지우시면 다시 복구할 수 없습니다.");

                 builder.setNegativeButton("NO",null);
                 builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         Map<String, String> dataPart=new HashMap<>();
                         dataPart.put("num", items.get(position).num);

                         Retrofit retrofit=RetrofitHelper.getInstance();
                         RetrofitService retrofitService=retrofit.create(RetrofitService.class);
                         Call<String> call=retrofitService.deleteData(dataPart);
                         call.enqueue(new Callback<String>() {
                             @Override
                             public void onResponse(Call<String> call, Response<String> response) {
                                 if (response.isSuccessful()) {
                                     String s=response.body();
                                     Log.e("DELETEONCLICK", s);
                                 }
                             }
                             @Override
                             public void onFailure(Call<String> call, Throwable t) {
                                 Log.e("DELETEONCLICK", t.getMessage());
                             }
                         });
                         Toast.makeText(context, "게시글이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
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


        public VH(@NonNull View itemView) {
            super(itemView);

            bookImg=itemView.findViewById(R.id.my_item_iv);
            title=itemView.findViewById(R.id.my_item_title);
            kategorie=itemView.findViewById(R.id.my_DB_Kategorie);
            bookName=itemView.findViewById(R.id.my_DB_bookName);
            date=itemView.findViewById(R.id.my_DB_date);
            views=itemView.findViewById(R.id.my_DB_views);
            tvD=itemView.findViewById(R.id.my_delete);



        }
    }

}
