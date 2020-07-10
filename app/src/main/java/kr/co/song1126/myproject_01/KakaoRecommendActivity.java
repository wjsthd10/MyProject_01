package kr.co.song1126.myproject_01;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class KakaoRecommendActivity extends AppCompatActivity {

    Spinner spinner;
    EditText title;
    EditText msg;
    EditText bookName;
    EditText authorname;
    TextView tvNum;

    final int GALLERY2=30;
    ImageView bookImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_recommend);

        spinner=findViewById(R.id.edit_spinner_kakao);
        title=findViewById(R.id.edit_title_kakao);
        msg=findViewById(R.id.edit_msg_kakao);
        bookName=findViewById(R.id.edit_bookName_kakao);
        authorname=findViewById(R.id.edit_Authorname_kakao);
        tvNum=findViewById(R.id.textNum_kakao);
        bookImg=findViewById(R.id.bookImg_kakao);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.spinnerItem, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

    }

    public void clickUpButton(View view) {
        //다이얼로그로 확인받고 종료하기
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("게시글 작성을 종료하시겠습니까?");

        builder.setNegativeButton("NO",null);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog=builder.create();
        dialog.show();
    }

    public void clickAdd(View view) {
        //서버로 데이터 전달하기
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("게시글을 등록하시겠습니까?");

        builder.setNegativeButton("NO",null);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  서버로 데이터 보내는 코드 작성
            }
        });

        AlertDialog dialog=builder.create();
        dialog.show();
    }

    public void setBookImgKakao(View view) {
        //카카오 게시글의 사진 불러오기
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY2);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case GALLERY2:
                if (resultCode !=RESULT_CANCELED){
                    Uri uri=data.getData();
                    if (uri==null) Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();

                    Glide.with(this).load(uri).into(bookImg);
                }
                break;
        }
    }
}