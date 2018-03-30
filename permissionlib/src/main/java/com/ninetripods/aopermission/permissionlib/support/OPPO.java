package com.ninetripods.aopermission.permissionlib.support;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.ninetripods.aopermission.permissionlib.interf.ISetting;

/**
 * Created by mq on 2018/3/6 上午11:44
 * mqcoder90@gmail.com
 */

public class OPPO implements ISetting {

    private Context context;

    public OPPO(Context context) {
        this.context = context;
    }

    @Override
    public Intent getSetting() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        return intent;
    }
}
