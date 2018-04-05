# Aopermission
A library that request Permissions in an AOP manner.

BLOG地址：https://blog.csdn.net/u013700502/article/details/79748829

点此查看效果图：https://upload-images.jianshu.io/upload_images/587163-78cf7c69a1e04c66.gif?imageMogr2/auto-orient/strip

![downloadQR.png](https://upload-images.jianshu.io/upload_images/587163-406bfe5d806d0b63.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
扫描二维码或[点此下载APK](https://www.pgyer.com/6ThQ)：

# UML Sequence Chart

![UML时序图.png](https://upload-images.jianshu.io/upload_images/587163-2e0308c1dc5faaab.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# How to use
1、权限库引入方式，在app模块的build.gradle中引入如下：
```
apply plugin: 'android-aspectjx'

dependencies {
     compile 'com.ninetripods:aop-permission:1.0.1'
     ..........其他............
}
```
2、在整个工程的build.gradle里面配置如下：
```
dependencies {
    classpath 'com.android.tools.build:gradle:2.3.3'
    classpath 'com.hujiang.aspectjx:gradle-android-plugin-aspectjx:1.0.8'
    ................其他................
}
```
说明：**aspectjx:1.0.8不是最新版本，最高支持gradle的版本到2.3.3，如果你的工程里gradle版本是3.0.0以上，请使用aspectjx：1.1.0以上版本，aspectjx历史版本查看地址：**
https://github.com/HujiangTechnology/gradle_plugin_android_aspectjx/blob/master/CHANGELOG.md

3、被@NeedPermission注解作用的类及方法名不能被混淆,需要在混淆配置里keep住, 比如:
```
package com.hujiang.test;

public class A {
    @NeedPermission
    public boolean funcA(String args) {
        ....
    }
}

-keep class com.hujiang.test {*;}
```

## 申请单个权限
申请单个权限：
```
btn_click.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        callMap();
    }
});

/**
 * 申请权限
 */
@NeedPermission(value = {Manifest.permission.ACCESS_FINE_LOCATION}, requestCode = 0)
private void callMap() {
    Toast.makeText(this, "定位权限申请通过", Toast.LENGTH_SHORT).show();
}
```
@NeedPermission后面的value代表需要申请的权限，是一个String[]数组；requestCode是请求码，是为了区别开同一个Activity中有多个不同的权限请求，默认是0，如果同一个Activity中只有一个权限申请，requestCode可以忽略不写。

```
/**
 * 权限被取消
 *
 * @param bean CancelBean
 */
@PermissionCanceled
public void dealCancelPermission(CancelBean bean) {
    Toast.makeText(this, "requestCode:" + bean.getRequestCode(), Toast.LENGTH_SHORT).show();
}
```
声明一个public方法接收权限被取消的回调，**方法必须有一个CancelBean类型的参数**，这点类似于EventBus，CancelBean中有requestCode变量，即是我们请求权限时的请求码。
```
/**
 * 权限被拒绝
 *
 * @param bean DenyBean
 */
@PermissionDenied
public void dealPermission(DenyBean bean) {
        Toast.makeText(this, 
        "requestCode:" + bean.getRequestCode()+ ",Permissions: " + Arrays.toString(bean.getDenyList().toArray()), Toast.LENGTH_SHORT).show();
  }
```
声明一个public方法接收权限被取消的回调，**方法必须有一个DenyBean类型的参数**，DenyBean中有一个requestCode变量，即是我们请求权限时的请求码，另外还可以通过denyBean.getDenyList()来拿到被权限被拒绝的List。

## 申请多个权限

基本用法同上，区别是@NeedPermission后面声明的权限是多个，如下：
```
/**
 * 申请多个权限
 */
@NeedPermission(value = {Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA}, requestCode = 10)
public void callPhone() {
    Toast.makeText(this, "电话、相机权限申请通过", Toast.LENGTH_SHORT).show();
}
```
value中声明了两个权限，一个电话权限，一个相机权限

## Thanks To
https://github.com/HujiangTechnology/gradle_plugin_android_aspectjx
