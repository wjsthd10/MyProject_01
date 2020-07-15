package kr.co.song1126.myproject_01;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter {

    RadioGroup rg;
    RadioButton rb;
    EditText et;

    int good=0;
    int notGood=0;

    Context context;
    ArrayList<CommentItems> items;

    public CommentAdapter(Context context, ArrayList<CommentItems> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.comment_item, parent, false);
        VH holder=new VH(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh=(VH) holder;
        vh.comment_id.setText(items.get(position).comment_id);
        vh.comment_date.setText(items.get(position).comment_date);
        vh.comment_msg.setText(items.get(position).comment_msg);
//        vh.comment_reportTV.setText(items.get(position).comment_reportTV);
//        vh.comment_notgood_tv.setText(items.get(position).comment_notgood_tv);
//        vh.comment_good_tv.setText(items.get(position).comment_good_tv);




    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView comment_id;
        TextView comment_date;
        TextView comment_msg;
        TextView comment_reportTV;
        ImageView comment_reportIV;
        ImageView comment_notgood;
        TextView comment_notgood_tv;
        ImageView comment_good;
        TextView comment_good_tv;
        TextView comment_line;

        public VH(@NonNull View itemView) {
            super(itemView);
            comment_id=itemView.findViewById(R.id.comment_id);
            comment_date=itemView.findViewById(R.id.comment_date);
            comment_msg=itemView.findViewById(R.id.comment_msg);
            comment_reportTV=itemView.findViewById(R.id.comment_reportTV);
            comment_reportIV=itemView.findViewById(R.id.comment_reportIV);
            comment_notgood=itemView.findViewById(R.id.comment_notgood);
            comment_notgood_tv=itemView.findViewById(R.id.comment_notgood_tv);
            comment_good=itemView.findViewById(R.id.comment_good);
            comment_good_tv=itemView.findViewById(R.id.comment_good_tv);
            comment_line=itemView.findViewById(R.id.comment_line);


            comment_reportIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reportDialog();
                }
            });
            comment_reportTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reportDialog();
                }
            });

            //추천버튼, 반대버튼 숫자 저장==> DB로 보내기
            comment_good.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkgood();
                }
            });
            comment_good_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkgood();
                }
            });
            comment_notgood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkNotgood();
                }
            });
            comment_notgood_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkNotgood();
                }
            });


        }//VH
    }//class VH



    public void checkgood(){
        if (good==0){
            Toast.makeText(context, "추천", Toast.LENGTH_SHORT).show();
            good++;
        }else if (good==1){
            Toast.makeText(context, "추천취소", Toast.LENGTH_SHORT).show();
            good--;
        }
    }

    public void checkNotgood(){
        if (notGood==0){
            Toast.makeText(context, "반대", Toast.LENGTH_SHORT).show();
            notGood++;
        }else if (notGood==1){
            Toast.makeText(context, "반대취소", Toast.LENGTH_SHORT).show();
            notGood--;
        }
    }

    public void reportDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.report_dialog, null);
        builder.setView(view);

        builder.setPositiveButton("신고하기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //다이얼로그 선택하여 신고하기
                et=view.findViewById(R.id.report_edit);
                rg=view.findViewById(R.id.group_btn);

                String reportData=et.getText().toString();
                int reportBtn=rg.getCheckedRadioButtonId();
                rb=rg.findViewById(reportBtn);
                String btnText=rb.getText().toString();
                //todo btnText, reportData, 신고자 아이디, 댓글작성자 아이디를 관리자 DB에 전송
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //취소버튼
                dialog.cancel();
            }
        });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

}
