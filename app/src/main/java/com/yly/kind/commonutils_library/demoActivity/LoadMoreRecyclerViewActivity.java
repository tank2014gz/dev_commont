package com.yly.kind.commonutils_library.demoActivity;

import android.os.Bundle;

import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.yly.kind.commonutils_library.R;
import com.yly.kind.commonutils_library.demoAdapter.MuliteTypeAdapter;
import com.yly.kind.commonutils_library.widget.DMRefreshHeadView;
import com.yly.kind.commonutils_library.widget.LoadMoreRecyclerView;
import com.zly.www.easyrecyclerview.EasyDefRecyclerView;
import com.zly.www.easyrecyclerview.listener.OnLoadListener;
import com.zly.www.easyrecyclerview.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * Created by yuliyan on 2017/4/5.
 */

public class LoadMoreRecyclerViewActivity    extends AppCompatActivity implements OnLoadListener,OnRefreshListener {

    private EasyDefRecyclerView moreRecyclerView;
    private LinearLayoutManager linearLayoutManager;

    private MuliteTypeAdapter muliteTypeAdapter;
    private ArrayList<String> strings = new ArrayList<>();
    private Handler mHandler = new Handler();
    private View mHeadView;
    private View mBannerView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadmore_activity_layout);
        moreRecyclerView = (EasyDefRecyclerView) findViewById(R.id.erv);
        mHeadView= new DMRefreshHeadView(LoadMoreRecyclerViewActivity.this);
        moreRecyclerView.setHeaderView(mHeadView);
        mBannerView=LayoutInflater.from(this).inflate(R.layout.share_widget_item_layout,null,false);
        moreRecyclerView.addView(mBannerView,0);
        linearLayoutManager=new LinearLayoutManager(LoadMoreRecyclerViewActivity.this, RecyclerView.VERTICAL,false);
        moreRecyclerView.setLayoutManager(linearLayoutManager);
        muliteTypeAdapter = new MuliteTypeAdapter();
        moreRecyclerView.setLastUpdateTimeRelateObject(this);
        moreRecyclerView.setOnLoadListener(this);
        moreRecyclerView.setOnRefreshListener(this);
        moreRecyclerView.setAdapter(muliteTypeAdapter);
        for (int i=0;i<20;i++){
            strings.add("str"+i);
        }
        muliteTypeAdapter.addAll(strings);
        moreRecyclerView.autoRefresh(true);

    }


    @Override
    public void onRefreshListener() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    muliteTypeAdapter.insert("最新数据", 0);
                }
            }
        }, 3000);
    }

    @Override
    public void onLoadListener() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 10; i++) {
                    muliteTypeAdapter.add("加载更多");
                }
            }
        }, 3000);
    }
}