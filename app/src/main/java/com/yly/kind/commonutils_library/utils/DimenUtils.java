package com.yly.kind.commonutils_library.utils;

import android.content.Context;

/**
 * Created by lielvwang on 2017/2/15.
 */

public class DimenUtils {

    public static int dip2px(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }
}
