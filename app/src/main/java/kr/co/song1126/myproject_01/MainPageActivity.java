package kr.co.song1126.myproject_01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainPageActivity extends AppCompatActivity {

    Toolbar toolbar;
    LinearLayout layout;            //이벤트 페이지
    LinearLayout fragmentlayout;    //프레그먼트 보여질 뷰
    TextView title;                 //타이틀바
    FragmentManager fragmentManager=getSupportFragmentManager();
    Fragment[] fragments=new Fragment[3];   //프레그먼트 배열
    ImageView titleIV;
    ImageView getUrl01, getUrl02, getUrl03;

    String munpiaEventUrl;
    String munpiaEventImgUrl;
    String munpiaEventSite;
    String munpiaEventDate;

    String seriesEventUrl;
    String seriesEventImgUrl;
    String seriesEventSite;
    String seriesEventDate;

    String kakaoEventUrl;
    String kakaoEventImgUrl;
    String kakaoEventSite;
    String kakaoEventDate;

    BottomNavigationView bottomNav;

    @Override
    protected void onResume() {
        super.onResume();
//        if (G.googleLoginIn) {
//            title.setText(G.loginP.getString("Name", ""));
//        }else if (G.kakaoLoginIn){
//            title.setText(G.loginP.getString("Name", ""));
//        }else title.setText("익명");

        switch (bottomNav.getMenu().findItem(bottomNav.getSelectedItemId()).getItemId()){
            case R.id.moon:
                if (fragmentlayout.getVisibility()==View.GONE){
                    title.setText("Main Page");
                }else {
                    title.setText("문 피 아");
                }
                break;
            case R.id.kakao:
                title.setText("카카오 페이지");
                break;
            case R.id.series:
                title.setText("시 리 즈");
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        getUrl01=findViewById(R.id.eventURL01);
        getUrl02=findViewById(R.id.eventURL02);
        getUrl03=findViewById(R.id.eventURL03);

        fragments[0]=new MunpiaFragment(this);
        fragments[1]=new KakaoFragment(this);
        fragments[2]=new SeriesFragment(this);

        //find
        toolbar=findViewById(R.id.toolbar);
        title=findViewById(R.id.title);
        layout=findViewById(R.id.mainpage);
        fragmentlayout=findViewById(R.id.lay);
        titleIV=findViewById(R.id.ic_mypage);

        //서버에서 이벤트 데이터 가져오기
        loadData();//items에 저장됨

        //바텀네비게이션
        bottomNav=findViewById(R.id.bottomNav);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                FragmentTransaction transaction=fragmentManager.beginTransaction();
//                transaction.addToBackStack(null);//프래그먼트는 스택영역에 들어가지 않지만 스택영역에 들어갈 수 있게 만들어주는 코드

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

    }//onCreate;;

    long backKeyPressedTime=0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime+2000){
            backKeyPressedTime=System.currentTimeMillis();
            showToast();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime+2000){
            finish();

        }

    }


    void showToast(){
        Toast.makeText(this, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<EventViewItems> items=new ArrayList<>();

    //loadData
    public void loadData(){
        Retrofit retrofit=RetrofitHelper.getInstance2();
        RetrofitService retrofitService=retrofit.create(RetrofitService.class);

        Call<ArrayList<EventViewItems>> call=retrofitService.loadEventMunpia();
        call.enqueue(new Callback<ArrayList<EventViewItems>>() {
            @Override
            public void onResponse(Call<ArrayList<EventViewItems>> call, Response<ArrayList<EventViewItems>> response) {
                if (response.isSuccessful()) {
                    ArrayList<EventViewItems> itemDB=response.body();
                    items.clear();
                    for (int i=0; i<itemDB.size(); i++){
                        items.add(new EventViewItems(
                                itemDB.get(i).eventUrl,
                                itemDB.get(i).eventImgUrl,
                                itemDB.get(i).eventSite,
                                itemDB.get(i).eventDT));
                    }
                    Log.w("TAG09", items.get(0).eventImgUrl);
                    eventData();
                    setEventView();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<EventViewItems>> call, Throwable t) {
                Log.w("LOADDATA0102", t.getMessage());
            }
        });

    }


    public void clickMyPage(View view) {
        Intent intent=new Intent(this, MyPageActivity.class);
        startActivity(intent);

    }

    public void clickSearch(View view) {
        //todo 검색창 엑티비티 만들기
        //아래는 다른 액티비티 열어놨음

    }




    //문피아 이벤트 페이지 데이터 뽑기
    public void eventData(){
        //eventUrl  eventImgUrl eventSite   eventDT
        Log.w("TAG03", items.size()+"");

        for (int i=0; i<items.size(); i++){

            Log.w("TAG02", "asdfasdf");

            if (items.get(i).eventSite.equals("문피아")){
//                Log.w("TAG08", items.size()+"");
                munpiaEventUrl=items.get(i).eventUrl;
                munpiaEventImgUrl="http://wjsthd10.dothome.co.kr/MyProject01/"+items.get(i).eventImgUrl;
                munpiaEventSite=items.get(i).eventSite;
                munpiaEventDate=items.get(i).eventDT;

//                Log.w("TAG08", munpiaEventImgUrl);
            }else if (items.get(i).eventSite.equals("시리즈")){
                seriesEventUrl=items.get(i).eventUrl;
                seriesEventImgUrl="http://wjsthd10.dothome.co.kr/MyProject01/"+items.get(i).eventImgUrl;
                seriesEventSite=items.get(i).eventSite;
                seriesEventDate=items.get(i).eventDT;


            }else if (items.get(i).eventSite.equals("카카오")){
                kakaoEventUrl=items.get(i).eventUrl;
                kakaoEventImgUrl="http://wjsthd10.dothome.co.kr/MyProject01/"+items.get(i).eventImgUrl;
                kakaoEventSite=items.get(i).eventSite;
                kakaoEventDate=items.get(i).eventDT;

            }
        }


    }

    void setEventView(){
//        Log.w("TAGTAG", munpiaEventImgUrl);
        Glide.with(this).load(munpiaEventImgUrl).into(getUrl01);
        Glide.with(this).load(kakaoEventImgUrl).into(getUrl02);
        Glide.with(this).load(seriesEventImgUrl).into(getUrl03);

    }

    //이미지 클릭시 이벤트 페이지로 이동....http, https만 가능
    public void eventClick01(View view) {
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(munpiaEventUrl));
        startActivity(intent);
    }

    public void eventClick02(View view) {
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(kakaoEventUrl));
        startActivity(intent);
    }

    public void eventClick03(View view) {
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(seriesEventUrl));
        startActivity(intent);
    }
}
