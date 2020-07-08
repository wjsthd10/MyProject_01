package kr.co.song1126.myproject_01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toolbar;

public class MainPageActivity extends AppCompatActivity {

    LinearLayout layout;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        layout=findViewById(R.id.mainpage);
        pager=findViewById(R.id.pager);



    }

    public void clickMyPage(View view) {
    }

    public void clickSearch(View view) {
    }
}
