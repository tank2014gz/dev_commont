package com.yly.kind.commonutils_library.demoActivity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.yly.kind.commonutils_library.EndlessRecyclerOnScrollListener;
import com.yly.kind.commonutils_library.R;
import com.yly.kind.commonutils_library.demoAdapter.RecycleDemoAdapter;
import java.util.ArrayList;

public class RecyclerRefreshAndLoadMoreActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayoutManager layoutManager;
    private ArrayList<String> mDatas = new ArrayList<>();
    private RecycleDemoAdapter mAdapter;
    private int pagerIndex = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_refresh_and_load_more);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshLayout.setColorSchemeResources(
            R.color.holo_green_dark,
            R.color.holo_purple,
            R.color.holo_orange_light,
            R.color.holo_red_dark
        );
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(RecyclerRefreshAndLoadMoreActivity.this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
        });
        mAdapter = new RecycleDemoAdapter(RecyclerRefreshAndLoadMoreActivity.this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pagerIndex = 1;
                        mDatas.clear();
                        for (int i = 0; i < 20; i++) {
                            mDatas.add(0, "刷新获取的新数据 number" + i);
                        }
                        mAdapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {
                pagerIndex++;
                for (int i = pagerIndex * 10 + 1; i < 10 * (1 + pagerIndex);
                     i++) {
                    mDatas.add("加载更多获取的新数据 number" + i);
                }
                mAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        for (int i = 0; i < 30; i++) {
            mDatas.add("recyclerDemo" + i + "---");
        }
        mAdapter.notifyDataSetChanged();

    }
}
