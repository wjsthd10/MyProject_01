package kr.co.song1126.myproject_01;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//todo G에다가 시작할때 정보 넣기, 반복문 돌릴 사이즈 찾기
public class ClickRecyclerItemActivity extends AppCompatActivity {
    CircleImageView otherRecommend;
    RadioButton rb;
    EditText reportEt;
    RadioGroup rg;
    CircleImageView thumd;
    //관리자 서버로 보낼 데이터
    String reportData="";
    String buttonText="";
    int checkedID;
    int checkedThumd=0;//공감버튼 변화
    //======== 서버에서 받아올 데이터 타이틀
    TextView kategory, title, writerID, date, views, favorite, msg;
    //=======서버에서 받아올 도서정보
    TextView bookName, authorName;
    ImageView bookImg;
    ArrayList<MyBookItems> items=new ArrayList<>();
    ArrayList<FavoriteItem> favoriteItems=new ArrayList<>();
    //아이템 클릭시 받아온 넘버값
    String viewNum;
    int favoriteNum;

    ArrayList<String> list=new ArrayList<>();

    static final String SETTING_JSON="setting_json";

    void loadData(){
        Retrofit retrofit=RetrofitHelper.getInstance2();
        RetrofitService retrofitService=retrofit.create(RetrofitService.class);

        Call<ArrayList<MyBookItems>> call=retrofitService.loadFromclickItemView();
        call.enqueue(new Callback<ArrayList<MyBookItems>>() {
            @Override
            public void onResponse(Call<ArrayList<MyBookItems>> call, Response<ArrayList<MyBookItems>> response) {
                if (response.isSuccessful()) {
                    ArrayList<MyBookItems> itemDB=response.body();
                    items.clear();

                    for (int i=0; i<itemDB.size();i++){
                        if (itemDB.get(i).num.equals(viewNum)){
                            items.add(new MyBookItems(
                                    itemDB.get(i).num,
                                    itemDB.get(i).title,
                                    itemDB.get(i).imgUrl,
                                    itemDB.get(i).kategorie,
                                    itemDB.get(i).userName,
                                    itemDB.get(i).bookName,
                                    itemDB.get(i).date,
                                    itemDB.get(i).views,
                                    itemDB.get(i).authorname,
                                    itemDB.get(i).msg,
                                    itemDB.get(i).favorite
                            ));
                        }
                    }
                    //가져온 아에팀 뷰에 넣기
                    favoriteNum=Integer.parseInt(items.get(0).favorite);
                    setView();

                    Log.w("TAGVIE", items.size()+"");

                    otherRecommend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("OTHERRECOMMEND", items.get(0).bookName);
                            Intent intent=new Intent(ClickRecyclerItemActivity.this, OtherRecommendActivity.class);
                            intent.putExtra("bookName",items.get(0).bookName);
                            startActivity(intent);
                        }
                    });

                    thumd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //1. 공감버튼누르면 토스트로 알려주고 데이터 베이스에 공감 수 ++시키기 다시 누르면 --
                            //2. 둘중에 하나라도 누르면 변경 불가능하게 만들기.
                            if (checkedThumd==0){
                                Toast.makeText(ClickRecyclerItemActivity.this, "공감하셨습니다.", Toast.LENGTH_SHORT).show();
                                Glide.with(ClickRecyclerItemActivity.this).load(R.drawable.ic_thumb_up_red_24dp).into(thumd);
                                favoriteNum++;
                                //json에 array저장

                                list.add(items.get(0).num);

                                setStringArrayPref(SETTING_JSON, list);
//                                Log.e("GCLASS", list.size()+"");
                                checkedThumd++;

                            }else if (checkedThumd==1){
                                Toast.makeText(ClickRecyclerItemActivity.this, "공감을 취소 하셨습니다.", Toast.LENGTH_SHORT).show();
                                Glide.with(ClickRecyclerItemActivity.this).load(R.drawable.ic_thumb_up_24dp).into(thumd);
                                favoriteNum--;
                                //json에 저장된 array 에서 지우기
                                for (int i=0;i<G.jsonArray.length();i++){
                                    try {
                                        if (items.get(0).num.equals(G.jsonArray.get(i))){
                                            G.jsonArray.remove(i);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                checkedThumd--;
                            }
                            //ArrayList<item> 속성으로 2개 viewNum, favoriteNum(int)
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MyBookItems>> call, Throwable t) {
                Log.e("ClickItem MyBookItems", t.getMessage());
            }
        });
    }
    void  setStringArrayPref(String key, ArrayList<String> values){
        SharedPreferences prefs=getSharedPreferences(key, MODE_PRIVATE);
        SharedPreferences.Editor editor=prefs.edit();
        for (int i=0; i<values.size(); i++){
            G.jsonArray.put(values.get(i));
        }
        if (!values.isEmpty()){
            editor.putString(key, G.jsonArray.toString());
//            Log.e("asdfsadf", values.size()+"");
//            editor.putInt("Values",values.size());
        }else {
            editor.putString(key, null);
        }
        editor.apply();
    }

    ArrayList<String> getStringArrayPref(String key){//json에서 불러와서 저장하기
        SharedPreferences prefs=getSharedPreferences(key, MODE_PRIVATE);
        String json=prefs.getString(key, null);
        ArrayList<String> urls=new ArrayList<>();
        if (json!=null){
            Log.e("jsonView", json);
            if (json!=null){
                for (int i=0;i<G.val.length;i++){
//                    String url=G.jsonArray.optString(i);
//                    Log.e("JSonciew",url+G.jsonArray);
                    String url=G.val[i];
                    urls.add(url);
                }
            }
        }
        return urls;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        viewNum=intent.getStringExtra("viewNum");
        loadData();
        setContentView(R.layout.activity_click_recycler_item);
        thumd=findViewById(R.id.thumb_view);
        otherRecommend=findViewById(R.id.go_recommend_view);
        //==========
        kategory=findViewById(R.id.tv_recyclerItem_kategorie);
        title=findViewById(R.id.tv_recyclerItem_title);
        writerID=findViewById(R.id.recommendID);
        date=findViewById(R.id.recommendDate);
        views=findViewById(R.id.recommendViews02);
        favorite=findViewById(R.id.recommendFavorite);
        msg=findViewById(R.id.msg_view);
        bookName=findViewById(R.id.bookName_view);
        authorName=findViewById(R.id.authorname_view);
        bookImg=findViewById(R.id.bookimg_view);
    }

    void setView(){
        ArrayList<String> list=getStringArrayPref(SETTING_JSON);
        if (list!=null){
            for (int i=0;i<list.size();i++){
//                ArrayList<String> value=new ArrayList<>();
//                value.set(i,list.get(i));
//                Log.e("TAG", value.get(i)+"");
                try {
                    if (items.get(0).num.equals(G.jsonArray.get(i))){
                        Glide.with(this).load(R.drawable.ic_thumb_up_red_24dp).into(thumd);
                        checkedThumd=1;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        kategory.setText(items.get(0).kategorie);
        title.setText(items.get(0).title);
        writerID.setText(items.get(0).userName);
        date.setText(items.get(0).date);
        views.setText(items.get(0).views);
        authorName.setText(items.get(0).authorname);
        msg.setText(items.get(0).msg);
        bookName.setText(items.get(0).bookName);
        favorite.setText(items.get(0).favorite);
        Glide.with(this).load("http://wjsthd10.dothome.co.kr/MyProject01/"+items.get(0).imgUrl).into(bookImg);
        bookImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이미지 확대 됌.  ===> Glide 안됌.....로딩에 시간이좀 걸림...로딩표시해야함
                Log.e("DDDD", "DDDDD");
                AlertDialog.Builder builder=new AlertDialog.Builder(ClickRecyclerItemActivity.this);
                LayoutInflater inflater=LayoutInflater.from(ClickRecyclerItemActivity.this);
                View view1=inflater.inflate(R.layout.dialog_imageview, null);
                ImageView imageView=view1.findViewById(R.id.dialogImg);
                Log.e("IMGSET", "http://wjsthd10.dothome.co.kr/MyProject01/"+items.get(0).imgUrl);
                Picasso.get().load("http://wjsthd10.dothome.co.kr/MyProject01/"+items.get(0).imgUrl).into(imageView);
                //setView는 builder에게... Dialog가 만들어지기 전에
                builder.setView(view1);
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });
    }

    public void clickReport(View view) {
        //신고 다이얼로그
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        LayoutInflater inflater=(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.report_dialog, null);
        builder.setView(v);
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //취소버튼
                dialog.cancel();
            }
        });

        builder.setPositiveButton("신고하기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                //신고버튼
                reportEt=(EditText) v.findViewById(R.id.report_edit);
                rg=v.findViewById(R.id.group_btn);
                reportData=reportEt.getText().toString();
                checkedID=rg.getCheckedRadioButtonId();
                rb=rg.findViewById(checkedID);
                buttonText=rb.getText().toString();
                reportDataUpload();
                Toast.makeText(ClickRecyclerItemActivity.this, "신고내용이 접수되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    void reportDataUpload(){
        //서버작업
        Retrofit retrofit=RetrofitHelper.getInstance();
        RetrofitService retrofitService=retrofit.create(RetrofitService.class);
        Map<String, String> dataPart=new HashMap<>();
        dataPart.put("reportType", "Recommend");
        dataPart.put("reportMsg", reportData);
        dataPart.put("userName", G.loginP.getString("Name", ""));
        dataPart.put("reportNum", viewNum);
        dataPart.put("buttonText", buttonText);

        Log.e("DATAPARTLOG", dataPart.toString());

        Call<String> call=retrofitService.reportDataUpload(dataPart);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String s=response.body();
                    Log.e("MAPDATAPART", s);
                    Log.e("MAPDATAPART", response.toString());
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("MAPDATAPART", t.getMessage());
            }
        });
    }
    public void clickUpButton(View view) {finish();}
    public void clickMain(View view) {
        //메인페이지 열기
        Intent intent=new Intent(this, MainPageActivity.class);
        startActivity(intent);
        finish();
    }

    public void clickDet(View view) {
        Intent intent=new Intent(this, CommentActivity.class);
        //데이터 추가로 보내야함
        intent.putExtra("viewNum", viewNum);
        startActivity(intent);
    }

}
