package kr.co.song1126.myproject_01;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CommentActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<CommentItems> items=new ArrayList<>();
    CommentAdapter commentAdapter;

    EditText addComment;

    String commentDB;
    String viewNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        addComment=findViewById(R.id.comment_add_et);
        recyclerView=findViewById(R.id.comment_recycler);

//        items.add(new CommentItems("num","작성자","20.07.08","댓글내용", ""));

        viewNum=getIntent().getStringExtra("viewNum");

        commentAdapter=new CommentAdapter(this, items, viewNum);
        loadData();

        recyclerView.setAdapter(commentAdapter);



    }

    void loadData(){
        Retrofit retrofit=RetrofitHelper.getInstance2();
        RetrofitService retrofitService=retrofit.create(RetrofitService.class);
        Call<ArrayList<CommentItems>> call=retrofitService.loadDataFromComment();
        call.enqueue(new Callback<ArrayList<CommentItems>>() {
            @Override
            public void onResponse(Call<ArrayList<CommentItems>> call, Response<ArrayList<CommentItems>> response) {
                if (response.isSuccessful()){
                    ArrayList<CommentItems> itemDB=response.body();
                    items.clear();
                    commentAdapter.notifyDataSetChanged();


                    for (int i=0; i<itemDB.size();i++){
                        if (viewNum.equals(itemDB.get(i).recommend_num)){
                            items.add(new CommentItems(
                                    itemDB.get(i).recommend_num,
                                    itemDB.get(i).comment_id,
                                    itemDB.get(i).comment_date,
                                    itemDB.get(i).comment_msg,
                                    itemDB.get(i).favor
                            ));
                        }
                        commentAdapter.notifyItemChanged(i);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CommentItems>> call, Throwable t) {
                Log.d("SHOWLOADDATA", t.getMessage());
            }
        });
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
        if (G.googleLoginIn){
            commentDialog();
        }else if (G.kakaoLoginIn){
            commentDialog();
        }else {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("로그인 후 사용할 수 있는 기능입니다.");
            builder.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent=new Intent(CommentActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }
        addComment.setText("");//글자 지우기
    }
    void commentDialog(){
        commentDB=addComment.getText().toString();//사용자가 작성한 댓글
        String userName=G.loginP.getString("Name","");//사용자 아이디
        Map<String, String> dataPart=new HashMap<>();
        dataPart.put("recommend_num",viewNum);
        dataPart.put("comment_id", userName);
        dataPart.put("comment_date","");
        dataPart.put("comment_msg", commentDB);
        dataPart.put("favor","0");
        Retrofit retrofit=RetrofitHelper.getInstance();
        RetrofitService retrofitService=retrofit.create(RetrofitService.class);
        Call<String> call=retrofitService.postDataComment(dataPart);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.w("COMMENTITEM", dataPart.toString());
                    Log.w("COMMENTITEM", response.body());
                    AlertDialog.Builder builder=new AlertDialog.Builder(CommentActivity.this);
                    builder.setMessage("댓글 업로드 성공");
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.w("call",t.getMessage());
            }
        });
    }

}
