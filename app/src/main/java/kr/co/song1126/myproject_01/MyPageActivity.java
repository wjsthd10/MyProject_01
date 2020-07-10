package kr.co.song1126.myproject_01;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MyPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
    }

    public void clickUpButton(View view) {
        finish();
    }


    public void clickLogout(View view) {

        //로그인 & 로그아웃 일때 구별해서 다이얼로그 작성 switch ()문 사용해보기  ==> 로그인 상태에 따라서 버튼의 text변경


        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("로그아웃 하시겠습니까?");

        builder.setNegativeButton("NO",null);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // todo 서버로 데이터 보내는 코드 작성
            }
        });

        AlertDialog dialog=builder.create();
        dialog.show();
    }

    public void clickMyRecommend(View view) {
        Intent intent=new Intent(this, MyRecommendActivity.class);
        startActivity(intent);
    }

    public void clickMyfavorite(View view) {
    }
}
