package kr.co.song1126.myproject_01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<CommentItems> items=new ArrayList<>();
    CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);


    }

    public void clickUpButton(View view) {
        finish();
    }
}
