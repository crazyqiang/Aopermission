package com.ninetripods.aopermission.permissionlib.bean;

import java.util.List;

/**
 * Created by mq on 2018/3/28 下午5:19
 * mqcoder90@gmail.com
 */

public class DenyBean {

    private int requestCode;
    private List<String> denyList;

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public List<String> getDenyList() {
        return denyList;
    }

    public void setDenyList(List<String> denyList) {
        this.denyList = denyList;
    }
}
