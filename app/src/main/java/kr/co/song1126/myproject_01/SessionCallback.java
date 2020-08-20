package kr.co.song1126.myproject_01;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.kakao.auth.ISessionCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.OptionalBoolean;
import com.kakao.util.exception.KakaoException;

import static android.content.Context.MODE_PRIVATE;

public class SessionCallback implements ISessionCallback {

    Context context;

    public SessionCallback(Context context) {
        this.context = context;
    }

    @Override
    public void onSessionOpened() {
        requestMe();
    }

    @Override
    public void onSessionOpenFailed(KakaoException exception) {
        Log.e("SessionCallback::", "onSessionOpenFailed:"+exception.getMessage());
    }

    public void requestMe(){
        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e("kakao_api","세션이 닫혀 있음:"+errorResult);
            }

            @Override
            public void onSuccess(MeV2Response result) {
                Log.i("KAKAO_API", "사용자 아이디:"+result.getId());
                SharedPreferences spf=context.getSharedPreferences("Login",MODE_PRIVATE);

                UserAccount kakoAccount=result.getKakaoAccount();
                if (kakoAccount!=null){
//                    이메일
                    String email=kakoAccount.getEmail();
                    if (email !=null){
                        Log.i("kakao_api", "email: "+email);
                    }else if(kakoAccount.emailNeedsAgreement()== OptionalBoolean.TRUE){
//                        동의 요청 후 이메일 획득 기능
//                        단, 선택 동의로 설정되어 있다면 서비스 이용 시나리오 상에서 반드시 필요한 경우에만
                    }else {
//                        이메일 획득 불가
                    }

                    Profile profile=kakoAccount.getProfile();
                    if (profile!=null){
                        Log.d("kakao_api", "nickname: "+profile.getNickname());
                        Log.d("kakao_api", "profile image: "+profile.getProfileImageUrl());
                        Log.d("kakao_api", "thumbnail image: "+profile.getThumbnailImageUrl());
                    }else if (kakoAccount.profileNeedsAgreement()==OptionalBoolean.TRUE){
//                        동의 요청 후 프로필 정보 획득 가능
                    }else {}
                }
            }
        });
    }
}
