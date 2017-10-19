package com.qiuyixiao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qiuyixiao.adapter.ImgAdapter;
import com.qiuyixiao.bean.ImgBean;
import com.qiuyixiao.myapplication.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class Img extends Fragment {
    ArrayList<ImgBean> list;
    RecyclerView rv5;
    View view;
    Gson gson;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.tupian,null);
        gson=new Gson();
        list = new ArrayList<ImgBean>();
        rv5 = (RecyclerView) view.findViewById(R.id.rv5);
        getData();
        return view;
    }

    public void getData(){
        String url = "http://api.laifudao.com/open/tupian.json";
        RequestParams requestParams = new RequestParams(url);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(!TextUtils.isEmpty(result)){
                    Type type = new TypeToken<ArrayList<ImgBean>>() {
                    }.getType();

                    list = gson.fromJson(result, type);
                    rv5.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rv5.setAdapter(new ImgAdapter(getActivity(),list));
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });


    }
}
