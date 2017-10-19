package com.qiuyixiao.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qiuyixiao.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class News extends Fragment {
    private TabLayout tablayout;
    private ViewPager viewpager;
    private String[] title;
    List<Fragment> list =new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.news, null);
        tablayout = view.findViewById(R.id.tablayout);
        viewpager =  view.findViewById(R.id.viewpager);
        viewpager.setOffscreenPageLimit(3);
        title = new String[]{"头条","NBA","汽车","笑话"};
        list.add(new NewsFragment());
        list.add(new NBAFragment());
        list.add(new CarFragment());
        list.add(new JokeFragment());

        viewpager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                return  list.get(position);
            }
            @Override
            public int getCount() {
                return title.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return title[position];
            }
        });
        tablayout.setupWithViewPager(viewpager);
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //            tab被选中
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("sss","onTabSelected::"+tab.getText());
            }
            //          tab取消选中
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d("sss","onTabUnselected"+tab.getText());
            }
            //          tab被重复选中
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d("sss","onTabReselected::"+tab.getText());
            }
        });
        return view;
    }
}
