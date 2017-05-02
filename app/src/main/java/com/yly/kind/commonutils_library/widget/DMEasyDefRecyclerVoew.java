package com.yly.kind.commonutils_library.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import com.zly.www.easyrecyclerview.footer.ErvDefaultFooter;
import com.zly.www.easyrecyclerview.ptrlib.PtrClassicDefaultHeader;

/**
 * Created by yuliyan on 2017/4/14.
 */
public class DMEasyDefRecyclerVoew extends  DMEasyRecyclerView {


    private PtrClassicDefaultHeader mPtrClassicHeader;

    public DMEasyDefRecyclerVoew(Context context) {
        this(context, null);
    }

    public DMEasyDefRecyclerVoew(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DMEasyDefRecyclerVoew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setHeaderView(mPtrClassicHeader = new PtrClassicDefaultHeader(context));
        setFooterView(new ErvDefaultFooter(context));
        setLayoutManager(new LinearLayoutManager(getContext()));
        setOnEmptyViewClick(new OnClickListener() {
            @Override
            public void onClick(View v) {
                autoRefresh();
            }
        });
    }

    public void setLastUpdateTimeKey(String key) {
        if (mPtrClassicHeader != null) {
            mPtrClassicHeader.setLastUpdateTimeKey(key);
        }
    }

    public void setLastUpdateTimeRelateObject(Object object) {
        if (mPtrClassicHeader != null) {
            mPtrClassicHeader.setLastUpdateTimeRelateObject(object);
        }
    }
}
