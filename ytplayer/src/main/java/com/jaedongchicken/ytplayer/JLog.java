package com.jaedongchicken.ytplayer;

import android.util.Log;

public class JLog {

    public static final boolean DEBUG = true;
    
    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void v(String tag, String msg) {
        if (DEBUG) {
            Log.v(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUG) {
            Log.w(tag, msg);
        }
    }


    private static final String TAG = "JD";

    public static void d(String message) {
        if (DEBUG)
            Log.d(TAG, buildLogMsg(message));
    }

    public static void e(String message) {
        if (DEBUG)
            Log.e(TAG, buildLogMsg(message));
    }

    public static void i(String message) {
        if (DEBUG)
            Log.i(TAG, buildLogMsg(message));
    }

    public static void w(String message) {
        if (DEBUG)
            Log.w(TAG, buildLogMsg(message));
    }

    private static String buildLogMsg(String message) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[4];

        StringBuilder sb = new StringBuilder();

        sb.append("[");
        sb.append(ste.getFileName().replace(".java", ""));
        sb.append("::");
        sb.append(ste.getMethodName());
        sb.append("]");
        sb.append(message);

        return sb.toString();
    }
}