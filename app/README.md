
### SDK接入文档

#### 接入前准备
请在 [移动](dev.10086.cn), [电信](https://id.189.cn/) , [联通](https://saas.wostore.cn/index.html) 运营商分别注册app信息以及开通一键登录功能。
注册完毕请将各个运营商的appid以及appkey,填入MainActivity中，在build.gradle中添加打包信息

#### 工程配置
- 权限配置

接入SDK需要如下权限，将如下代码copy到主app的AndroidManifest.xml对应位置
```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
<uses-permission android:name="android.permission.WRITE_SETTINGS" />
<uses-permission android:name="android.permission.GET_TASKS" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
<uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
```
- 最小支持版本

Android4.0

- 添加工程依赖
```
```
- so架构
SDK目前提供了armeabi,armeabi-v7a,arm64-v8a,x86,x86_64架构，请在app/build.gradle文件中**根据需要**配置代码。在android/defaultConfig/结构下添加
```
ndk {
    abiFilters "armeabi-v7a","armeabi","arm64-v8a","x86","x86_64"
}
```
- 混淆配置

```
-dontwarn com.cmic.sso.sdk.**
-keep class com.cmic.sso.sdk.** {*;}
-keep class cn.com.chinatelecom.account.**{*;}
-dontwarn com.unicom.xiaowo.account.shield.** 
-keep class com.unicom.xiaowo.account.shield.**{*;}
```
#### 功能接入

具体使用可以参考 [demo]()

- 初始化
```
/**
 * 初始化sdk,返回识别的运营商类型.建议在Application 或 Activity 的 onCreate()调用;不建议初始化与取号接口同时调用
 * @param authManagerConfig
 * @return 返回运营商类别 {@link ISPType#TYPE_UNKNOW,ISPType#TYPE_CMCC,ISPType#TYPE_CTCC,ISPType#TYPE_CUCC}
 */
@Override
public int init(AuthManagerConfig authManagerConfig)
```
- 取号
```
/**
 * 取号
 * @param onOfferNumberResultListener 取号回调
 */
@Override
public void offerNumber(IOfferNumberListener onOfferNumberResultListener) 
```
- 唤出授权页
```
/**
 * 唤出授权页
 * @param iTokenListener 唤出授权页点击登录的回调
 */
@Override
public void login(ITokenListener iTokenListener)
```
- 获取用户信息
```
/**
 * 获取用户信息(手机号)
 * @param iGetUserInfoListener 获取信息的回调
 */
@Override
public void getUserInfo(IGetUserInfoListener iGetUserInfoListener)
```
- 关闭授权页
```
/**
 * 关闭授权界面
 */
@Override
public void closeAuthActivity()
```
##### 授权页自定义view相关（UIConfig）
- 联通自定义view需要修改以下xml文件
```
unicomsdk_loading.xml
unicomsdk_login_button.xml
unicomsdk_progressbar.xml
unicomsdk_quick_login.xml
unicomsdk_service_agreement.xml
unicomsdk_title.xml
``` 

- 电信自定义view需要修改以下xml文件
```
ct_account_auth_activity.xml
```
- 移动自定义view需要配置UIConfig以下内容

设置授权页状态栏颜色
```
/**
 * @param val 状态栏颜色，目前只有移动支持
 * @return
 */
public Builder statusBarColor(int val)
```
设置授权页导航栏颜色

```
/**
 * @param val 导航栏颜色
 * @return
 */
public Builder navColor(int val)
```
设置授权页导航栏左上角图标

```
/**
 * @param val 导航栏左上角图标,传入drawable的名称
 * @return
 */
public Builder navReturnDrawable(String val)
```
设置授权页登录logo

```
/**
 * @param val 登录logo,传入drawable的名称
 * @return
 */
public Builder logoDrawable(String val)
```
设置授权页是否隐藏“切换账号

```
/**
 * @param val 是否隐藏“切换账号
 * @return
 */
public Builder switchAccHidden(boolean val)
```
设置授权页登录按钮background

```
/**
 * @param val 登录按钮background,传入drawable的名称
 * @return
 */
public Builder loginBtnImageDrawable(String val)
```
设置授权页登录按钮大小

```
/**
 * @param val 登录按钮大小
 * @return
 */
public Builder loginBtnSize(Size val)
```
设置授权页取号结果显示颜色

```
/**
 * @param val 取号结果显示颜色
 * @return
 */
public Builder numberColor(int val)
```
设置授权页取号结果显示大小

```
/**
 * @param val 取号结果显示大小
 * @return
 */
public Builder numberSize(int val)
```
设置授权页取号结果相对标题栏下边缘偏移量

```
/**
 * @param val 取号结果相对标题栏下边缘偏移量
 * @return
 */
public Builder numberFieldOffSetY(int val)
```
设置授权页登录按钮相对标题栏下边缘偏移量

```
 /**
 * @param val 登录按钮相对标题栏下边缘偏移量
 * @return
 */
public Builder loginBtnOffSetY(int val)
```
设置授权页设置slogan相对于标题栏下边缘y偏移

```

/**
 * @param val 设置slogan相对于标题栏下边缘y偏移
 * @return
 */
public Builder sloganOffSetY(int val)
```
设置授权页导航栏左上角文字

```
/**
 * @param val 导航栏左上角文字
 * @return
 */
public Builder navText(String val)
```