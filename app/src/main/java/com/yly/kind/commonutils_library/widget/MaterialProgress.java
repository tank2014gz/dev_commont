package com.yly.kind.commonutils_library.widget;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by lielvwang on 2017/2/15.
 */

public class MaterialProgress extends View {

    private int mStartColor = 0XFF5588FF;
    private int mMidColor = 0xFFEE8834;
    private int mEndColor = 0xFF773355;
    private int mRadius = 30;
    private int mStrokeWidth = 5;

    private Paint mGapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private ArrayList<Integer> mColors = new ArrayList<Integer>();
    private int mColorIndex = 0;
    private boolean mColorChange = true;


    private int mPaddingLeft;
    private int mPaddingRight;
    private int mPaddingTop;
    private int mPaddingBottom;
    private int mWidth;
    private int mHeight;
    private RectF mRectF;
    private float mProgress;
    private float mSpeed = 230f;
    private long mLastTimeAnimated = 0;


    public MaterialProgress(Context context) {
        super(context);
    }

    public MaterialProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaterialProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MaterialProgress(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
