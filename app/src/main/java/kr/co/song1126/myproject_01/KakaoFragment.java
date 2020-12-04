package kr.co.song1126.myproject_01;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class KakaoFragment extends Fragment {

    Context context;
    Spinner spinner;

    RecyclerView recyclerView;
    KakaoAdapter kakaoAdapter;
    ArrayList<RecyclerViewitems> items=new ArrayList<>();
    FloatingActionButton actionButton;



    public KakaoFragment() {
    }

    public KakaoFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //recycler테스트용 item시험
//        items.add(new RecyclerViewitems("테스트화면", "http://wjsthd10.dothome.co.kr/html/MyProject01/https://lh3.googleusercontent.com/a-/AOh14GhNtSQJmkzo2PGI21-95jPWSrWLiyCwAvmigi9O","text카테고리","책이름","2020.07.15","20,002"));
        loadData();
    }

    @Override
    public void onResume() {
        super.onResume();

        loadData();
    }

    void loadData(){
        Retrofit retrofit=RetrofitHelper.getInstance2();
        RetrofitService retrofitService=retrofit.create(RetrofitService.class);

        Call<ArrayList<RecyclerViewitems>> call=retrofitService.loadDataFromBoard2();

        call.enqueue(new Callback<ArrayList<RecyclerViewitems>>() {
            @Override
            public void onResponse(Call<ArrayList<RecyclerViewitems>> call, Response<ArrayList<RecyclerViewitems>> response) {
                if (response.isSuccessful()) {
                    ArrayList<RecyclerViewitems> itemDB=response.body();
                    items.clear();
                    kakaoAdapter.notifyDataSetChanged();

                    for (int i=0;i<itemDB.size();i++){
                        items.add(new RecyclerViewitems(
                                itemDB.get(i).num,
                                itemDB.get(i).title,
                                itemDB.get(i).imgUrl,
                                itemDB.get(i).kategorie,
                                itemDB.get(i).bookName,
                                itemDB.get(i).date,
                                itemDB.get(i).views));
                        kakaoAdapter.notifyItemInserted(i);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RecyclerViewitems>> call, Throwable t) {
                Log.d("SHOW",t.getMessage());
            }
        });




    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.kakao_layout,container, false);

        kakaoAdapter=new KakaoAdapter(context, items);
        recyclerView=view.findViewById(R.id.kakao_recycler);
        actionButton=view.findViewById(R.id.kakaoAdd);


        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (G.googleLoginIn){
                    Intent intent=new Intent(context, KakaoRecommendActivity.class);
                    startActivity(intent);
                }else if (G.kakaoLoginIn){
                    Intent intent=new Intent(context, KakaoRecommendActivity.class);
                    startActivity(intent);
                }else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setMessage("로그인 후 사용할 수 있는 기능입니다.");
                    builder.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(context, MainActivity.class);
                            startActivity(intent);
                        }
                    });

                    AlertDialog alertDialog=builder.create();
                    alertDialog.show();
                }
            }
        });


        spinner=view.findViewById(R.id.spinner02);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this.getActivity(), R.array.spinnerItem, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        recyclerView.setAdapter(kakaoAdapter);

        return view;
    }

}
