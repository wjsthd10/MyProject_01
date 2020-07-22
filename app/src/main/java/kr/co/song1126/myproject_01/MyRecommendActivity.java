package kr.co.song1126.myproject_01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyRecommendActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<MyRecommendItem> items=new ArrayList<>();
    MyRecommendAdapter adapter;

//    TextView tvD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recommend);
        recyclerView=findViewById(R.id.my_recommend_recycler);
        adapter=new MyRecommendAdapter(this, items);
        loadData();
        recyclerView.setAdapter(adapter);


    }

    void loadData(){
        Retrofit retrofit=RetrofitHelper.getInstance2();
        RetrofitService retrofitService=retrofit.create(RetrofitService.class);
        Call<ArrayList<MyRecommendItem>> call=retrofitService.loadDataMyRecommend();
        call.enqueue(new Callback<ArrayList<MyRecommendItem>>() {
            @Override
            public void onResponse(Call<ArrayList<MyRecommendItem>> call, Response<ArrayList<MyRecommendItem>> response) {
                if (response.isSuccessful()) {
                    ArrayList<MyRecommendItem> itemDB=response.body();
                    items.clear();
                    adapter.notifyDataSetChanged();
                    Log.d("RESPONSETEST01", G.loginP.getString("Name",""));
                    Log.d("RESPONSETEST01", itemDB.get(1).userName);
                    for (int i=0; i<itemDB.size();i++){
                        if (G.loginP.getString("Name","").equals(itemDB.get(i).userName)){
                            items.add(new MyRecommendItem(
                                    itemDB.get(i).num,
                                    itemDB.get(i).title,
                                    itemDB.get(i).imgUrl,
                                    itemDB.get(i).kategorie,
                                    itemDB.get(i).bookName,
                                    itemDB.get(i).date,
                                    itemDB.get(i).views,
                                    itemDB.get(i).userName
                            ));
                            Log.d("RESPONSETEST01",items.get(i).bookName);
                            adapter.notifyItemInserted(i);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MyRecommendItem>> call, Throwable t) {
                Log.d("RESPONSETEST01",t.getMessage());
            }
        });

    }

    public void clickUpButton(View view) {
        finish();
    }
}
