package com.yly.kind.commonutils_library.demoActivity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.yly.kind.commonutils_library.R;
import com.yly.kind.commonutils_library.window.SharePopView;

public class ShareMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mShowMenu1;
    private TextView mShowMenu2;
    private SharePopView mShareMenu1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_menu);
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

        mShowMenu1=(TextView) findViewById(R.id.show_menu);
        mShowMenu1.setOnClickListener(this);

        mShowMenu2=(TextView)findViewById(R.id.show_menu1);
        mShowMenu2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show_menu:
                if(mShareMenu1==null){
                    mShareMenu1=new SharePopView(ShareMenuActivity.this);
                }
                if(mShareMenu1.isShowing()){
                    mShareMenu1.dismiss();
                }else {
                    mShareMenu1.showWindow(v);
                }

                break;
            case R.id.show_menu1:
                break;
        }
    }
}
