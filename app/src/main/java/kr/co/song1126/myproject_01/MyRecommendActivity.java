package kr.co.song1126.myproject_01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyRecommendActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<MyBookItems> items=new ArrayList<>();
    MyRecommendAdapter adapter;

//    TextView tvD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recommend);
//        tvD=findViewById(R.id.my_delete);
//        tvD.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                //todo 삭제 버튼 누르면 데이터 삭제
//                return false;
//            }
//        });

        recyclerView=findViewById(R.id.my_recommend_recycler);
        adapter=new MyRecommendAdapter(this, items);
        recyclerView.setAdapter(adapter);


    }

    public void clickUpButton(View view) {
        finish();
    }
}
