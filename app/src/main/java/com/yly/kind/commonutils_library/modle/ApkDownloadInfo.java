package com.yly.kind.commonutils_library.modle;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by lielvwang on 2017/3/29.
 */
@Table(name = "download_apk")
public class ApkDownloadInfo {
    @Id
    private int id;
    @Column(column = "startPoint")
    private int startPoint;
    @Column(column = "endPoint")
    private int endPoint;
    @Column(column = "completeSize")
    private int completeSize;
    @Column(column = "downloadUrl")
    private String downloadUrl;
    @Column(column = "downloadLocalFile")
    private String downloadLocalFile;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(int startPoint) {
        this.startPoint = startPoint;
    }

    public int getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(int endPoint) {
        this.endPoint = endPoint;
    }

    public int getCompleteSize() {
        return completeSize;
    }

    public void setCompleteSize(int completeSize) {
        this.completeSize = completeSize;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDownloadLocalFile() {
        return downloadLocalFile;
    }

    public void setDownloadLocalFile(String downloadLocalFile) {
        this.downloadLocalFile = downloadLocalFile;
    }

    @Override
    public String toString() {
        return "ApkDownloadInfo{" +
            "\nid=" + id +
            "\n, startPoint=" + startPoint +
            "\n, endPoint=" + endPoint +
            "\n, completeSize=" + completeSize +
            "\n, downloadUrl='" + downloadUrl + '\'' +
            "\n, downloadLocalFile='" + downloadLocalFile + '\'' +
            '}';
    }
}
