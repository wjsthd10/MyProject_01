package kr.co.song1126.myproject_01;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface RetrofitService {

    @Multipart
    @POST("/MyProject01/MyRecommend.php/")
    Call<String> postDataToBoard(@PartMap Map<String, String> dataPart, @Part MultipartBody.Part filePart);


    @Multipart
    @POST("/MyProject01/comment_view.php/")
    Call<String> postDataComment(@PartMap Map<String, String> dataPart);


    @GET("/MyProject01/searchLoad.php/")
    Call<ArrayList<MyRecommendItem>> loadDataMyRecommend();


    @GET("/MyProject01/searchLoad.php/")
    Call<ArrayList<RecyclerViewitems>> loadDataSearch();

    @GET("/MyProject01/loadComment.php/")
    Call<ArrayList<CommentItems>> loadDataFromComment();

    @GET("/MyProject01/kakaoLoadDB02.php/")
    Call<ArrayList<RecyclerViewitems>> loadDataFromBoard2();


    @GET("/MyProject01/munpiaLoadDB.php/")
    Call<ArrayList<RecyclerViewitems>> loadFromMunpia();

    @GET("/MyProject01/seriesLoadDB.php/")
    Call<ArrayList<RecyclerViewitems>> loadFromSeries();

    @GET("/MyProject01/clickItemView.php/")
    Call<ArrayList<MyBookItems>> loadFromclickItemView();


    @GET("/MyProject01/loadEventImg.php/")
    Call<ArrayList<EventViewItems>> loadEventMunpia();





}
