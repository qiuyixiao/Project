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
import com.qiuyixiao.adapter.NewsAdapter;
import com.qiuyixiao.bean.HeadLineBean;
import com.qiuyixiao.bean.OnItemClickListener;
import com.qiuyixiao.bean.T1348647909107Bean;
import com.qiuyixiao.myapplication.OnClickActivity;
import com.qiuyixiao.myapplication.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

public class NewsFragment extends Fragment {

    private SwipeRefreshLayout swipe_refresh;
    private RecyclerView recyclerview;
    private NewsAdapter myAdapter;
    private MaterialRefreshLayout refresh;
    private RecyclerView recyclerview1;
    private Gson mGson=new Gson();
    private int START;
    private int END;

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

    public void initData(int START, final int END){
        Log.d("sss","START::"+START+"END::"+END);
        RequestParams requestParams = new RequestParams("http://c.m.163.com/nc/article/headline/T1348647909107/"+START+"-"+END+".html");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            private ArrayList<T1348647909107Bean> t1348647909107;
            @Override
            public void onSuccess(String result) {
                Log.d("sss","result::"+result);
                if (!TextUtils.isEmpty(result)){
                    HeadLineBean headLineBean = mGson.fromJson(result, HeadLineBean.class);

                    if (headLineBean.getT1348647909107().size()==0){
                        Toast.makeText(getActivity(),"该页面没有数据",Toast.LENGTH_SHORT ).show();
                        return;
                    }

                    if (myAdapter==null){
                        t1348647909107 = headLineBean.getT1348647909107();
                        myAdapter = new NewsAdapter( t1348647909107,getActivity());
                        recyclerview1.setAdapter(myAdapter);
                    }else {
                        t1348647909107 = headLineBean.getT1348647909107();
                        myAdapter.refreshData(t1348647909107);
                    }

                    myAdapter.setOnItemClickListener(new OnItemClickListener() {


                        @Override
                        public void onItemClick(View view, int i) {
                            Intent intent = new Intent(getActivity(), OnClickActivity.class);
                            intent.putExtra("imgUrl",t1348647909107.get(i).getImgsrc());
                            intent.putExtra("body",t1348647909107.get(i).getUrl());
                            intent.putExtra("title",t1348647909107.get(i).getTitle());
                            startActivity(intent);
                        }
                    });


                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d("sss","result::"+ex.getMessage());
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
