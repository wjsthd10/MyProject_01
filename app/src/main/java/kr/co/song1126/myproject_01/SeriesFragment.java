package kr.co.song1126.myproject_01;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SeriesFragment extends Fragment {

    Spinner spinner;
    Context context;


    RecyclerView recyclerView;
    ArrayList<MyBookItems> items=new ArrayList<>();
    SeriesAdapter seriesAdapter;
    FloatingActionButton actionButton;

    public SeriesFragment() {
    }

    public SeriesFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.series_layout,container, false);
        recyclerView=view.findViewById(R.id.series_recycler);
        seriesAdapter=new SeriesAdapter(context, items);
        recyclerView.setAdapter(seriesAdapter);
        actionButton=view.findViewById(R.id.seriesAdd);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, RecommendActivity.class);
                startActivity(intent);
            }
        });


        spinner=view.findViewById(R.id.spinner03);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this.getActivity(), R.array.spinnerItem, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.getScrollBarSize();
        spinner.setAdapter(adapter);


        return view;
    }

}
