package kr.co.song1126.myproject_01;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class MyRecommendView_retouch_Activity extends AppCompatActivity {

    Spinner spinner;
    EditText title;
    EditText msg;
    EditText bookName;
    EditText authorname;
    TextView tvNum;

    ImageView bookImg;

    String imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recommend_view_retouch_);

        spinner=findViewById(R.id.edit_spinner_retouch);
        title=findViewById(R.id.edit_title_retouch);
        msg=findViewById(R.id.edit_msg_retouch);
        bookName=findViewById(R.id.edit_bookName_retouch);
        authorname=findViewById(R.id.edit_Authorname_retouch);
        tvNum=findViewById(R.id.textNum_retouch);
        bookImg=findViewById(R.id.bookImg_retouch);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.spinnerItem, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


    }

    public void clickUpButton(View view) {
        //수정하는 페이지 종료 다이얼로그 만들기
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("게시글 수정을 종료하시겠습니까?");
        builder.setMessage("수정된 부분이 저장되지 않습니다.");
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //서버로 데이터들 보내기
                dialog.cancel();
                finish();
            }
        });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }

    public void clickAdd(View view) {
        //수정한 데이터 서버로 보내기
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("수정된 게시글을 저장하시겠습니까?");
        builder.setMessage("수정된 데이터로 게시됩니다.");
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //서버로 데이터들 보내기
                dialog.cancel();
                //Toast.makeText(MyRecommendView_retouch_Activity.this, "수정", Toast.LENGTH_SHORT).show();//정상작동
                finish();
            }
        });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    public void setBookImg(View view) {
        //갤러리열기
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //갤러리에서 받아온 사진 열기
        switch (requestCode){
            case 200:
                if (resultCode != RESULT_CANCELED) {
                    Uri uri=data.getData();
                    if (uri==null) Toast.makeText(MyRecommendView_retouch_Activity.this, "null", Toast.LENGTH_SHORT );
                    Picasso.get().load(uri).into(bookImg);
                    Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();
                    //사진 보여주고 서버로 보내기

                }
                break;
        }

    }
}
