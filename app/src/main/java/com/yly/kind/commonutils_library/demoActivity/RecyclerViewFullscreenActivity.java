package com.yly.kind.commonutils_library.demoActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.yly.kind.commonutils_library.R;
import com.yly.kind.commonutils_library.demoAdapter.RecycleDemoAdapter;

import java.util.ArrayList;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class RecyclerViewFullscreenActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<String> mArrayList = new ArrayList<String>();
    private RecycleDemoAdapter mRecycleDemoAdapter;
    private boolean isRecover;
    private int fangxiang;
    private EditText mEditText;
    private EditText mPositonEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_fullscreen);
        mEditText = (EditText) findViewById(R.id.edit_info);
        mPositonEditText = (EditText) findViewById(R.id.edit_position);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        fangxiang = RecyclerView.HORIZONTAL;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager
                //此参数是定义线性布局的方向，是否翻转
                (RecyclerViewFullscreenActivity.this, fangxiang, isRecover);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);//如果Item的高度是固定的这个方法可以提高性能
        mRecycleDemoAdapter = new RecycleDemoAdapter(RecyclerViewFullscreenActivity.this, mArrayList);
        mRecyclerView.setAdapter(mRecycleDemoAdapter);

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        for (int i = 0; i < 6; i++) {
            mArrayList.add("中国的制度规范法则（" + i + ");");
        }
        mRecycleDemoAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recycle_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.vertical:
                fangxiang = RecyclerView.VERTICAL;
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager
                        //此参数是定义线性布局的方向，是否翻转
                        (RecyclerViewFullscreenActivity.this, fangxiang, isRecover);
                mRecyclerView.setLayoutManager(layoutManager);
                break;
            case R.id.horizontal:
                fangxiang = RecyclerView.HORIZONTAL;
                layoutManager = new LinearLayoutManager
                        //此参数是定义线性布局的方向，是否翻转
                        (RecyclerViewFullscreenActivity.this, fangxiang, isRecover);
                mRecyclerView.setLayoutManager(layoutManager);
                break;
            case R.id.recoverOrder:
                isRecover = true;
                layoutManager = new LinearLayoutManager
                        //此参数是定义线性布局的方向，是否翻转
                        (RecyclerViewFullscreenActivity.this, fangxiang, isRecover);
                mRecyclerView.setLayoutManager(layoutManager);
                break;
            case R.id.order:
                isRecover = false;
                layoutManager = new LinearLayoutManager
                        //此参数是定义线性布局的方向，是否翻转
                        (RecyclerViewFullscreenActivity.this, fangxiang, isRecover);
                mRecyclerView.setLayoutManager(layoutManager);
                break;
            case R.id.grid:
                isRecover = false;
                layoutManager = new GridLayoutManager(RecyclerViewFullscreenActivity.this, 3, RecyclerView.VERTICAL, false);
                mRecyclerView.setLayoutManager(layoutManager);
                break;
            case R.id.add:
                String e = mEditText.getText().toString();
                if (TextUtils.isEmpty(e)) {
                    break;
                }
                String n = mPositonEditText.getText().toString();
                if (TextUtils.isEmpty(n)) {
                    n = "0";
                }
                int position = Integer.parseInt(n);
                mRecycleDemoAdapter.addItem(e, position);
                break;
            case R.id.delected:
                n = mPositonEditText.getText().toString();
                if (TextUtils.isEmpty(n)) {
                    n = "0";
                }
                position = Integer.parseInt(n);
                mRecycleDemoAdapter.delecteItem(position);
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
