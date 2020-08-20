package kr.co.song1126.myproject_01;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class G {

    static SharedPreferences loginP=null;
    static boolean kakaoLoginIn=false;
    static boolean googleLoginIn=false;
    static ArrayList<String> favorite;

    static void bookViewsUpdate(String bookViewStr, String viewNum){

        Log.e("BOOKVIEWSTR", bookViewStr);
        Log.e("VIEWNUM", viewNum);

        Retrofit retrofit=RetrofitHelper.getInstance();
        RetrofitService retrofitService=retrofit.create(RetrofitService.class);

        Map<String, String> dataPart=new HashMap<>();
        dataPart.put("views", bookViewStr);
        dataPart.put("num",viewNum);

        Log.e("CALLDATAPART", dataPart.toString());

        Call<String> call=retrofitService.updateViews(dataPart);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String s=response.body();
                    Log.e("BOOKVIEWS", s);
                    Log.e("RESPONSEVIEWS", response.toString());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("RESPONSEFAIL", t.getMessage());
            }
        });
    }

}
