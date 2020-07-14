package kr.co.song1126.myproject_01;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class ClickRecyclerItemActivity extends AppCompatActivity {

    RadioButton rb;

    EditText reportEt;

    RadioGroup rg;

    CircleImageView thumd;

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
    String bookImgUrl="https://byline.network/wp-content/uploads/2018/05/cat.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //todo 이 액티비티가 시작될때 서버에서 해당하는 추천글의 데이터 받아오기.

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

        //=========todo 이미지를 서버에 올라간 책 이미지로 선택한다. ==> 아래코드 변경
        bookImgUrl=G.loginP.getString("Img","");

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
//                radioButton01=(RadioButton) v.findViewById(R.id.radio01);
//                radioButton02=(RadioButton)v.findViewById(R.id.radio02);
//                radioButton03=(RadioButton)v.findViewById(R.id.radio03);
//                radioButton04=(RadioButton)v.findViewById(R.id.radio04);
                reportEt=(EditText) v.findViewById(R.id.report_edit);
                rg=v.findViewById(R.id.group_btn);

                reportData=reportEt.getText().toString();
                checkedID=rg.getCheckedRadioButtonId();

                rb=rg.findViewById(checkedID);
                buttonText=rb.getText().toString();

                //Toast.makeText(ClickRecyclerItemActivity.this, buttonText+" : "+reportData, Toast.LENGTH_SHORT).show();

                //  buttonText : 라디오 버튼택스트, reportData : 직접작성한 신고내용
                //  todo  관리자 서버로 보내기

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
        //공감버튼누르면 토스트로 알려주고 데이터 베이스에 공감 수 ++시키기 다시 누르면 --



        if (checkedThumd==0){
            Toast.makeText(this, "공감하셨습니다.", Toast.LENGTH_SHORT).show();
            Glide.with(this).load(R.drawable.ic_thumb_up_red_24dp).into(thumd);
            checkedThumd++;
        }else if (checkedThumd==1){
            Toast.makeText(this, "공감을 취소 하셨습니다.", Toast.LENGTH_SHORT).show();
            Glide.with(this).load(R.drawable.ic_thumb_up_24dp).into(thumd);
            checkedThumd--;
        }

    }

    public void clickGoRecommend(View view) {
        //추천글의 작성자의 다른 게시글 불러오기
        //없으면 다이얼로그로 알려주기
    }

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
