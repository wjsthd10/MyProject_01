package kr.co.song1126.myproject_01;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MunpiaFragment extends Fragment {

    Context context;

    public MunpiaFragment(Context context) {
        this.context = context;
    }

    public MunpiaFragment() {
    }

    Spinner spinner;
    RecyclerView recyclerView;
    ArrayList<MyBookItems> items =new ArrayList<>();
    MunpiaAdapter munpiaAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.munpia_layout,container, false);

        recyclerView=view.findViewById(R.id.munpia_recycler);
        spinner=view.findViewById(R.id.spinner01);

        FloatingActionButton actionButton=view.findViewById(R.id.munpiaAdd);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MunpiaRecommendActivity.class);
                startActivity(intent);
            }
        });

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this.getActivity(), R.array.spinnerItem, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        munpiaAdapter=new MunpiaAdapter(context,items);
        recyclerView.setAdapter(munpiaAdapter);



        return view;
    }
}
