package com.qiuyixiao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.gson.Gson;
import com.qiuyixiao.adapter.NBAAdapter;
import com.qiuyixiao.bean.NBABean;
import com.qiuyixiao.bean.OnItemClickListener;
import com.qiuyixiao.bean.T1348649145984Bean;
import com.qiuyixiao.myapplication.OnClickActivity;
import com.qiuyixiao.myapplication.R;


import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

public class NBAFragment extends Fragment {
    private SwipeRefreshLayout swipe_refresh;
    private RecyclerView recyclerview;
    private MaterialRefreshLayout refresh;
    private Gson mGson=new Gson();
    private NBAAdapter myAdapter;
    private RecyclerView recyclerview1;
    private int START;
    private int END;
    private ArrayList<T1348649145984Bean> arrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.layout_news, null);
        START=0;
        END=10;
        refresh = view.findViewById(R.id.refresh);
        recyclerview1 = view.findViewById(R.id.recyclerview1);
        swipe_refresh =  view.findViewById(R.id.swipe_refresh);
        recyclerview =  view.findViewById(R.id.recyclerview);
        initMaterialRefresh();//github第三方框架制作下拉刷新 上拉加载
        return view;
    }

    private void initMaterialRefresh() {


        recyclerview1.setLayoutManager(new LinearLayoutManager(getActivity()));
        refresh.setLoadMore(true);//设置支持上拉加载
        refresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                START+=10;
                END+=10;
                initData(START,END);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                START=0;
                END+=10;
                initData(START,END);

            }
        });


        initData(START,END);
    }

    private void initData(int START, final int END) {
        RequestParams requestParams = new RequestParams("http://c.m.163.com/nc/article/list/T1348649145984/"+START+"-"+END+".html");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {

            private ArrayList<T1348649145984Bean> list;
            @Override
            public void onSuccess(String result) {
                if (!TextUtils.isEmpty(result)){
                    NBABean nbaBean = mGson.fromJson(result, NBABean.class);

                    if (nbaBean.getT1348649145984().size()==0){
                        Toast.makeText(getActivity(),"该页面没有数据",Toast.LENGTH_SHORT ).show();
                        return;
                    }
                    if (myAdapter==null){
                        list = nbaBean.getT1348649145984();
                        myAdapter = new NBAAdapter( list,getActivity());
                        recyclerview1.setAdapter(myAdapter);
                    }else {
                        list = nbaBean.getT1348649145984();
                        myAdapter.refreshData(list);
                    }
                    myAdapter.setOnItemClickListener(new OnItemClickListener(){

                        @Override
                        public void onItemClick(View view, int i) {
                            Intent intent = new Intent(getActivity(), OnClickActivity.class);
                            intent.putExtra("imgUrl",list.get(i).getImgsrc());
                            intent.putExtra("body",list.get(i).getUrl());
                            intent.putExtra("title",list.get(i).getTitle());
                            getActivity().startActivity(intent);

                        }
                    });
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d("ssss","result::"+ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                refresh.finishRefresh();
                refresh.finishRefreshLoadMore();
            }
        });
    }
}
