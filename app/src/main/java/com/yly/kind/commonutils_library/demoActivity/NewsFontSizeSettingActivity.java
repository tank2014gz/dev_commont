package com.yly.kind.commonutils_library.demoActivity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.yly.kind.commonutils_library.R;
import com.yly.kind.commonutils_library.widget.NewsFontSettingView;

import java.util.Arrays;
import java.util.List;

public class NewsFontSizeSettingActivity extends AppCompatActivity {

    private NewsFontSettingView mNewsFontSetting;
    private List<String> titles = Arrays.asList("小", "中", "大", "特大");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_font_size_setting);
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

        mNewsFontSetting=(NewsFontSettingView) findViewById(R.id.newsFontSettingView);
        mNewsFontSetting.setOnSelectChangeListener(new NewsFontSettingView.OnSelectChangeListener() {
            @Override
            public void change(int position) {
                Toast.makeText(NewsFontSizeSettingActivity.this,titles.get(position),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
