package com.yly.kind.commonutils_library.demoActivity;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.yly.kind.commonutils_library.MainActivity;
import com.yly.kind.commonutils_library.R;
import com.yly.kind.commonutils_library.modle.ApkDownloadInfo;
import com.yly.kind.commonutils_library.utils.DowloadAppTask;
import com.yly.kind.commonutils_library.utils.DownloadAppDBManager;
import com.yly.kind.commonutils_library.utils.Validator;
import java.io.File;

public class DownloadActivity extends AppCompatActivity implements View.OnClickListener {

    Button mDownLoad;
    Button mClearDB;
    Button mClearFile;
    ApkDownloadInfo info;

    private DowloadAppTask dowloadAppTask;
    private ProgressBar mProgressBar;

    private Button find_task_btn;
    private TextView showTaskInfo;
    private Button find_file_btn;
    private TextView showFileInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
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
        mDownLoad = (Button) findViewById(R.id.download);
        mDownLoad.setOnClickListener(this);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_progressbar);
        mClearDB = (Button) findViewById(R.id.clearDB);
        mClearFile = (Button) findViewById(R.id.clearFile);
        mClearFile.setOnClickListener(this);
        mClearDB.setOnClickListener(this);
        info = new ApkDownloadInfo();
        String url = "http://bookingctrip-android.oss-cn-shanghai.aliyuncs.com/bookingctrip/v2.0/bookingctrip_v2.0.apk";
        info.setDownloadUrl(url);
        String path = "";
        int inde = url.lastIndexOf("/");
        if (inde > 0) {
            path = url.substring(inde);
        }
        String fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/_a1";
        File parentFile = new File(fileName);
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        fileName += path;
        info.setDownloadLocalFile(fileName);
        if (dowloadAppTask == null) {
            dowloadAppTask = new DowloadAppTask(DownloadActivity.this, info, new DowloadAppTask.DownloadStateListener() {
                @Override
                public void startDownLoad(ApkDownloadInfo info, int max, int progress) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setMax(max);
                    mProgressBar.setProgress(progress);
                    mDownLoad.setText("暂停下载");
                }

                @Override
                public void downloding(ApkDownloadInfo info, int progress) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(progress);
                }

                @Override
                public void downloadSuccess(ApkDownloadInfo info) {
                    mProgressBar.setVisibility(View.GONE);
                    mDownLoad.setText("去安装");
                }

                @Override
                public void downloadFail(ApkDownloadInfo info) {
                    mDownLoad.setText("下载失败");
                }

                @Override
                public void downloadPause(ApkDownloadInfo info) {
                    mDownLoad.setText("继续下载");
                }
            });
        }

        if (dowloadAppTask.isDownFinish()) {
            mDownLoad.setText("去安装");
        } else {
            if (dowloadAppTask.isHasDoTask()) {
                mDownLoad.setText("继续下载");
                mProgressBar.setMax(dowloadAppTask.getMax());
                mProgressBar.setProgress(dowloadAppTask.getCurrentProgress());
            }
        }


        find_task_btn = (Button) findViewById(R.id.find_task_by_url);
        showTaskInfo = (TextView) findViewById(R.id.show_task_info);
        find_task_btn.setOnClickListener(this);
        find_file_btn = (Button) findViewById(R.id.find_file_info);
        find_file_btn.setOnClickListener(this);
        showFileInfo = (TextView) findViewById(R.id.show_file_info);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.download:
                if (dowloadAppTask.isDownloading()) {
                    dowloadAppTask.pauseDownloadTask();
                } else {
                    dowloadAppTask.startDownLoadTask();
                }
                break;
            case R.id.clearDB:
                DownloadAppDBManager.getManager(DownloadActivity.this).clearDB();
                break;
            case R.id.clearFile:

                File file = new File(info.getDownloadLocalFile());
                if (file.exists()) {
                    file.delete();
                }
                break;
            case R.id.find_task_by_url:
                ApkDownloadInfo info1 = DownloadAppDBManager.getManager(DownloadActivity.this).getTaskByUrl(info.getDownloadUrl());
                if (info1 != null) {
                    showTaskInfo.setText(info1.toString());
                }
                break;
            case R.id.find_file_info:
                File file1 = new File(info.getDownloadLocalFile());
                if (file1.exists()) {
                    showFileInfo.setText("file:" + file1.length());
                }

                break;
        }

    }
}
