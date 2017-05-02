package com.yly.kind.commonutils_library;

import java.util.ArrayList;

/**
 * Created by lielvwang on 2017/2/14.
 */

public class DemoFuctionName {

    private static DemoFuctionName mInstance;
    public static ArrayList<String> FuctionNameArray = new ArrayList<String>();

    private DemoFuctionName() {
        super();
        FuctionNameArray.add("系统默认的缓存路劲设置");
        FuctionNameArray.add("RecyclerView");
        FuctionNameArray.add("RecyclerView Header and Footer");
        FuctionNameArray.add("RecyclerView Decoration 分割线");
        FuctionNameArray.add("RecyclerView LoadAndRefresh");
        FuctionNameArray.add("MaterialPageStateLayout");
        FuctionNameArray.add("JNI");
        FuctionNameArray.add("断点下载");
        FuctionNameArray.add("LoadMoreRecyclerView");
        FuctionNameArray.add("刻度尺");
        FuctionNameArray.add("share_menu");
        FuctionNameArray.add("LoadMoreRecyclerView1");
        FuctionNameArray.add("Ripple_drawable");
        FuctionNameArray.add("Rxjava响应式");
    }

    public static DemoFuctionName getInstance() {
        if (mInstance == null) {
            synchronized (DemoFuctionName.class) {
                if (mInstance == null) {
                    mInstance = new DemoFuctionName();
                }
            }
        }
        return mInstance;
    }


    public ArrayList getDatas() {
        return FuctionNameArray;
    }

}
