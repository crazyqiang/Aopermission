package com.ninetripods.aopermission.permissionlib.bean;

import android.content.Context;

/**
 * Created by mq on 2018/3/28 下午8:36
 * mqcoder90@gmail.com
 */

public class CancelBean {
    private int requestCode;
    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }
}
