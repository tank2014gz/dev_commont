package com.yly.kind.commonutils_library.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yly.kind.commonutils_library.R;

import com.zly.www.easyrecyclerview.ptrlib.PtrFrameLayout;
import com.zly.www.easyrecyclerview.ptrlib.PtrUIHandler;
import com.zly.www.easyrecyclerview.ptrlib.indicator.PtrIndicator;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yuliyan on 2017/4/5.
 */

public class DMRefreshHeadView extends FrameLayout implements PtrUIHandler {

    private final static String KEY_SharedPreferences = "cube_ptr_classic_last_update";
    private static SimpleDateFormat sDataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int mRotateAniTime = 1200;
    static final Interpolator ANIMATION_INTERPOLATOR = new LinearInterpolator();
    private   Matrix mHeaderImageMatrix;
    private TextView mTitleTextView;

    private ImageView mProgressImage;
    private ImageView mProgressBottomImage;
    private long mLastUpdateTime = -1;

    private String mLastUpdateTimeKey;


    private  LastUpdateTimeUpdater mLastUpdateTimeUpdater = new LastUpdateTimeUpdater();
    private RotateAnimation mRotateAnimation;

    public DMRefreshHeadView(@NonNull Context context) {
        super(context);
        initViews(null);
    }

    public DMRefreshHeadView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews(attrs);
    }

    public DMRefreshHeadView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DMRefreshHeadView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews(attrs);
    }



    protected void initViews(AttributeSet attrs) {
        TypedArray arr = getContext().obtainStyledAttributes(attrs, com.zly.www.easyrecyclerview.R.styleable.PtrClassicHeader, 0, 0);
        if (arr != null) {
            mRotateAniTime = arr.getInt(R.styleable.PtrClassicHeader_ptr_rotate_ani_time, mRotateAniTime);
        }
        mRotateAnimation = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
        mRotateAnimation.setDuration(mRotateAniTime);
        mRotateAnimation.setRepeatCount(Animation.INFINITE);
        mRotateAnimation.setRepeatMode(Animation.RESTART);

        View header = LayoutInflater.from(getContext()).inflate( R.layout.recycler_header, this);



        mTitleTextView = (TextView) header.findViewById(R.id.pull_to_refresh_text);
        mProgressImage = (ImageView) header.findViewById(R.id.pull_to_refresh_image);
        mProgressImage.setImageResource(R.drawable.gx_jiantou);
        mProgressBottomImage=(ImageView) header.findViewById(R.id.pull_to_refresh_base);
        mProgressImage.setScaleType(ImageView.ScaleType.MATRIX);
        mHeaderImageMatrix = new Matrix();
        mProgressImage.setImageMatrix(mHeaderImageMatrix);
        resetView();
    }
    private void resetView() {
        mProgressImage.clearAnimation();

        resetImageRotation();
        mProgressImage.setVisibility(INVISIBLE);
        mProgressBottomImage.setVisibility(INVISIBLE);
    }
    private void resetImageRotation() {
        if (null != mHeaderImageMatrix) {
            mHeaderImageMatrix.reset();
            mProgressImage.setImageMatrix(mHeaderImageMatrix);
        }
    }


    @Override
    public void onUIReset(PtrFrameLayout frame) {
        resetView();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        mLastUpdateTimeUpdater.start();

        mProgressImage.setVisibility(INVISIBLE);
        mProgressBottomImage.setVisibility(INVISIBLE);

        mTitleTextView.setVisibility(VISIBLE);
        if (frame.isPullToRefresh()) {
            mTitleTextView.setText(getResources().getString(com.zly.www.easyrecyclerview.R.string.cube_ptr_pull_down_to_refresh));
        } else {
            mTitleTextView.setText(getResources().getString(com.zly.www.easyrecyclerview.R.string.cube_ptr_pull_down));
        }
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        mProgressImage.setVisibility(VISIBLE);
        mProgressBottomImage.setVisibility(VISIBLE);
        mTitleTextView.setVisibility(VISIBLE);
        mTitleTextView.setText(com.zly.www.easyrecyclerview.R.string.cube_ptr_refreshing);
        mProgressImage.startAnimation(mRotateAnimation);

        mLastUpdateTimeUpdater.stop();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {

        mProgressImage.setVisibility(INVISIBLE);
        mProgressBottomImage.setVisibility(INVISIBLE);
        mTitleTextView.setVisibility(VISIBLE);
        mTitleTextView.setText(getResources().getString(com.zly.www.easyrecyclerview.R.string.cube_ptr_refresh_complete));

        // update last update time
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(KEY_SharedPreferences, 0);
        if (!TextUtils.isEmpty(mLastUpdateTimeKey)) {
            mLastUpdateTime = new Date().getTime();
            sharedPreferences.edit().putLong(mLastUpdateTimeKey, mLastUpdateTime).commit();
        }
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();

        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromBottomUnderTouch(frame);

            }
        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromTopUnderTouch(frame);

            }
        }
    }
    private void crossRotateLineFromTopUnderTouch(PtrFrameLayout frame) {
        if (!frame.isPullToRefresh()) {
            mTitleTextView.setVisibility(VISIBLE);
            mTitleTextView.setText(com.zly.www.easyrecyclerview.R.string.cube_ptr_release_to_refresh);
        }
    }
    private void crossRotateLineFromBottomUnderTouch(PtrFrameLayout frame) {
        mTitleTextView.setVisibility(VISIBLE);
        if (frame.isPullToRefresh()) {
            mTitleTextView.setText(getResources().getString(com.zly.www.easyrecyclerview.R.string.cube_ptr_pull_down_to_refresh));
        } else {
            mTitleTextView.setText(getResources().getString(com.zly.www.easyrecyclerview.R.string.cube_ptr_pull_down));
        }
    }
    private class LastUpdateTimeUpdater implements Runnable {

        private boolean mRunning = false;

        private void start() {
            if (TextUtils.isEmpty(mLastUpdateTimeKey)) {
                return;
            }
            mRunning = true;
            run();
        }

        private void stop() {
            mRunning = false;
            removeCallbacks(this);
        }

        @Override
        public void run() {

            if (mRunning) {
                postDelayed(this, 1000);
            }
        }
    }



    /**
     * Specify the last update time by this key string
     *
     * @param key
     */
    public void setLastUpdateTimeKey(String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        mLastUpdateTimeKey = key;
    }

    /**
     * Using an object to specify the last update time.
     *
     * @param object
     */
    public void setLastUpdateTimeRelateObject(Object object) {
        setLastUpdateTimeKey(object.getClass().getName());
    }


}
