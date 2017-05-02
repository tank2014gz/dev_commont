package com.yly.kind.commonutils_library.demoActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yly.kind.commonutils_library.R;
import com.yly.kind.commonutils_library.demoAdapter.RecyclerDecorationAdapter;

import java.util.ArrayList;

public class RecyclerViewDecorationActivity extends AppCompatActivity {

    private RecyclerDecorationAdapter mDecorationAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> mArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_decoration);
        mRecyclerView = (RecyclerView) findViewById(R.id.decoration_recyclerview);
        mLayoutManager = new LinearLayoutManager(RecyclerViewDecorationActivity.this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
        });
        mDecorationAdapter = new RecyclerDecorationAdapter(mArrayList);
        mRecyclerView.setAdapter(mDecorationAdapter);
    }


    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        for (int i = 0; i < 100; i++) {
            mArrayList.add("recycler dectoration " + i);
        }
        mDecorationAdapter.notifyDataSetChanged();
    }
}
