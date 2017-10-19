package com.qiuyixiao.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.qiuyixiao.adapter.ContentAdapter;
import com.qiuyixiao.bean.NewsBean;
import com.qiuyixiao.test.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


/**
 * Created by lenovo on 2017/10/17.
 */

public class ContentFragment extends Fragment {
    @Nullable
    View v;
    String path;
    Gson gson = new Gson();
    ListView listView;
    ContentAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.list,null);
        listView = v.findViewById(R.id.listview);
        listView.setAdapter(adapter);
        getData(path);
        return v;

    }
    public void getData(String path) {
        RequestParams requestParams = new RequestParams(path);
        requestParams.addQueryStringParameter("type","");
        requestParams.addQueryStringParameter("key","");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                final NewsBean bean = gson.fromJson(result, NewsBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.updataDate(bean);
                    }
                });
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
