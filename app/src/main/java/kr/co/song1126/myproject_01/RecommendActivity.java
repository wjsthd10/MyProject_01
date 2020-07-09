package kr.co.song1126.myproject_01;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class RecommendActivity extends AppCompatActivity {

    Spinner spinner;
    EditText title;
    EditText msg;
    EditText bookName;
    EditText authorname;
    TextView tvNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        spinner=findViewById(R.id.edit_spinner);
        title=findViewById(R.id.edit_title);
        msg=findViewById(R.id.edit_msg);
        bookName=findViewById(R.id.edit_bookName);
        authorname=findViewById(R.id.edit_Authorname);
        tvNum=findViewById(R.id.textNum);

    }

    public void clickUpButton(View view) {
        //다이얼로그로 물어보고 finish()하기
        finish();
    }

    public void clickAdd(View view) {
        //서버에 입력한 데이터 전송하기.
    }

    public void setBookImg(View view) {
        //todo 0709 : 갤러리랑 연결시키기
    }
}
