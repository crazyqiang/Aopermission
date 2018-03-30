package com.ninetripods.aopermission;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ninetripods.aopermission.permissionlib.annotation.NeedPermission;
import com.ninetripods.aopermission.permissionlib.annotation.PermissionCanceled;
import com.ninetripods.aopermission.permissionlib.annotation.PermissionDenied;
import com.ninetripods.aopermission.permissionlib.bean.CancelBean;
import com.ninetripods.aopermission.permissionlib.bean.DenyBean;
import com.ninetripods.aopermission.permissionlib.util.SettingUtil;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Button btn_click;
    private Button btn_click1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initEvents();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_container, new PermissionFragment());
        transaction.commitAllowingStateLoss();
    }

    private void initViews() {
        btn_click = (Button) findViewById(R.id.btn_click);
        btn_click1 = (Button) findViewById(R.id.btn_click1);
    }


    private void initEvents() {
        btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhone();
            }
        });
        btn_click1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMap();
            }
        });
    }

    /**
     * 申请多个权限
     */
    @NeedPermission(value = {Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA}, requestCode = 10)
    public void callPhone() {
        Toast.makeText(this, "电话、相机权限申请通过", Toast.LENGTH_SHORT).show();
    }

    /**
     * 申请单个权限
     */
    @NeedPermission(value = {Manifest.permission.ACCESS_FINE_LOCATION}, requestCode = 0)
    private void callMap() {
        Toast.makeText(this, "定位权限申请通过", Toast.LENGTH_SHORT).show();
    }

    /**
     * 权限被拒绝
     *
     * @param bean DenyBean
     */
    @PermissionDenied
    public void dealPermission(DenyBean bean) {
        if (bean == null) return;
        Toast.makeText(this, "requestCode:" + bean.getRequestCode()
                + ",Permissions: " + Arrays.toString(bean.getDenyList().toArray()), Toast.LENGTH_SHORT).show();
        List<String> denyList = bean.getDenyList();
        switch (bean.getRequestCode()) {
            case 10:
                //多个权限申请返回结果
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < denyList.size(); i++) {
                    if (Manifest.permission.CALL_PHONE.equals(denyList.get(i))) {
                        builder.append("电话");
                    } else if (Manifest.permission.CAMERA.equals(denyList.get(i))) {
                        builder.append("相机");
                    }
                }
                builder.append("权限被禁止，需要手动打开");
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("提示")
                        .setMessage(builder)
                        .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                SettingUtil.go2Setting(MainActivity.this);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();

                break;
            case 0:
                //单个权限申请返回结果
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("提示")
                        .setMessage("定位权限被禁止，需要手动打开")
                        .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                SettingUtil.go2Setting(MainActivity.this);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
                break;
            default:
                break;
        }
    }

    /**
     * 权限被取消
     *
     * @param bean CancelBean
     */
    @PermissionCanceled
    public void dealCancelPermission(CancelBean bean) {
        Toast.makeText(this, "requestCode:" + bean.getRequestCode(), Toast.LENGTH_SHORT).show();
    }

    /**
     * 在Service中申请权限
     *
     * @param view
     */
    public void startPermissionService(View view) {
        Intent intent = new Intent(this, PermissionService.class);
        startService(intent);
    }

}
