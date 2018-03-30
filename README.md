# Aopermission
A library that request Permissions in an AOP manner.

BLOG地址：https://blog.csdn.net/u013700502/article/details/79748829

![运行效果图.gif](https://upload-images.jianshu.io/upload_images/587163-78cf7c69a1e04c66.gif?imageMogr2/auto-orient/strip)

# How to use
1、权限库引入方式，在app模块的build.gradle中引入如下：
```
apply plugin: 'android-aspectjx'

dependencies {
     
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

3、需要在AOP代码进行hook的类及方法名不能被混淆,需要在混淆配置里keep住, 比如:
```
package com.hujiang.test;

public class A {
    public boolean funcA(String args) {
        ....
    }
}

//如果你在AOP代码里对A#funcA(String)进行hook, 那么在混淆配置文件里加上这样的配置

-keep class com.hujiang.test.A {*;}
```
4、终于配好了，都闪开，我要开始举栗子了：
![举栗子.jpg](https://upload-images.jianshu.io/upload_images/587163-708162abf2b0da37.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
下面以Activity中申请权限为例，Fragment、Service中使用是一样的，就不一一写了，源码中也有相应使用的Demo

###  4.1 申请单个权限
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

###  4.2 申请多个权限

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
