package kr.co.song1126.myproject_01;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pkmmte.view.CircularImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClickRecyclerItemActivity extends AppCompatActivity {

    RadioButton radioButton01;
    RadioButton radioButton02;
    RadioButton radioButton03;
    RadioButton radioButton04;

    EditText reportDt;

    Button cancel;
    Button report;

    CircleImageView thumd;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_recycler_item);
        thumd=findViewById(R.id.thumb_view);


    }

    public void clickReport(View view) {
        //신고 다이얼로그

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        AlertDialog alertDialog=builder.create();
        LayoutInflater inflater=(LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.report_dialog, null);

        radioButton01=findViewById(R.id.radio01);
        radioButton02=findViewById(R.id.radio02);
        radioButton03=findViewById(R.id.radio03);
        radioButton04=findViewById(R.id.radio04);
        reportDt=findViewById(R.id.report_edit);
        cancel=findViewById(R.id.cancelReport_dialog);
        report=findViewById(R.id.report_btn);

        builder.setView(v);
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
        //댓글창 엑티비티 열기
    }

    public void clickThumb(View view) {
        //공감버튼누르면 토스트로 알려주고 데이터 베이스에 공감 수 ++시키기 다시 누르면 --
        Toast.makeText(this, "공감하셨습니다.", Toast.LENGTH_SHORT).show();

    }

    public void clickGoRecommend(View view) {
        //추천글의 작성자의 다른 게시글 불러오기
        //없으면 다이얼로그로 알려주기
    }
}
