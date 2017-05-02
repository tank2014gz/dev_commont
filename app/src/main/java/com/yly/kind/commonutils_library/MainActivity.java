package com.yly.kind.commonutils_library;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.yly.kind.commonutils_library.demoActivity.DownloadActivity;
import com.yly.kind.commonutils_library.demoActivity.LoadMoreRecyclerViewActivity;
import com.yly.kind.commonutils_library.demoActivity.LoadMoreRecyclerViewActivity1;
import com.yly.kind.commonutils_library.demoActivity.NewsFontSizeSettingActivity;
import com.yly.kind.commonutils_library.demoActivity.RecyclerRefreshAndLoadMoreActivity;
import com.yly.kind.commonutils_library.demoActivity.RecyclerViewDecorationActivity;
import com.yly.kind.commonutils_library.demoActivity.RecyclerViewFullscreenActivity;
import com.yly.kind.commonutils_library.demoActivity.RecyclerViewHeaderAndFooterActivity;
import com.yly.kind.commonutils_library.demoActivity.RippleActivity;
import com.yly.kind.commonutils_library.demoActivity.ShareMenuActivity;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, OnItemClickedListener {

    private SwipeRefreshLayout mSwipeRefreshWidget;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private DemoRecycleViewAdapter mAdapter;
    private int lastVisibleItem;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (mSwipeRefreshWidget.isRefreshing()) {
                mSwipeRefreshWidget.setRefreshing(false);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwipeRefreshWidget = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
        mRecyclerView = (RecyclerView) findViewById(android.R.id.list);
        mSwipeRefreshWidget.setColorSchemeResources(R.color.holo_green_dark, R.color.holo_red_dark,
            R.color.holo_purple, R.color.holo_orange_light);
        mSwipeRefreshWidget.setOnRefreshListener(this);

        mSwipeRefreshWidget.setProgressViewOffset(false, 0, (int) TypedValue
            .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                .getDisplayMetrics()));

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                    mSwipeRefreshWidget.setRefreshing(true);
                    // 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
                    mHandler.sendEmptyMessageDelayed(0, 3000);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }

        });

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new DemoRecycleViewAdapter(this, DemoFuctionName.getInstance().getDatas());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setItemClickedListener(this);

        // 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
        mSwipeRefreshWidget.setRefreshing(true);
        mHandler.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    public void OnItem(View itemView, int position) {
        Toast.makeText(this, "item" + position, Toast.LENGTH_SHORT).show();
        switch (position) {
            case 0:
                break;
            case 1:
                Intent rIntent = new Intent(MainActivity.this, RecyclerViewFullscreenActivity.class);
                startActivityForResult(rIntent, 0);
                break;
            case 2:
                Intent rIntent1 = new Intent(MainActivity.this, RecyclerViewHeaderAndFooterActivity.class);
                startActivityForResult(rIntent1, 0);
                break;
            case 3:
                Intent rIntent2 = new Intent(MainActivity.this, RecyclerViewDecorationActivity.class);
                startActivityForResult(rIntent2, 0);
                break;
            case 4:
                Intent rIntent3 = new Intent(MainActivity.this, RecyclerRefreshAndLoadMoreActivity.class);
                startActivityForResult(rIntent3, 0);
                break;


            case 5:
                Intent rIntent4 = new Intent(MainActivity.this, RecyclerRefreshAndLoadMoreActivity.class);
                startActivityForResult(rIntent4, 0);
                break;
            case 7:
                Intent rIntent5 = new Intent(MainActivity.this, DownloadActivity.class);
                startActivityForResult(rIntent5, 0);
                break;
            case 8:
                Intent rIntent6=new Intent(MainActivity.this, LoadMoreRecyclerViewActivity.class);
                startActivityForResult(rIntent6,8);
                break;
            case 9:
                Intent intent=new Intent(MainActivity.this,NewsFontSizeSettingActivity.class);
                startActivityForResult(intent,9);
                break;
            case 10:
                Intent intent10=new Intent(MainActivity.this, ShareMenuActivity.class);
                startActivityForResult(intent10,10);
                break;
            case 11:
                Intent rIntent11=new Intent(MainActivity.this, LoadMoreRecyclerViewActivity1.class);
                startActivityForResult(rIntent11,11);
                break;
            case 12:
                Intent rIntent12=new Intent(MainActivity.this, RippleActivity.class);
                startActivityForResult(rIntent12,11);
                break;
        }
    }
}
