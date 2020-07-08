package kr.co.song1126.myproject_01;

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

import com.bumptech.glide.Glide;

public class KakaoFragment extends Fragment {

    Spinner spinner;
    ImageView iv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.kakao_layout,container, false);
        iv=view.findViewById(R.id.iv_kakao);

        spinner=view.findViewById(R.id.spinner02);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this.getActivity(), R.array.spinnerItem, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.getScrollBarSize();
        spinner.setAdapter(adapter);

        return view;
    }
}
