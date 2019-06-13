# Aopermission
#### AOP方式封装的6.0运行时申请权限的库—A library that request Permissions in an AOP manner.

- BLOG地址：https://blog.csdn.net/u013700502/article/details/79748829

- 点此查看效果图：
https://upload-images.jianshu.io/upload_images/587163-78cf7c69a1e04c66.gif?imageMogr2/auto-orient/strip

![downloadQR.png](https://upload-images.jianshu.io/upload_images/587163-406bfe5d806d0b63.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
扫描二维码或[点此下载APK](https://www.pgyer.com/6ThQ)：


## 更新日志
#### 2019-06-13
```
1、jcenter升级到1.2.0
2、修改8.0系统上透明activity引起的bug
3、targetSDK升级到28 
4、支持在util类中的非静态方法中申请权限(方法中的第一个参数传入context，如果参数为空，默认使用的是application)
```

# UML Sequence Chart(UML时序图)

![UML时序图.png](https://upload-images.jianshu.io/upload_images/587163-2e0308c1dc5faaab.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# How to use

## 一、配置
1、权限库引入方式，在**app模块的build.gradle**中引入如下：
```
apply plugin: 'android-aspectjx'

dependencies {
     compile 'com.ninetripods:aop-permission:1.2.0'
     ..........其他............
}

//可选配置：include和exclude的规则是去匹配包名，如果找到匹配的包名，则整个jar(即整个库)生效，
//这样做主要是考虑到性能的问题。
aspectjx {
    include 'com.ninetripods','使用注解所在的包名'
}

```
点此查看最新版本：https://jcenter.bintray.com/com/ninetripods/aop-permission/

2、在**根目录的build.gradle**里面配置如下：
```
dependencies {
    classpath 'com.android.tools.build:gradle:3.0.1'//替换你的gradle版本
    classpath 'com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.4'
    ................其他................
}
```
说明：**aspectjx:2.0.4目前是最新版本，aspectjx历史版本查看地址：**
https://github.com/HujiangTechnology/gradle_plugin_android_aspectjx/blob/master/CHANGELOG.md

3、本项目中在AOP类中用到了反射，如果你的项目中在混淆后导致权限申请失败，将下面的配置加到你的混淆配置中:
```
-keepclasseswithmembers class * {
    @com.ninetripods.aopermission.permissionlib.annotation.NeedPermission <methods>;
}

-keepclasseswithmembers class * {
    @com.ninetripods.aopermission.permissionlib.annotation.PermissionCanceled <methods>;
}

-keepclasseswithmembers class * {
    @com.ninetripods.aopermission.permissionlib.annotation.PermissionDenied <methods>;
}
```

## 二、使用举例
### 1、申请单个权限
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

### 2、申请多个权限

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

