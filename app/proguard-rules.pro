# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#demo
-keep class com.cosmos.operatorlogin.JsonUserInfo{*;}




# sdk
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
