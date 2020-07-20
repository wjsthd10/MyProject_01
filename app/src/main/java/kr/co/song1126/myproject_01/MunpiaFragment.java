package kr.co.song1126.myproject_01;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Field;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MunpiaFragment extends Fragment {

    Context context;
    Spinner spinner;

    RecyclerView recyclerView;
    ArrayList<RecyclerViewitems> items =new ArrayList<>();
    MunpiaAdapter munpiaAdapter;

    public MunpiaFragment(Context context) {
        this.context = context;
    }

    public MunpiaFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //recycler테스트용 item시험
//        items.add(new RecyclerViewitems("테스트화면",G.loginP.getString("Img",""),"text카테고리","책이름","2020.07.15","20,002"));
        loadData();

    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    //item들 정보 받아오기
    void loadData(){
        Retrofit retrofit=RetrofitHelper.getInstance2();
        RetrofitService retrofitService=retrofit.create(RetrofitService.class);

        Call<ArrayList<RecyclerViewitems>> call=retrofitService.loadFromMunpia();
        call.enqueue(new Callback<ArrayList<RecyclerViewitems>>() {
            @Override
            public void onResponse(Call<ArrayList<RecyclerViewitems>> call, Response<ArrayList<RecyclerViewitems>> response) {
                if (response.isSuccessful()) {
                    ArrayList<RecyclerViewitems> itemDB=response.body();
                    items.clear();;
                    munpiaAdapter.notifyDataSetChanged();
                    for (int i=0;i<itemDB.size();i++){
                        items.add(new RecyclerViewitems(
                                itemDB.get(i).num,
                                itemDB.get(i).title,
                                itemDB.get(i).imgUrl,
                                itemDB.get(i).kategorie,
                                itemDB.get(i).bookName,
                                itemDB.get(i).date,
                                itemDB.get(i).views));
                        munpiaAdapter.notifyItemInserted(i);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RecyclerViewitems>> call, Throwable t) {
                Log.d("SHOW", t.getMessage());
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.munpia_layout,container, false);

        munpiaAdapter=new MunpiaAdapter(context,items);
        recyclerView=view.findViewById(R.id.munpia_recycler);
        spinner=view.findViewById(R.id.spinner01);

        FloatingActionButton actionButton=view.findViewById(R.id.munpiaAdd);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MunpiaRecommendActivity.class);
                startActivity(intent);
            }
        });

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this.getActivity(), R.array.spinnerItem, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        recyclerView.setAdapter(munpiaAdapter);

        return view;
    }
}
