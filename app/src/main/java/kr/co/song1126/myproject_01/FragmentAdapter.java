package kr.co.song1126.myproject_01;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {

    Fragment[] fragments=new Fragment[3];

    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);

        fragments[0]=new MunpiaFragment();
        fragments[1]=new KakaoFragment();
        fragments[2]=new SeriesFragment();

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
