package com.ninetripods.aopermission.permissionlib.interf;

import java.util.List;

/**
 * Created by mq on 2018/3/6 下午2:52
 * mqcoder90@gmail.com
 */

public interface IPermission {

    //同意权限
    void PermissionGranted();

    //拒绝权限并且选中不再提示
    void PermissionDenied(int requestCode, List<String> denyList);

    //取消权限
    void PermissionCanceled(int requestCode);
}
