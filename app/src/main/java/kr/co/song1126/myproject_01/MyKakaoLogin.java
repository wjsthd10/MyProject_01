package kr.co.song1126.myproject_01;

import android.app.Application;
import android.content.Context;

import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;

public class MyKakaoLogin extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        KakaoSDK.init(new KakaoAdapter() {
            @Override
            public IApplicationConfig getApplicationConfig() {
                return new IApplicationConfig() {
                    @Override
                    public Context getApplicationContext() {
                        return MyKakaoLogin.this;
                    }
                };
            }
        });
    }
}
