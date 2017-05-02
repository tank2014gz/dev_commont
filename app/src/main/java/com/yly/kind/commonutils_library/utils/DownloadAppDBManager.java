package com.yly.kind.commonutils_library.utils;

import android.content.Context;
import android.text.TextUtils;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.yly.kind.commonutils_library.modle.ApkDownloadInfo;
import retrofit2.http.PUT;

/**
 * Created by lielvwang on 2017/3/29.
 */

public class DownloadAppDBManager {

    private volatile static DownloadAppDBManager mManager;
    private static DbUtils mDBManager;

    private DownloadAppDBManager(Context context) {
        super();
        mDBManager = DbUtils.create(context, "dm_apk_download.db", 1, new DbUtils.DbUpgradeListener() {

            @Override
            public void onUpgrade(DbUtils dbUtils, int i, int i1) {

            }
        });
    }


    public static DownloadAppDBManager getManager(Context context) {
        if (mManager == null) {
            synchronized (DownloadAppDBManager.class) {
                if (mManager == null) {
                    mManager = new DownloadAppDBManager(context);
                }
            }
        }
        return mManager;
    }


    public boolean initDownloadTask(ApkDownloadInfo info) {
        if (info != null && mDBManager != null) {
            if (!hasDownloadTask(info.getDownloadUrl())) {
                try {
                    mDBManager.save(info);
                    return true;
                } catch (DbException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }


    public ApkDownloadInfo getTask(String url) {
        if (!TextUtils.isEmpty(url) && mDBManager != null) {
            try {
                ApkDownloadInfo info = mDBManager.findFirst(Selector.from(ApkDownloadInfo.class).where("downloadUrl", "=", url));
                if (info == null) {
                    return null;
                }
                return info;
            } catch (DbException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }


    public boolean hasDownloadTask(String url) {
        if (url != null && mDBManager != null) {
            try {
                ApkDownloadInfo info = mDBManager.findFirst(Selector.from(ApkDownloadInfo.class).where("downloadUrl", "=", url));
                if (info != null) {
                    return true;
                }
            } catch (DbException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }


    /**
     * 刷新数据库中进度
     */
    public void setDownloadProgress(ApkDownloadInfo info, String url) {
        try {
            mDBManager.update(info, WhereBuilder.b("downloadUrl", "=", url));
            //ApkDownloadInfo info = mDBManager.findFirst(Selector.from(ApkDownloadInfo.class).where("downloadUrl", "=", url));
            //info.setStartPoint(progress);
            //mDBManager.replace(info);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    public void removeDownloadTask(String url) {
        try {
            mDBManager.delete(ApkDownloadInfo.class, WhereBuilder.b("downloadUrl", "=", url));
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    public ApkDownloadInfo getTaskByUrl(String url) {
        ApkDownloadInfo taskInfo = null;
        try {
            taskInfo = mDBManager.findFirst(Selector.from(ApkDownloadInfo.class).where("downloadUrl", "=", url));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return taskInfo;
    }

    public void clearDB() {
        try {
            mDBManager.deleteAll(ApkDownloadInfo.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public void closeTaskDB() {
        mDBManager.close();
    }


}
