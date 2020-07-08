package kr.co.song1126.myproject_01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainPageActivity extends AppCompatActivity {

    Toolbar toolbar;
    LinearLayout layout;
    ViewPager pager;
    FragmentAdapter adapter;
    TextView title;
    FragmentManager fragmentManager=getSupportFragmentManager();
    Fragment[] fragments=new Fragment[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        fragments[0]=new MunpiaFragment();
        fragments[1]=new KakaoFragment();
        fragments[2]=new SeriesFragment();

        toolbar=findViewById(R.id.toolbar);

        title=findViewById(R.id.title);

        layout=findViewById(R.id.mainpage);

        pager=findViewById(R.id.pager);
        adapter=new FragmentAdapter(getSupportFragmentManager());
        BottomNavigationView bottomNav=findViewById(R.id.bottomNav);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                FragmentTransaction transaction=fragmentManager.beginTransaction();

                switch (menuItem.getItemId()){
                    case R.id.moon:
                        layout.setVisibility(View.GONE);
                        pager.setVisibility(View.VISIBLE);
                        title.setText("문 피 아");
                        toolbar.setBackgroundColor(0xFF033CDA);//FFFBDB08//FF06ED2C
                        break;
                    case R.id.kakao:
                        layout.setVisibility(View.GONE);
                        pager.setVisibility(View.VISIBLE);
                        title.setText("카카오 페이지");
                        toolbar.setBackgroundColor(0xFFFBDB08);//FFFBDB08//FF06ED2C
                        break;
                    case R.id.series:
                        layout.setVisibility(View.GONE);
                        pager.setVisibility(View.VISIBLE);
                        title.setText("시 리 즈");
                        toolbar.setBackgroundColor(0xFF06ED2C);//FFFBDB08//FF06ED2C
                        break;
                }

                return true;
            }
        });

        pager.setAdapter(adapter);

    }




    public void clickMyPage(View view) {
    }

    public void clickSearch(View view) {
    }


}
