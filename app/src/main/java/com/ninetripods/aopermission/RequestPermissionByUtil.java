package com.ninetripods.aopermission;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.ninetripods.aopermission.permissionlib.annotation.NeedPermission;
import com.ninetripods.aopermission.permissionlib.annotation.PermissionCanceled;
import com.ninetripods.aopermission.permissionlib.annotation.PermissionDenied;
import com.ninetripods.aopermission.permissionlib.bean.CancelBean;
import com.ninetripods.aopermission.permissionlib.bean.DenyBean;
import com.ninetripods.aopermission.permissionlib.util.SettingUtil;


/**
 * Created by mq on 2019-06-11 19:58
 * mqcoder90@gmail.com
 */
public class RequestPermissionByUtil {


    @NeedPermission(value = {Manifest.permission.CAMERA})
    public void requestPermission(Context context) {
        Toast.makeText(context, "相机权限申请成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 权限被取消
     *
     * @param bean CancelBean
     */
    @PermissionCanceled
    public void dealCancelPermission(CancelBean bean) {
        Toast.makeText(bean.getContext(), "相机权限申请被取消，请求码 :" + bean.getRequestCode(), Toast.LENGTH_SHORT).show();
    }

    /**
     * 权限被拒绝
     *
     * @param denyBean DenyBean
     */
    @PermissionDenied
    public void dealDeniedPermission(DenyBean denyBean) {

        final Context context = denyBean.getContext();

        new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage("相机权限被禁止，需要手动打开")
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        SettingUtil.go2Setting(context);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();

    }


}
