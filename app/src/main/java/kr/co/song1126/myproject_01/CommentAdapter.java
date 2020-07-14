package kr.co.song1126.myproject_01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter {

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
        vh.comment_reportTV.setText(items.get(position).comment_reportTV);
// todo 0714 여기까지


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
        }
    }

}
