package kr.co.song1126.myproject_01;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MunpiaRecommendActivity extends AppCompatActivity {

    Spinner spinner;
    EditText title;
    EditText msg;
    EditText bookName;
    EditText authorname;
    TextView tvNum;

    final int GALLERY3=40;
    ImageView bookImg;

    String spinnerItem;
    String bookImgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_munpia_recommend);
        spinner=findViewById(R.id.edit_spinner_Munpia);
        title=findViewById(R.id.edit_title_Munpia);
        msg=findViewById(R.id.edit_msg_Munpia);
        bookName=findViewById(R.id.edit_bookName_Munpia);
        authorname=findViewById(R.id.edit_Authorname_Munpia);
        tvNum=findViewById(R.id.textNum_Munpia);
        bookImg=findViewById(R.id.bookImg_Munpia);

        String[] permissions=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(permissions[0])== PackageManager.PERMISSION_DENIED){
                requestPermissions(permissions, 100);
            }
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerItem=(String)spinner.getSelectedItem();
                Toast.makeText(MunpiaRecommendActivity.this, spinnerItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.spinnerItem, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);



    }//onCreate

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this, "사진 전송 불가능", Toast.LENGTH_SHORT).show();
        }
    }

    public void setBookImgMunpia(View view) {
        //갤러리에서 이미지 불러오기
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY3);

    }

    public void clickAdd(View view) {
        //서버에 등록버튼 다이얼로그로 확인하기
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("게시글을 등록하시겠습니까?");

        builder.setNegativeButton("NO",null);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  서버로 데이터 보내는 코드 작성

                String titleDB=title.getText().toString();
                String msgDB=msg.getText().toString();
                String bookNameDB=bookName.getText().toString();
                String authornameDB=authorname.getText().toString();

                Map<String, String> dataPart=new HashMap<>();
                dataPart.put("title", titleDB);
                dataPart.put("msg",msgDB );
                dataPart.put("kategorie", spinnerItem);
                dataPart.put("bookName", bookNameDB);
                dataPart.put("date", "");
                dataPart.put("views", "0");
                dataPart.put("userName", G.loginP.getString("Name",""));
                dataPart.put("userEmail", G.loginP.getString("Email",""));
                dataPart.put("authorname", authornameDB);
                dataPart.put("fragmentName", "munpia");

                Retrofit retrofit=RetrofitHelper.getInstance();
                RetrofitService retrofitService=retrofit.create(RetrofitService.class);

                MultipartBody.Part filePart=null;
                if (bookImgUri !=null){
                    File file=new File(bookImgUri);
                    RequestBody requestBody=RequestBody.create(MediaType.parse("image/*"),file);
                    filePart=MultipartBody.Part.createFormData("img", file.getName(), requestBody);
                }

                Call<String> call=retrofitService.postDataToBoard(dataPart, filePart);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            String s=response.body();
                            Toast.makeText(MunpiaRecommendActivity.this, ""+s, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(MunpiaRecommendActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        AlertDialog dialog=builder.create();
        dialog.show();

    }

    public void clickUpButton(View view) {
        //다이얼로그 finish
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("게시글 작성을 종료하시겠습니까?");

        builder.setNegativeButton("NO",null);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog=builder.create();
        dialog.show();
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
        switch (requestCode){
            case GALLERY3:
                if (resultCode!= RESULT_CANCELED){
                    Uri uri=data.getData();
                    if (uri==null) Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
                    bookImgUri=getRealPathFromUri(uri);
                    Glide.with(this).load(uri).into(bookImg);

                }
                break;
        }

    }
}
