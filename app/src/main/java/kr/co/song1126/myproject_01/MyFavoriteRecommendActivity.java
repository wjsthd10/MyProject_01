package kr.co.song1126.myproject_01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyFavoriteRecommendActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<RecyclerViewitems> items=new ArrayList<>();
    MyFavoriteAdapter favoriteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorite_recommend);
        loadData();
        recyclerView=findViewById(R.id.my_favorite_recycler);
        favoriteAdapter=new MyFavoriteAdapter(this, items);
        recyclerView.setAdapter(favoriteAdapter);




    }

    void loadData(){
        Retrofit retrofit=RetrofitHelper.getInstance2();
        RetrofitService retrofitService=retrofit.create(RetrofitService.class);
        Call<ArrayList<RecyclerViewitems>> call=retrofitService.loadDataSearch();
        call.enqueue(new Callback<ArrayList<RecyclerViewitems>>() {
            @Override
            public void onResponse(Call<ArrayList<RecyclerViewitems>> call, Response<ArrayList<RecyclerViewitems>> response) {
                if (response.isSuccessful()){
                    ArrayList<RecyclerViewitems> itemDB=response.body();
                    items.clear();
                    favoriteAdapter.notifyDataSetChanged();
                    for (int i=0;i<itemDB.size();i++){
                        items.add(new RecyclerViewitems(
                                itemDB.get(i).num,
                                itemDB.get(i).title,
                                itemDB.get(i).imgUrl,
                                itemDB.get(i).kategorie,
                                itemDB.get(i).bookName,
                                itemDB.get(i).date,
                                itemDB.get(i).views
                        ));
                        favoriteAdapter.notifyItemInserted(i);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RecyclerViewitems>> call, Throwable t) {
                Log.d("FAVORITE", t.getMessage());
            }
        });
    }

    public void clickUpButton(View view) {
        finish();
    }
}
