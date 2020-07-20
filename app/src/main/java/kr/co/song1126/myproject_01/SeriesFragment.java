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
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SeriesFragment extends Fragment {

    Spinner spinner;
    Context context;

    RecyclerView recyclerView;
    ArrayList<RecyclerViewitems> items=new ArrayList<>();
    SeriesAdapter seriesAdapter;
    FloatingActionButton actionButton;

    public SeriesFragment() {
    }

    public SeriesFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();

    }//onCreate

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    void loadData(){
        Retrofit retrofit=RetrofitHelper.getInstance2();
        RetrofitService retrofitService=retrofit.create(RetrofitService.class);

        Call<ArrayList<RecyclerViewitems>> call=retrofitService.loadFromSeries();
        call.enqueue(new Callback<ArrayList<RecyclerViewitems>>() {
            @Override
            public void onResponse(Call<ArrayList<RecyclerViewitems>> call, Response<ArrayList<RecyclerViewitems>> response) {
                if (response.isSuccessful()) {
                    ArrayList<RecyclerViewitems> itemDB=response.body();
                    items.clear();
                    seriesAdapter.notifyDataSetChanged();

                    for (int i=0; i<itemDB.size(); i++){
                        items.add(new RecyclerViewitems(
                                itemDB.get(i).num,
                                itemDB.get(i).title,
                                itemDB.get(i).imgUrl,
                                itemDB.get(i).kategorie,
                                itemDB.get(i).bookName,
                                itemDB.get(i).date,
                                itemDB.get(i).views));
                        seriesAdapter.notifyItemInserted(i);
                    }

                }
            }

            @Override
            public void onFailure(Call<ArrayList<RecyclerViewitems>> call, Throwable t) {
                Log.w("SHOW2",t.getMessage());
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.series_layout,container, false);
        recyclerView=view.findViewById(R.id.series_recycler);
        seriesAdapter=new SeriesAdapter(context, items);
        actionButton=view.findViewById(R.id.seriesAdd);


        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, RecommendActivity.class);
                startActivity(intent);
            }
        });


        spinner=view.findViewById(R.id.spinner03);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this.getActivity(), R.array.spinnerItem, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.getScrollBarSize();
        spinner.setAdapter(adapter);


        recyclerView.setAdapter(seriesAdapter);

        return view;
    }

}
