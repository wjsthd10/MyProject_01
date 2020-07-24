package kr.co.song1126.myproject_01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.util.exception.KakaoException;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

public class MyPageActivity extends AppCompatActivity {

    Button log;
    TextView pro;
    GoogleSignInClient mGoogleSignInClient;
    CircularImageView ivPro;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        pro=findViewById(R.id.tv_pro);
        ivPro=findViewById(R.id.iv_pro);
        relativeLayout=findViewById(R.id.id_myPage);



        log=findViewById(R.id.addButton_Munpia);

        //왜 글라이드만 안돼
//        Glide.with(this).load(R.drawable.moon).into(ivPro);


        if (G.kakaoLoginIn){
            //버튼 이름을 로그아웃으로
            log.setText("로그아웃");
            pro.setText(G.loginP.getString("Name",""));
            Picasso.get().load(G.loginP.getString("Img", "")).into(ivPro);
        }else if (G.googleLoginIn){
            log.setText("로그아웃");
            pro.setText(G.loginP.getString("Name",""));
            Picasso.get().load(G.loginP.getString("Img", "")).into(ivPro);
//            relativeLayout.setVisibility(View.INVISIBLE);
        }else {
            //버튼 이름 로그인 온클릭으로 가서 나머지
            log.setText("로그인");
            relativeLayout.setVisibility(View.INVISIBLE);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    ISessionCallback sessionCallback=new ISessionCallback() {
        @Override
        public void onSessionOpened() {
            //사용자의 정보 얻어오는 메소드 실행
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Toast.makeText(MyPageActivity.this, "로그인 세션 연결 실패", Toast.LENGTH_SHORT).show();
        }
    };//카카오 세션

    public void clickUpButton(View view) {
        finish();
    }


    public void clickLogout(View view) {

        Session.getCurrentSession().addCallback(sessionCallback);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);//구글 세션


        //로그인 & 로그아웃 일때 구별해서 다이얼로그 작성 switch ()문 사용해보기  ==> 로그인 상태에 따라서 버튼의 text변경

        if (G.kakaoLoginIn){

            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("로그아웃 하시겠습니까?");

            builder.setNegativeButton("NO",null);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                        @Override
                        public void onCompleteLogout() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MyPageActivity.this, "로그아웃 하셨습니다.", Toast.LENGTH_SHORT).show();
                                    G.kakaoLoginIn=false;
                                    getSharedPreferences("Login",MODE_PRIVATE).edit().putBoolean("kakaoLoginBoolean", G.kakaoLoginIn).commit();
                                    log.setText("로그인");
                                    relativeLayout.setVisibility(View.INVISIBLE);
                                    Session.getCurrentSession().removeCallback(sessionCallback);
                                }
                            });
                        }
                    });
                }
            });

            AlertDialog dialog=builder.create();
            dialog.show();
        }else if (G.googleLoginIn){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("로그아웃 하시겠습니까?");

            builder.setNegativeButton("NO",null);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            G.googleLoginIn=false;
                            Toast.makeText(MyPageActivity.this, "로그아웃 하셨습니다.", Toast.LENGTH_SHORT).show();
                            getSharedPreferences("Login",MODE_PRIVATE).edit().putBoolean("googleLoginBoolean", G.googleLoginIn).commit();
                            log.setText("로그인");
                            relativeLayout.setVisibility(View.INVISIBLE);
                            mGoogleSignInClient=null;
                        }
                    });
                }
            });

            AlertDialog dialog=builder.create();
            dialog.show();

        }else {
            Intent intent=new Intent(MyPageActivity.this, MainActivity.class);
            Session.getCurrentSession().removeCallback(sessionCallback);
            mGoogleSignInClient=null;
            startActivity(intent);
        }

    }//로그인 로그아웃 버튼


    public void clickMyRecommend(View view) {
        
        if (G.googleLoginIn){
            Intent intent=new Intent(this, MyRecommendActivity.class);
            startActivity(intent);
        }else if (G.kakaoLoginIn){
            Intent intent=new Intent(this, MyRecommendActivity.class);
            startActivity(intent);
        }else Toast.makeText(this, "로그인 후 사용할 수 있습니다.", Toast.LENGTH_LONG).show();
        
    }//사용자가 작성한 추천글 리스트

    public void clickMyfavorite(View view) {
        Intent intent=new Intent(this, MyFavoriteRecommendActivity.class);
        startActivity(intent);
    }//사용자가 공감한 추천글 리스트


}
