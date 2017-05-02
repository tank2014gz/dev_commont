package com.yly.kind.commonutils_library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by lielvwang on 2017/2/17.
 */

public class DecorationLine extends RecyclerView.ItemDecoration {
    private Context mContext;
    private Drawable mDivider;
    private int mOriention;
    private static final int HORZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    private static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    //我们通过获取系统属性中的listDivider来添加，在系统中的AppTheme中设置
    private static int[] ATTRS = new int[]{android.R.attr.listDivider};

    public DecorationLine(Context context, int oriention) {
        this.mContext = context;
        final TypedArray ta = context.obtainStyledAttributes(ATTRS);
        this.mDivider = ta.getDrawable(0);
        ta.recycle();
        setOriention(oriention);
    }

    public void setOriention(int oriention) {
        mOriention = oriention;

    }
}
