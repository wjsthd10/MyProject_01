package kr.co.song1126.myproject_01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Multipart;

public class SearchActivity extends AppCompatActivity {

    AppCompatSpinner spinner;
    String spinnerTag;

    EditText searchEt;
    String searchText;

    RecyclerView recyclerView;
    ArrayList<RecyclerViewitems> items=new ArrayList<>();
    SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        spinner=findViewById(R.id.spinner_serch);
        searchEt=findViewById(R.id.serch_edit);
        recyclerView=findViewById(R.id.recycler_search);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.spinnerItem, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        searchText=searchEt.getText().toString();

        searchAdapter=new SearchAdapter(this, items);
        recyclerView.setAdapter(searchAdapter);

    }

    public void clickSearch(View view) {
        loadData();

    }

    void loadData(){
        Retrofit retrofit=RetrofitHelper.getInstance2();
        RetrofitService retrofitService=retrofit.create(RetrofitService.class);
        Call<ArrayList<RecyclerViewitems>> call=retrofitService.loadDataSearch();
        call.enqueue(new Callback<ArrayList<RecyclerViewitems>>() {
            @Override
            public void onResponse(Call<ArrayList<RecyclerViewitems>> call, Response<ArrayList<RecyclerViewitems>> response) {
                if (response.isSuccessful()) {
                    ArrayList<RecyclerViewitems> itemDB=response.body();
                    items.clear();
                    searchAdapter.notifyDataSetChanged();
                    //String spinnerTag;     String searchText;
                    //&& searchText.equals(itemDB.get(i).bookName) && searchText.equals(itemDB.get(i).title)

                    spinnerTag=(String)spinner.getSelectedItem();
                    Log.d("SPINNERTAG", spinnerTag);//스피너값이 이상하게 들어감 ==>전체분류로들어감
                    Log.d("SPINNERTAG", searchText);//스피너값이 이상하게 들어감 ==>전체분류로들어감
                    for (int i=0;i<itemDB.size();i++){
                        Log.d("KATEGORIE", itemDB.get(i).kategorie);
                        if (spinnerTag.equals(itemDB.get(i).kategorie) || searchText.equals(itemDB.get(i).bookName) || searchText.equals(itemDB.get(i).title) ){
                            items.add(new RecyclerViewitems(
                                    itemDB.get(i).num,
                                    itemDB.get(i).title,
                                    itemDB.get(i).imgUrl,
                                    itemDB.get(i).kategorie,
                                    itemDB.get(i).bookName,
                                    itemDB.get(i).date,
                                    itemDB.get(i).views));
                            searchAdapter.notifyItemInserted(i);
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<ArrayList<RecyclerViewitems>> call, Throwable t) {
                Log.d("SHOW", t.getMessage());
            }
        });
    }

    public void clickUp(View view) {finish();}

}
