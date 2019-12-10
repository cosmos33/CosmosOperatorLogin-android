### 一键登录SDK接入文档

#### 接入前准备
请在 [移动](http://dev.10086.cn), [电信](https://id.189.cn/) , [联通](https://saas.wostore.cn/index.html) 运营商分别注册app信息以及开通一键登录功能。


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

Android 4.0

- 添加工程依赖
```
implementation "com.cosmos.auth:authlib:1.0.6"
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
-keep class com.cosmos.authlib.AuthManager {*;}

-keep class com.cosmos.authbase.ISPType{*;}
-keep class com.cosmos.authbase.UIConfig{*;}
-keep class com.cosmos.authbase.UIConfig.Builder{*;}

#移动
-dontwarn com.cmic.sso.sdk.**
-keep class com.cmic.sso.sdk.** {*;}
#电信
-keep class cn.com.chinatelecom.account.**{*;}
#联通
-dontwarn com.unicom.xiaowo.account.shield.**
-keep class com.unicom.xiaowo.account.shield.**{*;}

```
#### 功能接入

具体使用可以参考 [demo](https://github.com/cosmos33/CosmosOperatorLogin-android)

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
- 电信授权页添加自定义view需要添加以下xml文件
```
ctcc_customview1.xml
ctcc_customview2.xml
ctcc_customview3.xml
```
设置状态栏颜色
```
/**
 * @param val 设置状态栏颜色
 * @return
 */
public Builder statusBarColor(int val);
```
设置导航栏颜色
```
/**
 * @param val 设置导航栏颜色
 * @return
 */
public Builder navColor(int val);
```
导航栏左上角图标,传入drawable的名称
```
/**
 * @param val 导航栏左上角图标,传入drawable的名称
 * @return
 */
public Builder navReturnDrawable(String val);
```
登录logo,传入drawable的名称
```
/**
 * @param val 登录logo,传入drawable的名称
 * @return
 */
public Builder logoDrawable(String val);
```
登录logo距离标题栏下边缘偏移量
```
/**
 * @param val 登录logo距离标题栏下边缘偏移量
 * @return
 */
public Builder logoOffSet(int val);
```
是否隐藏“切换账号"
```
/**
 * @param val 是否隐藏“切换账号"
 * @return
 */
public Builder switchAccHidden(boolean val);
```
切换账号文字颜色
```
/**
 * @param val 切换账号文字颜色
 * @return
 */
public Builder switchTextColor(int val);
```
切换账号文字大小
```
/**
 * @param val 切换账号文字大小
 * @return
 */
public Builder switchTextSize(int val);
```
切换账号距离标题栏下边缘偏移量
```
/**
 * @param val 切换账号距离标题栏下边缘偏移量
 * @return
 */
public Builder switchTextOffY(int val);
```
切换账号文本内容
```
/**
 * @param val 切换账号文本内容
 * @return
 */
public Builder switchText(String val);
```
登录按钮background,传入drawable的名称
```
/**
 * @param val 登录按钮background,传入drawable的名称
 * @return
 */
public Builder loginBtnImageDrawable(String val);
```
登录按钮大小
```
/**
 * @param val 登录按钮大小
 * @return
 */
public Builder loginBtnSize(int[] val);
```
登录按钮文字颜色
```
/**
 * @param val 登录按钮文字颜色
 * @return
 */
public Builder loginBtnTextColor(int val);
```
登录按钮文字大小
```
/**
 * @param val 登录按钮文字大小
 * @return
 */
public Builder loginBtnTextSize(int val) {
    loginBtnTextSize = val;
    return this;
}
```
登录按钮文本内容
```
/**
 * @param val 登录按钮文本内容
 * @return
 */
public Builder loginBtnText(String val);
```
取号结果显示颜色
```
/**
 * @param val 取号结果显示颜色
 * @return
 */
public Builder numberColor(int val);
```
取号结果显示大小
```
/**
 * @param val 取号结果显示大小
 * @return
 */
public Builder numberSize(int val);
```
取号结果相对标题栏下边缘偏移量
```
/**
 * @param val 取号结果相对标题栏下边缘偏移量
 * @return
 */
public Builder numberFieldOffSetY(int val);
```
登录按钮相对标题栏下边缘偏移量
```
/**
 * @param val 登录按钮相对标题栏下边缘偏移量
 * @return
 */
public Builder loginBtnOffSetY(int val);
```
设置slogan相对于标题栏下边缘y偏移
```
/**
 * @param val 设置slogan相对于标题栏下边缘y偏移
 * @return
 */
public Builder sloganOffSetY(int val);
```
导航栏文字
```
/**
 * @param val 导航栏文字
 * @return
 */
public Builder navText(String val);
```
电信添加自定义view以及点击事件,最多支持3个
```

/**
 * 电信添加自定义view以及点击事件,最多支持3个
 *
 * @param viewId          ct_account_auth_activity.xml中自定义view的id
 * @param onClickListener 点击事件
 * @return
 */
public Builder addCtccCustomView(int viewId, CustomOnClickListener onClickListener);
```
联通、移动添加自定义view以及点击事件
```

/**
 * @param customView 联通、移动添加自定义view以及点击事件
 * @param viewId view的id
 * @return
 */
public Builder addCusTomView(View customView, String viewId);
```
logo宽度
```
/**
 * @param i logo宽度
 * @return
 */
public Builder logoWidth(int i);
```
logo高度
```
/**
 * @param i logo高度
 * @return
 */
public Builder logoHeight(int i);

```
导航栏文字颜色
```
/**
 * @param navTextColor 导航栏文字颜色
 * @return
 */
public Builder navTextColor(int navTextColor);
```
导航栏文字大小
```

/**
 * @param val 导航栏文字大小
 * @return
 */
public Builder navTextSize(int val);
```
logo是否隐藏
```

/**
 * @param val logo是否隐藏
 * @return
 */
public Builder logoHidden(boolean val);
```
隐私协议距离底部距离
```
/**
 * @param val 隐私协议距离底部距离
 * @return
 */
public Builder privacyOffYBottom(int val);
```
隐私协议选择匡未选中图标（移动、联通）
```
/**
 * @param val 隐私协议选择匡未选中图标（移动、联通）
 * @return
 */
public Builder privacyUnCheckImg(String val);
```
 隐私协议选择匡选中图标（移动、联通）
```
/**
 * @param val 隐私协议选择匡选中图标（移动、联通）
 * @return
 */
public Builder privacyCheckImg(String val);
```
隐私协议选择匡drawable图标（电信）
```
/**
 * @param val 隐私协议选择匡drawable图标（电信）
 * @return
 */
public Builder privacyCheckDrawable(int val);
```
隐私协议选择匡大小（宽、高）
```
/**
 * @param val 隐私协议选择匡大小（宽、高）
 * @return
 */
public Builder privacyCheckSize(int[] val);
```
隐私协议选择匡默认是否选中
```
/**
 * @param val 隐私协议选择匡默认是否选中
 * @return
 */
public Builder privacyChecked(boolean val);
```
隐私协议文本颜色
```
/**
 * @param val 隐私协议文本颜色
 * @return
 */
public Builder privacyTextColor(int val);
```
隐私协议文本大小
```
/**
 * @param val 隐私协议文本大小
 * @return
 */
public Builder privacyTextSize(int val);
```
自定义条款内容
```
/**
 * @param val 自定义条款内容
 * @return
 */
public Builder clauseContent(String val);
```
自定义条款url
```
/**
 * @param val 自定义条款url
 * @return
 */
public Builder clauseUrl(String val);
```
运营商条款颜色
```
/**
 * @param val 运营商条款颜色
 * @return
 */
public Builder operatorClauseColor(int val)p;
```
自定义条款颜色
```
/**
 * @param val 自定义条款颜色
 * @return
 */
public Builder customClauseColor(int val);
```
