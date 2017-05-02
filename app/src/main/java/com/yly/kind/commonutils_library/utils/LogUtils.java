package com.yly.kind.commonutils_library.utils;


/**
 * log工具类
 *
 * @author Administrator
 */
public class LogUtils {
    private static boolean DEBUG = true;

    public static void setDebug(boolean isDebug) {
        DEBUG = isDebug;
    }

    public static void v(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.v(tag, msg);
        }
    }


    public static void v(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.v(tag, msg, tr);
        }
    }

    public static void v(Object tag, String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.v(tag.getClass().getSimpleName(), msg, tr);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.d(tag, msg);
        }
    }

    public static void d(Object tag, String msg) {
        if (DEBUG) {
            android.util.Log.d(tag.getClass().getSimpleName(), msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.d(tag, msg, tr);
        }
    }

    public static void d(Object tag, String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.d(tag.getClass().getSimpleName(), msg, tr);
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.i(tag, msg);
        }
    }

    public static void i(Object tag, String msg) {
        if (DEBUG) {
            android.util.Log.i(tag.getClass().getSimpleName(), msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.i(tag, msg, tr);
        }
    }

    public static void i(Object tag, String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.i(tag.getClass().getSimpleName(), msg, tr);
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.w(tag, msg);
        }
    }

    public static void w(Object tag, String msg) {
        if (DEBUG) {
            android.util.Log.w(tag.getClass().getSimpleName(), msg);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.w(tag, msg, tr);
        }
    }

    public static void w(Object tag, String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.w(tag.getClass().getSimpleName(), msg, tr);
        }
    }

    public static void w(String tag, Throwable tr) {
        if (DEBUG) {
            android.util.Log.w(tag, tr);
        }
    }

    public static void w(Object tag, Throwable tr) {
        if (DEBUG) {
            android.util.Log.w(tag.getClass().getSimpleName(), tr);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.e(tag, msg);
        }
    }

    public static void e(Object tag, String msg) {
        if (DEBUG) {
            android.util.Log.e(tag.getClass().getSimpleName(), msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.e(tag, msg, tr);
        }
    }

    public static void e(Object tag, String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.e(tag.getClass().getSimpleName(), msg, tr);
        }
    }
}
