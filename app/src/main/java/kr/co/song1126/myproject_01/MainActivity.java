package kr.co.song1126.myproject_01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.exception.KakaoException;
import com.pkmmte.view.CircularImageView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.kakao.util.helper.Utility.getPackageInfo;

public class MainActivity extends AppCompatActivity {


    CircularImageView iv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv=findViewById(R.id.iv_company);
        String keyHash=getKeyHash(this);
        Log.d("TAG", keyHash);

        Session.getCurrentSession().addCallback(sessionCallback);

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
        super.onDestroy();
        Session.getCurrentSession().removeCallback(sessionCallback);
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

                Profile profile=userAccount.getProfile();
                if (profile==null) return;
                String userName=profile.getNickname();
                String imgUrl=profile.getProfileImageUrl();

                //사용자 아이디와 프로필 사진의주소 값을 맴버로 저장만 함.
                //Glide.with(MainActivity.this).load(imgUrl).into(iv);

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



}
