package kr.co.song1126.myproject_01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<CommentItems> items=new ArrayList<>();
    CommentAdapter commentAdapter;

    EditText addComment;

    String commentDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        addComment=findViewById(R.id.comment_add_et);
        recyclerView=findViewById(R.id.comment_recycler);

        items.add(new CommentItems("작성자",
                "20.07.08",
                "댓글내용"));

        commentAdapter=new CommentAdapter(this, items);
        recyclerView.setAdapter(commentAdapter);


    }

    public void clickUpButton(View view) {
        finish();
    }

    public void clickAdd(View view) {
        //addComment + userName + date DB로 전송
        //addComment==null이면 DB로 전송하지 않음
        if (addComment.getText().toString().equals("")){
            Toast.makeText(this, "댓글을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        commentDB=addComment.getText().toString();//사용자가 작성한 댓글
        String userName=G.loginP.getString("Name","");//사용자 아이디
        addComment.setText("");//글자 지우기

    }
}
