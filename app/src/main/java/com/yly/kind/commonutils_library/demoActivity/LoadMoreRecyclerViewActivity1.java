package com.yly.kind.commonutils_library.demoActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yly.kind.commonutils_library.R;
import com.yly.kind.commonutils_library.demoAdapter.MuliteTypeAdapter1;
import com.yly.kind.commonutils_library.widget.DMEasyDefRecyclerVoew;
import com.yly.kind.commonutils_library.widget.DMEasyRecyclerView;
import com.yly.kind.commonutils_library.widget.DMRefreshHeadView;
import com.zly.www.easyrecyclerview.listener.OnLoadListener;
import com.zly.www.easyrecyclerview.listener.OnRefreshListener;

import java.util.ArrayList;

public class LoadMoreRecyclerViewActivity1 extends AppCompatActivity implements OnLoadListener,OnRefreshListener {


    private DMEasyDefRecyclerVoew dmEasyRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private View mHeadView;
    private MuliteTypeAdapter1 typeAdapter1;
    private ArrayList<String> strings = new ArrayList<>();
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more_recycler_view1);
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
        dmEasyRecyclerView=(DMEasyDefRecyclerVoew) findViewById(R.id.dm_loadmore_recycler);
        linearLayoutManager=new LinearLayoutManager(LoadMoreRecyclerViewActivity1.this, RecyclerView.VERTICAL,false);
        dmEasyRecyclerView.setLayoutManager(linearLayoutManager);
        mHeadView= new DMRefreshHeadView(LoadMoreRecyclerViewActivity1.this);
        dmEasyRecyclerView.setHeaderView(mHeadView);
        typeAdapter1=new MuliteTypeAdapter1();
        dmEasyRecyclerView.setLastUpdateTimeRelateObject(this);
        dmEasyRecyclerView.setOnLoadListener(this);
        dmEasyRecyclerView.setOnRefreshListener(this);

        dmEasyRecyclerView.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
                int width = getResources().getDimensionPixelSize(R.dimen.menu_width);
                // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                // 添加右侧的，如果不添加，则右侧不会出现菜单。
                {
                    SwipeMenuItem deleteItem = new SwipeMenuItem(LoadMoreRecyclerViewActivity1.this)
                            .setBackgroundDrawable(R.drawable.selector_red)
//                            .setImage(R.mipmap.ic_action_delete)
                            .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                            .setTextColor(Color.WHITE)
                            .setWidth(width)
                            .setHeight(height);
                    swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。

                }
            }

        });
        dmEasyRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener );
        dmEasyRecyclerView.setAdapter(typeAdapter1);
        for (int i=0;i<20;i++){
            strings.add("str"+i);
        }
        typeAdapter1.addAll(strings);
//        dmEasyRecyclerView.autoRefresh(true);
    }
    /**
     * 菜单点击监听。
     */
    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
//                Long aid = mArrayList.get(adapterPosition).getId();
//                if (LoginManager.getInstance().isLogin()) {
//                    Long collectionId = mArrayList.get(adapterPosition).getCollectionid();
//                    cancelcollection(adapterPosition, collectionId, aid);
//                } else {
//                    mCollectionManager.removeCollection(mArrayList.get(adapterPosition));
//                    mArrayList.remove(adapterPosition);
//                    mArticalAdapter.notifyDataSetChanged();
//                    if (mArrayList.size() == 0) {
//                        mDataNullView.setVisibility(View.VISIBLE);
//                    }
//                }


            }
        }
    };
    @Override
    public void onRefreshListener() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    typeAdapter1.insert("最新数据", 0);
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
                    typeAdapter1.add("加载更多");
                }
            }
        }, 3000);
    }

}
