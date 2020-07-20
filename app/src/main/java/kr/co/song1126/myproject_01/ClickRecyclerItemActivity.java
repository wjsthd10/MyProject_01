package kr.co.song1126.myproject_01;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ClickRecyclerItemActivity extends AppCompatActivity {

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

    //확대한 사진의 이미지 주소 대입하기
    String bookImgUrl="";

    ArrayList<MyBookItems> items=new ArrayList<>();

    String viewNum;
    int favoriteNum;

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
//                    Log.w("TAGVIE", items.size()+"");
                    //가져온 아에팀 뷰에 넣기
                    favoriteNum=Integer.parseInt(items.get(0).favorite);
                    setView();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MyBookItems>> call, Throwable t) {
                //실패
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent=getIntent();
        viewNum=intent.getStringExtra("viewNum");
//        Toast.makeText(this, viewNum, Toast.LENGTH_SHORT).show();
        //items의 num값과 viewNum값이 같은 items의 값을 출력....

        //todo 이 액티비티가 시작될때 서버에서 해당하는 추천글의 데이터 받아오기.
        loadData();

        setContentView(R.layout.activity_click_recycler_item);
        thumd=findViewById(R.id.thumb_view);

        //==========
        kategory=findViewById(R.id.tv_recyclerItem_kategorie);
        title=findViewById(R.id.tv_recyclerItem_title);
        writerID=findViewById(R.id.recommendID);
        date=findViewById(R.id.recommendDate);
        views=findViewById(R.id.recommendViews02);
        favorite=findViewById(R.id.recommendFavorite);
        msg=findViewById(R.id.msg_view);



        //===========
        bookName=findViewById(R.id.bookName_view);
        authorName=findViewById(R.id.authorname_view);
        bookImg=findViewById(R.id.bookimg_view);

    }

    void setView(){
//        TextView kategory, title, writerID, date, views, favorite, msg;
//        TextView bookName, authorName;
//        ImageView bookImg;
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
        Log.w("TAGVIEW", "http://wjsthd10.dothome.co.kr/MyProject01/"+items.get(0).imgUrl);


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

                //  Toast.makeText(ClickRecyclerItemActivity.this, buttonText+" : "+reportData, Toast.LENGTH_SHORT).show();
                //  buttonText : 라디오 버튼택스트, reportData : 직접작성한 신고내용
                //  todo  관리자 서버로 보내기

                dialog.cancel();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    public void clickUpButton(View view) {
        //뒤로가기
        finish();
    }

    public void clickMain(View view) {
        //메인페이지 열기
        Intent intent=new Intent(this, MainPageActivity.class);
        startActivity(intent);
        finish();
    }

    public void clickDet(View view) {
        //댓글창 엑티비티 열기  ==> finish()하지 않기 댓글을 보고 돌아올 수 있음
        Intent intent=new Intent(this, CommentActivity.class);
        startActivity(intent);
    }

    public void clickThumb(View view) {
        //1. 공감버튼누르면 토스트로 알려주고 데이터 베이스에 공감 수 ++시키기 다시 누르면 --
        //2. 둘중에 하나라도 누르면 변경 불가능하게 만들기.
        if (checkedThumd==0){
            Toast.makeText(this, "공감하셨습니다.", Toast.LENGTH_SHORT).show();
            Glide.with(this).load(R.drawable.ic_thumb_up_red_24dp).into(thumd);
            favoriteNum++;
            checkedThumd++;
        }else if (checkedThumd==1){
            Toast.makeText(this, "공감을 취소 하셨습니다.", Toast.LENGTH_SHORT).show();
            Glide.with(this).load(R.drawable.ic_thumb_up_24dp).into(thumd);
            favoriteNum--;
            checkedThumd--;
        }
        //1. 서버로 보내기.(favoriteNum)==>
        //2. 폰에 저장?

    }

    public void clickGoRecommend(View view) {
        //추천글의 작성자의 다른 게시글 불러오기
        //intent로 엑티비티 열어주기
    }

    //이미지 크게보기
    public void bigImg(View view) {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        LayoutInflater inflater=LayoutInflater.from(this);

        View view1=inflater.inflate(R.layout.dialog_imageview, null);


        ImageView imageView=view1.findViewById(R.id.dialogImg);
        Picasso.get().load(bookImgUrl).into(imageView);
//        Glide.with(this).load(R.drawable.series).into(imageView);

        AlertDialog alertDialog=builder.create();
        alertDialog.setView(view1);
        alertDialog.show();


    }
}
