package kr.co.song1126.myproject_01;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.exception.KakaoException;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.kakao.util.helper.Utility.getPackageInfo;

public class MainActivity extends AppCompatActivity {


    CircularImageView iv;
    GoogleSignInClient mGoogleSignInClient;
    String userName;
    String email;
    String imgUrl;

    public static int RC_SIGN_IN=9001;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv=findViewById(R.id.iv_company);
        iv.setImageResource(R.mipmap.book_ic_asset);
//        Glide.with(this).load(R.drawable.series).into(iv);
//        Picasso.get().load(R.drawable.series).into(iv);
        String keyHash=getKeyHash(this);
        Log.d("TAG", keyHash);

        Session.getCurrentSession().addCallback(sessionCallback);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        findViewById(R.id.googlLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singIn();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        //시작할때 기본갑
        G.loginP=getSharedPreferences("Login", MODE_PRIVATE);
        G.loginP.getString("Name", "");
        G.loginP.getString("Email", "");
        G.loginP.getString("Img", "");
        G.kakaoLoginIn=G.loginP.getBoolean("kakaoLoginBoolean", false);

        //구글 로그인 기본갑
        G.googleLoginIn=G.loginP.getBoolean("googleLoginBoolean", false);


        //if문으로 로그인 여부 확인
        if (G.kakaoLoginIn || G.googleLoginIn) {
            startActivity(new Intent(this, MainPageActivity.class));
            finish();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try {
            GoogleSignInAccount account=completedTask.getResult(ApiException.class);
            updaateUI(account);

        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    void updaateUI(GoogleSignInAccount account){
        //구글 로그인
        if (account!=null){
            SharedPreferences spf=getSharedPreferences("Login", MODE_PRIVATE);
            G.googleLoginIn=true;
            SharedPreferences.Editor editor=spf.edit();

            //값을 account에서 가져온다.
            editor.putString("Name", account.getDisplayName());
            editor.putString("Email", account.getEmail());
            editor.putString("Img", String.valueOf(account.getPhotoUrl()));
            editor.putBoolean("googleLoginBoolean", G.googleLoginIn);
            editor.commit();


            Intent intent=new Intent(MainActivity.this, MainPageActivity.class);
            startActivity(intent);
            finish();
        }
    }

    void singIn(){
        Intent signInIntent=mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }




    ISessionCallback sessionCallback=new ISessionCallback() {
        @Override
        public void onSessionOpened() {
            //사용자의 정보 얻어오는 메소드 실행
            requestUserInfo();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Toast.makeText(MainActivity.this, "로그인 세션 연결 실패", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onDestroy() {
//        Log.e("SDDSGSDGGDS", "에러확인01");
        super.onDestroy();
//        Log.e("SDDSGSDGGDS", "에러확인");
        Session.getCurrentSession().removeCallback(sessionCallback);
        mGoogleSignInClient=null;
    }

    //로그인한 사용자의 정보 받아오기
    void requestUserInfo(){
        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {

            }

            @Override
            public void onSuccess(MeV2Response result) {
                UserAccount userAccount=result.getKakaoAccount();
                if (userAccount==null) return;


                Profile profile=userAccount.getProfile();
                if (profile==null) return;
                imgUrl=profile.getProfileImageUrl();
                userName=profile.getNickname();
                email=userAccount.getEmail();


                SharedPreferences spf=getSharedPreferences("Login",MODE_PRIVATE);

                G.kakaoLoginIn=true;

                //Log.e("TAG", email);

                SharedPreferences.Editor editor=spf.edit();
                editor.putString("Name", userName);
                editor.putString("Email", email);
                editor.putString("Img", imgUrl);
                editor.putBoolean("kakaoLoginBoolean", G.kakaoLoginIn);
                editor.commit();

                G.loginP=spf;
                Intent intent=new Intent(MainActivity.this, MainPageActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }



    //키해시 받기
    public static String getKeyHash(final Context context){
        //                      알트엔터로 따로 임포트함
        PackageInfo packageInfo=getPackageInfo(context, PackageManager.GET_SIGNATURES);
        if (packageInfo==null) return null;

        for (Signature signature : packageInfo.signatures){
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                //      Base64는 android.util로 임포트
                return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
            } catch (NoSuchAlgorithmException e) {
                Log.d("TAG", "Unale to get MessageDigest. signature ="+signature, e);
            }
        }
        return null;
    }


    public void clickNonAccount(View view) {
        Intent intent=new Intent(this, MainPageActivity.class);
        intent.putExtra("kakaoName", userName);
        intent.putExtra("email", email);
        startActivityForResult(intent, 100);
    }

    public void clickBtn(View view) {
        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "로그아웃", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void clickHtml(View view) {
        //http://wjsthd10.dothome.co.kr/MyProject01/MyProject01.html
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://wjsthd10.dothome.co.kr/MyProject01/MyProject01.html"));
        startActivity(intent);
    }
}
