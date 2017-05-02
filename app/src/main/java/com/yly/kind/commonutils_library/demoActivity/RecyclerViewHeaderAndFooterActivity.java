package com.yly.kind.commonutils_library.demoActivity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.yly.kind.commonutils_library.R;
import com.yly.kind.commonutils_library.demoAdapter.RecyclerHeaderAndFooterAdapter;

import java.util.ArrayList;

public class RecyclerViewHeaderAndFooterActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> mArrayList = new ArrayList<>();
    private RecyclerHeaderAndFooterAdapter mHeaderAndFooterAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_header_and_footer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_header_footer);
        mLayoutManager = new LinearLayoutManager(RecyclerViewHeaderAndFooterActivity.this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mHeaderAndFooterAdapter = new RecyclerHeaderAndFooterAdapter(RecyclerViewHeaderAndFooterActivity.this, mArrayList);
        mRecyclerView.setAdapter(mHeaderAndFooterAdapter);
        View headView = LayoutInflater.from(RecyclerViewHeaderAndFooterActivity.this).inflate(R.layout.item_header_layout, mRecyclerView, false);
        mHeaderAndFooterAdapter.setHeaderView(headView);
        View footView = LayoutInflater.from(RecyclerViewHeaderAndFooterActivity.this).inflate(R.layout.item_footer_layout, mRecyclerView, false);
        mHeaderAndFooterAdapter.setFooterView(footView);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        for (int i = 4; i < 10; i++) {
            mArrayList.add("item data" + i);
        }
        mHeaderAndFooterAdapter.notifyDataSetChanged();
    }

}
