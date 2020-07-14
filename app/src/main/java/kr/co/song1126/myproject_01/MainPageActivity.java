package kr.co.song1126.myproject_01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainPageActivity extends AppCompatActivity {

    Toolbar toolbar;
    LinearLayout layout;            //이벤트 페이지
    LinearLayout fragmentlayout;    //프레그먼트 보여질 뷰
    TextView title;                 //타이틀바
    FragmentManager fragmentManager=getSupportFragmentManager();
    Fragment[] fragments=new Fragment[3];   //프레그먼트 배열
    ImageView titleIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        fragments[0]=new MunpiaFragment(this);
        fragments[1]=new KakaoFragment(this);
        fragments[2]=new SeriesFragment(this);


        //find
        toolbar=findViewById(R.id.toolbar);
        title=findViewById(R.id.title);
        layout=findViewById(R.id.mainpage);
        fragmentlayout=findViewById(R.id.lay);
        titleIV=findViewById(R.id.ic_mypage);



        //바텀네비게이션
        BottomNavigationView bottomNav=findViewById(R.id.bottomNav);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                FragmentTransaction transaction=fragmentManager.beginTransaction();

                switch (menuItem.getItemId()){
                    case R.id.moon:
                        layout.setVisibility(View.GONE);
                        fragmentlayout.setVisibility(View.VISIBLE);
                        title.setText("문 피 아");
                        Glide.with(MainPageActivity.this).load(R.drawable.ic_home_black_24dp).into(titleIV);
                        toolbar.setBackgroundColor(0xFF033CDA);//FFFBDB08//FF06ED2C
                        titleIV.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(MainPageActivity.this, MainPageActivity.class);
                                startActivity(intent);
                            }
                        });
                        transaction.replace(R.id.lay, fragments[0]);
                        break;


                    case R.id.kakao:
                        layout.setVisibility(View.GONE);
                        fragmentlayout.setVisibility(View.VISIBLE);
                        title.setText("카카오 페이지");
                        toolbar.setBackgroundColor(0xFFFBDB08);//FFFBDB08//FF06ED2C
                        transaction.replace(R.id.lay, fragments[1]);
                        Glide.with(MainPageActivity.this).load(R.drawable.ic_home_black_24dp).into(titleIV);
                        titleIV.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(MainPageActivity.this, MainPageActivity.class);
                                startActivity(intent);
                            }
                        });
                        break;

                    case R.id.series:
                        layout.setVisibility(View.GONE);
                        fragmentlayout.setVisibility(View.VISIBLE);
                        title.setText("시 리 즈");
                        toolbar.setBackgroundColor(0xFF06ED2C);//FFFBDB08//FF06ED2C
                        Glide.with(MainPageActivity.this).load(R.drawable.ic_home_black_24dp).into(titleIV);
                        titleIV.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(MainPageActivity.this, MainPageActivity.class);
                                startActivity(intent);
                            }
                        });
                        transaction.replace(R.id.lay, fragments[2]);
                        break;
                }
                transaction.commit();
                return true;
            }
        });



    }


    @Override
    protected void onResume() {
        super.onResume();
        if (G.googleLoginIn) {
            title.setText(G.loginP.getString("Name", ""));
        }else if (G.kakaoLoginIn){
            title.setText(G.loginP.getString("Name", ""));
        }else title.setText("익명");
    }

    public void clickMyPage(View view) {
        Intent intent=new Intent(this, MyPageActivity.class);
        startActivity(intent);

    }

    public void clickSearch(View view) {
        //todo 검색창 엑티비티 만들기
        //아래는 다른 액티비티 열어놨음
        Intent intent=new Intent(this, ClickRecyclerItemActivity.class);
        startActivity(intent);

    }


}
