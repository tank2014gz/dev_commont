package com.yly.kind.commonutils_library.utils;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import com.yly.kind.commonutils_library.modle.ApkDownloadInfo;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lielvwang on 2017/3/29.
 */

public class DowloadAppTask {

    private ApkDownloadInfo apkDownloadInfo;
    private DownloadAppDBManager mDownloadAppDBMnager;
    public static final int INIT = 0;
    public static final int DOWNLOADING = 1;
    public static final int PAUSE = 2;
    public static final int STOP = 3;
    public static final int FAIL = 4;
    public static final int EXCEPTION = 5;
    public static final int SUCCESS = 6;
    private volatile int state = INIT;
    private DownloadStateListener mDownLoadListener;
    private String urlS;
    private boolean isHasDoTask;//之前是否下载过apk
    private Context mContext;

    public DowloadAppTask(Context context, ApkDownloadInfo info, DownloadStateListener listener) {
        mDownloadAppDBMnager = DownloadAppDBManager.getManager(context);
        this.mDownLoadListener = listener;
        this.mContext = context;

        if (info == null) {
            LogUtils.e("error", "info==null");
            return;
        }
        urlS = info.getDownloadUrl();
        if (TextUtils.isEmpty(urlS)) {
            LogUtils.e("error", "downloadUrl==null");
            return;
        }
        if (!Validator.isUrl(urlS)) {
            LogUtils.e("error", "downloadUrl非url");
            return;
        }
        if (isHasDoTask()) {
            apkDownloadInfo = mDownloadAppDBMnager.getTask(urlS);
        } else {
            apkDownloadInfo = info;
        }
        isHasDoTask = isHasDoTask();
    }

    /**
     * 判断是否之前已经下载过这个文件了
     */
    public boolean isHasDoTask() {
        if (urlS != null && mDownloadAppDBMnager != null) {
            ApkDownloadInfo apkDownloadInfo = mDownloadAppDBMnager.getTask(urlS);
            if (apkDownloadInfo != null) {
                int startPoint = apkDownloadInfo.getStartPoint();
                int endPoint = apkDownloadInfo.getEndPoint();
                String localFileName = apkDownloadInfo.getDownloadLocalFile();
                File localFile = new File(localFileName);
                boolean isFileExists = localFile.exists();
                if (isFileExists && endPoint > 0 && startPoint > 0 && startPoint < endPoint) {
                    return true;
                }

            }
        }
        return false;
    }

    /**
     * 判断是否已经下载完成这个apk了
     */
    public boolean isDownFinish() {
        if (urlS != null && mDownloadAppDBMnager != null) {
            ApkDownloadInfo apkDownloadInfo = mDownloadAppDBMnager.getTaskByUrl(urlS);
            if (apkDownloadInfo != null) {
                int startPoint = apkDownloadInfo.getStartPoint();
                int endPoint = apkDownloadInfo.getEndPoint();
                String localFileName = apkDownloadInfo.getDownloadLocalFile();
                File localFile = new File(localFileName);
                boolean isFileExists = localFile.exists();
                if (isFileExists && endPoint > 0 && startPoint == (endPoint + 1)) {
                    return true;
                }

            }
        }
        return false;
    }

    public int getMax() {
        if (urlS != null && mDownloadAppDBMnager != null) {
            ApkDownloadInfo apkDownloadInfo = mDownloadAppDBMnager.getTaskByUrl(urlS);
            if (apkDownloadInfo != null) {
                return apkDownloadInfo.getEndPoint();
            }
        }
        return 0;
    }

    public int getCurrentProgress() {
        if (urlS != null && mDownloadAppDBMnager != null) {
            ApkDownloadInfo apkDownloadInfo = mDownloadAppDBMnager.getTaskByUrl(urlS);
            if (apkDownloadInfo != null) {
                return apkDownloadInfo.getStartPoint();
            }
        }
        return 0;
    }


    public void startDownLoadTask() {

        new AsyncTask<Integer, Integer, Void>() {


            @Override
            protected Void doInBackground(Integer... integers) {

                if (isHasDoTask) {
                    return null;
                }
                URL url = null;
                RandomAccessFile accessFile = null;
                HttpURLConnection connection = null;
                try {
                    url = new URL(urlS);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(5000);
                    connection.setRequestMethod("GET");
                    int fileSize = connection.getContentLength();
                    File file = new File(apkDownloadInfo.getDownloadLocalFile());

                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    // 随机访问文件
                    accessFile = new RandomAccessFile(file, "rwd");
                    accessFile.setLength(fileSize);
                    apkDownloadInfo.setEndPoint(fileSize - 1);
                    mDownloadAppDBMnager.initDownloadTask(apkDownloadInfo);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (accessFile != null) {
                        try {
                            accessFile.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (apkDownloadInfo.getEndPoint() != 0 && apkDownloadInfo.getStartPoint() == (apkDownloadInfo.getEndPoint() + 1)) {
                    mDownLoadListener.downloadSuccess(apkDownloadInfo);
                    return;
                }
                DownloadThread downloadThread = new DownloadThread(apkDownloadInfo);
                downloadThread.start();
                if (mDownLoadListener != null) {
                    mDownLoadListener.startDownLoad(apkDownloadInfo, apkDownloadInfo.getEndPoint(), apkDownloadInfo.getStartPoint());
                }

            }
        }.execute();
    }


    class DownloadThread extends Thread {

        private ApkDownloadInfo downloadInfo;//下载信息

        /**
         * ApkDownloadInfo info下载文件信息
         */
        public DownloadThread(ApkDownloadInfo info) {
            this.downloadInfo = info;
        }

        @Override
        public void run() {
            super.run();
            state = DOWNLOADING;
            HttpURLConnection connection = null;
            RandomAccessFile randomAccessFile = null;
            InputStream is = null;
            String urlS = this.downloadInfo.getDownloadUrl();
            try {
                randomAccessFile = new RandomAccessFile(this.downloadInfo.getDownloadLocalFile(), "rwd");
                int start = this.downloadInfo.getStartPoint();
                LogUtils.e("seekto", "start:" + start);
                randomAccessFile.seek(start);
                URL url = new URL(urlS);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("GET");
                // 设置请求的数据的范围
                connection.setRequestProperty("Range", "bytes="
                    + (this.downloadInfo.getStartPoint()) + "-" + this.downloadInfo.getEndPoint());
                is = connection.getInputStream();
                byte[] buffer = new byte[6 * 1024]; // 6K的缓存
                int length = -1;
                while ((length = is.read(buffer)) != -1) {
                    randomAccessFile.write(buffer, 0, length); // 写缓存数据到文件
                    int compeleteSize = this.downloadInfo.getStartPoint();
                    compeleteSize += length;
                    this.downloadInfo.setStartPoint(compeleteSize);
                    mDownloadAppDBMnager.setDownloadProgress(this.downloadInfo, urlS);
                    if (mDownLoadListener != null) {
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mDownLoadListener.downloding(downloadInfo, downloadInfo.getStartPoint());
                            }
                        });

                    }
                    // 非正在下载状态时跳出循环
                    if (state == PAUSE) {
                        if (mDownLoadListener != null) {
                            ((Activity) mContext).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mDownLoadListener.downloadPause(downloadInfo);
                                }
                            });

                        }
                        break;
                    }
                }
                if ((downloadInfo.getEndPoint() != 0) && (downloadInfo.getEndPoint() + 1) == downloadInfo.getStartPoint()) {
                    state = SUCCESS;
                    if (mDownLoadListener != null) {
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mDownLoadListener.downloadSuccess(downloadInfo);
                            }
                        });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("异常退出____" + this.downloadInfo.getDownloadUrl());
                if (mDownLoadListener != null) {
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mDownLoadListener.downloadFail(downloadInfo);
                        }
                    });

                }
                state = EXCEPTION;
            } finally {
                // 不管发生了什么事，都要保存下载信息到数据库
                try {
                    if (is != null) {
                        is.close();
                    }
                    if (randomAccessFile != null) {
                        randomAccessFile.close();
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (state == SUCCESS) {
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mDownLoadListener.downloadSuccess(downloadInfo);
                        }
                    });

                }
            }
        }
    }


    public void pauseDownloadTask() {
        synchronized (this) {
            state = PAUSE;
        }
    }


    public boolean isDownloading() {
        synchronized (this) {
            return state == DOWNLOADING;
        }
    }


    public interface DownloadStateListener {
        public void startDownLoad(ApkDownloadInfo info, int max, int progress);

        public void downloding(ApkDownloadInfo info, int progress);

        public void downloadSuccess(ApkDownloadInfo info);

        public void downloadFail(ApkDownloadInfo info);


        public void downloadPause(ApkDownloadInfo info);
    }

}
