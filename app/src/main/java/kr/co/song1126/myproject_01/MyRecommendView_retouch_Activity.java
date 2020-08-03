package kr.co.song1126.myproject_01;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Multipart;

public class MyRecommendView_retouch_Activity extends AppCompatActivity {

    Spinner spinner;
    EditText title;
    EditText msg;
    EditText bookName;
    EditText authorname;
    TextView tvNum;

    ImageView bookImg;

    String bookImgUri;//이미지 주소
    String viewNum;

    String spinnerItem;

    ArrayList<MyBookItems> items=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recommend_view_retouch_);
        viewNum=getIntent().getStringExtra("viewNum");
        spinner=findViewById(R.id.edit_spinner_retouch);
        title=findViewById(R.id.edit_title_retouch);
        msg=findViewById(R.id.edit_msg_retouch);
        bookName=findViewById(R.id.edit_bookName_retouch);
        authorname=findViewById(R.id.edit_Authorname_retouch);
        tvNum=findViewById(R.id.textNum_retouch);
        bookImg=findViewById(R.id.bookImg_retouch);
        loadData();

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.spinnerItem, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerItem=(String)spinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    //loadData();
    void loadData(){
        Retrofit retrofit=RetrofitHelper.getInstance2();
        RetrofitService retrofitService=retrofit.create(RetrofitService.class);
        Call<ArrayList<MyBookItems>> call=retrofitService.loadFromclickItemView();
        call.enqueue(new Callback<ArrayList<MyBookItems>>() {
            @Override
            public void onResponse(Call<ArrayList<MyBookItems>> call, Response<ArrayList<MyBookItems>> response) {
                if (response.isSuccessful()) {
                    ArrayList<MyBookItems> itemDB=response.body();
                    for (int i=0; i<itemDB.size(); i++){
                        if (viewNum.equals(itemDB.get(i).num)){
                            Log.e("LOGTEST01",viewNum);
                            Log.e("LOGTEST01", itemDB.get(i).num);
                            items.add(new MyBookItems(
                                    itemDB.get(i).num,
                                    itemDB.get(i).title,
                                    itemDB.get(i).imgUrl,
                                    itemDB.get(i).kategorie,
                                    itemDB.get(i).userName,
                                    itemDB.get(i).bookName,
                                    itemDB.get(i).date,
                                    itemDB.get(i).favorite,
                                    itemDB.get(i).authorname,
                                    itemDB.get(i).msg,
                                    itemDB.get(i).views
                            ));
                            Log.e("LOGTEST01", itemDB.get(i).num);
                            Log.e("LOGTEST01",items.get(0).bookName);
                        }
//                        Log.e("TAG0102", itemDB.get(i).imgUrl);
                    }
                    bookImgUri=items.get(0).imgUrl;
                    insertView();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MyBookItems>> call, Throwable t) {
                Log.e("TAG0102",t.getMessage());
            }
        });
    }

    void insertView(){

        title.setText(items.get(0).title);
        msg.setText(items.get(0).msg);
        bookName.setText(items.get(0).bookName);
        authorname.setText(items.get(0).authorname);
        for (int i=0; i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equals(items.get(0).kategorie)){
                spinner.setSelection(i);
            }
        }
        Glide.with(this).load("http://wjsthd10.dothome.co.kr/MyProject01/"+items.get(0).imgUrl).into(bookImg);
        tvNum.setText(items.get(0).msg.length()+"");
        Log.e("Bookimguri", bookImgUri);
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
                //Toast.makeText(MyRecommendView_retouch_Activity.this, "수정", Toast.LENGTH_SHORT).show();//정상작동
                pushDB();
                finish();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
    void pushDB(){
        Log.e("VIEWNUM", viewNum);
        Map<String, String> dataPart=new HashMap<>();
        dataPart.put("num", viewNum);
        dataPart.put("title", title.getText().toString());
        dataPart.put("msg", msg.getText().toString());
        dataPart.put("kategorie",spinnerItem);
        dataPart.put("bookName", bookName.getText().toString());
        dataPart.put("date","");
        dataPart.put("views","0");
        dataPart.put("userName",G.loginP.getString("Name",""));
        dataPart.put("userEmail",G.loginP.getString("Email",""));
        dataPart.put("authorname", authorname.getText().toString());
        dataPart.put("fragmentName","series");
        MultipartBody.Part filePart=null;
        Log.e("BookimguriPush", bookImgUri);
        if (bookImgUri !=null){
            File file=new File(bookImgUri);
            RequestBody requestBody=RequestBody.create(MediaType.parse("image/*"),file);
            filePart=MultipartBody.Part.createFormData("img", file.getName(),requestBody);
        }else {
            filePart=MultipartBody.Part.createFormData("img", items.get(0).imgUrl);
        }

        Retrofit retrofit=RetrofitHelper.getInstance();
        RetrofitService retrofitService=retrofit.create(RetrofitService.class);
        Call<String> call=retrofitService.retouchUpload(dataPart, filePart);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String s=response.body();
                    Log.e("RESPONSEOK", s);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("RESPONSEFAIL", t.getMessage());
            }
        });
    }

    public void setBookImg(View view) {
        //갤러리열기
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 200);
    }

    //todo 이미지 절대경로 저장
    String getRealPathFromUri(Uri uri){
        String[] proj= {MediaStore.Images.Media.DATA};
        CursorLoader loader= new CursorLoader(this, uri, proj, null, null, null);
        Cursor cursor= loader.loadInBackground();
        int column_index= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result= cursor.getString(column_index);
        cursor.close();
        return  result;
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
                    bookImgUri=getRealPathFromUri(uri);
                }
                break;
        }

    }
}
