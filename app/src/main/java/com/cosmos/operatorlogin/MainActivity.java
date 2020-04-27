package com.cosmos.operatorlogin;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cosmos.authbase.AuthManagerConfig;
import com.cosmos.authbase.IAuth;
import com.cosmos.authbase.IOfferNumberListener;
import com.cosmos.authbase.ISPType;
import com.cosmos.authbase.ITokenListener;
import com.cosmos.authbase.UIConfig;
import com.cosmos.authlib.AuthManager;

public class MainActivity extends BaseActivity {
    private static final String CMCC_APPID = "";//移动（请在自己项目中替换该值）
    private static final String CMCC_APP_KEY = "";//（请在自己项目中替换该值）

    private static final String CUCC_APP_ID = "";//联通//（请在自己项目中替换该值）
    private static final String CUCC_APP_KEY = "";//（请在自己项目中替换该值）

    public static final String CTCC_APPID = "";  //请在天翼账号开放平台申请应用的APPID//（请在自己项目中替换该值）
    public static final String CTCC_APPSECRET = ""; //请在天翼账号开放平台申请应用的APPSECRET//（请在自己项目中替换该值）

    private static final int REQUEST_CODE = 194;
    private AuthManagerConfig authManagerConfig;
    private boolean offNum;
    private Handler handler;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setColor(this, getResources().getColor(R.color.bg_blue));
        setContentView(R.layout.activity_main);

        authManagerConfig = new AuthManagerConfig.Builder()
                .context(this)
                .cmccAppId(CMCC_APPID).cmccAppKey(CMCC_APP_KEY)
                .ctccAppId(CTCC_APPID).ctccAppKey(CTCC_APPSECRET)
                .cuccAppId(CUCC_APP_ID).cuccAppKey(CUCC_APP_KEY)
                .logOpen(true)
                .uiConfig(getUiConfig())
                .build();


        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE);
            return;
        }
        initAuth();
    }

    private void initAuth() {
        int returnType = AuthManager.getInstance().init(authManagerConfig);
        if (returnType == ISPType.TYPE_UNKNOW) {
            Toast.makeText(this, "初始化失败", Toast.LENGTH_SHORT).show();
        } else {
            AuthManager.getInstance().offerNumber(new IOfferNumberListener() {

                @Override
                public void onOfferNumberResult(final String errMsg) {
                    if (errMsg != null) {
                        if (handler == null) {
                            handler = new Handler();
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, String.format("offnum failed:", errMsg), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        offNum = true;
                    }
                }
            });
        }
    }

    private UIConfig getUiConfig() {
        // 如果不设置uiConfig，会使用运营商loginactivity的默认ui

//        // 配置移动的界面
        int[] btnSize = new int[]{300, 42};//登录按钮宽高
        return new UIConfig.Builder()
                .statusBarColor(0xffffffff)//状态栏颜色,电信暂不支持
                //导航栏
                .navColor(0xffffffff)//导航栏颜色
                .navText("")//导航栏文字
//                .navTextColor(0xff000000)
                .navReturnDrawable("arrow_left")//导航栏左上角图标
                .navTextSize(20)
                //logo
                .logoDrawable("main_top_icon")//登录logo
                .logoOffSet(100)
                .logoWidth(71)
                .logoHeight(71)
                .logoHidden(false)
                //取号结果
                .numberColor(0xff000000)//取号结果显示颜色
                .numberSize(30)//取号结果显示大小
                .numberFieldOffSetY(160)//取号结果相对标题栏下边缘偏移量
                //slogan
                .sloganOffSetY(240)//设置slogan相对于标题栏下边缘y偏移   ***提供认证服务
                //登录按钮
                .loginBtnImageDrawable("auth_btn_login_selector") //登录按钮background,传入drawable的名称
                .loginBtnSize(btnSize)//登录按钮大小
                .loginBtnOffSetY(260)//登录按钮相对标题栏下边缘偏移量
                .loginBtnTextSize(20)
                .loginBtnText("本机号码一键登录")
                .loginBtnTextColor(0xffffffff)
                //切换账号
                .switchAccHidden(false)
                .switchText("手机号登录")
                .switchTextColor(0xff8B8F93)
                .switchTextSize(14)
                .switchTextOffY(320)
                // 电信添加自定义view以及点击事件
                .addCtccCustomView(R.id.unicomQQLogin, new UIConfig.CustomOnClickListener() {

                    @Override
                    public void onClick() {
                        Toast.makeText(MainActivity.this, "qq onclick", Toast.LENGTH_SHORT).show();
                    }
                })
                .addCtccCustomView(R.id.unicomWeiXinLogin, new UIConfig.CustomOnClickListener() {

                    @Override
                    public void onClick() {
                        Toast.makeText(MainActivity.this, "weixin onclick", Toast.LENGTH_SHORT).show();
                    }
                })
                .addCtccCustomView(R.id.unicomWeiboLogin, new UIConfig.CustomOnClickListener() {

                    @Override
                    public void onClick() {
                        Toast.makeText(MainActivity.this, "weibo onclick", Toast.LENGTH_SHORT).show();
                    }
                })
                //联通添加自定义view以及点击事件
                //移动添加自定义view以及点击事件
                .addCusTomView(getCustomView(), "relative_item_view")
                //隐私
                .privacyCheckImg("privacy_checked")//移动联通需要这么设置
                .privacyUnCheckImg("privacy_uncheck")//移动联通需要这么设置
                .privacyCheckDrawable(R.drawable.privacy_check_drawable)//电信需要这么设置
                .privacyChecked(true)
                .privacyOffYBottom(30)
//                .clauseContent("自定义条款1")
//                .clauseUrl("http://a.b.c")
                .clauseContentTwo("自定义条款2")//移动联通支持第二个隐私条约
                .clauseUrlTwo("http://a.b.c.2")//移动联通支持第二个隐私条约
                .ctccClauseContent("电信独有协议")//电信支持
                .ctccClauseUrl("http://123.456")//电信支持
                .auxiliaryPrivacyWords(new String[]{"登录即同意", "和", "、", "并使用手机号登录"})
                .clauseDefaultContent("这就是电信条款哈哈哈哈哈")//电信支持
                .build();
//        return new UIConfig.Builder().build() ;
    }

    private View getCustomView() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LinearLayout relativeLayout = (LinearLayout) layoutInflater.inflate(R.layout.relative_item_view, null);
        RelativeLayout.LayoutParams layoutParamsOther = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsOther.setMargins(0, Utils.dip2px(this, 340), 0, 0);
        layoutParamsOther.addRule(RelativeLayout.CENTER_HORIZONTAL);
        relativeLayout.setLayoutParams(layoutParamsOther);
        ImageView weixin = relativeLayout.findViewById(R.id.unicomWeiXinLogin);
        ImageView qq = relativeLayout.findViewById(R.id.unicomQQLogin);
        ImageView weibo = relativeLayout.findViewById(R.id.unicomWeiboLogin);
//        TextView phone = relativeLayout.findViewById(R.id.phone);
        weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "微信", Toast.LENGTH_SHORT).show();
            }
        });
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "qq", Toast.LENGTH_SHORT).show();
            }
        });
        weibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "微博", Toast.LENGTH_SHORT).show();
            }
        });
        return relativeLayout;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initAuth();
            } else {
                Toast.makeText(this, "没有权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onLoginClick(View view) {
        if (!offNum) {
            Toast.makeText(this, "预取号未成功", Toast.LENGTH_SHORT).show();
            return;
        }
        AuthManager.getInstance().openLoginAuth(this, new ITokenListener() {
            @Override
            public void onTokenResult(String errorMsg) {
                AuthManager.getInstance().closeAuthActivity();
                if (errorMsg == null) {
                    GetUserInfoHelper.getInstance().getUserInfo(new IGetUserInfoListener() {
                        @Override
                        public void onUserInfoResult(String mobileNum, String errMsg) {
                            Toast.makeText(MainActivity.this, String.format("用户信息%s", mobileNum != null ? ("成功:" + mobileNum) : "失败：" + errMsg), Toast.LENGTH_SHORT).show();
                            if (!TextUtils.isEmpty(mobileNum)) {
                                LoginResultActivity.start(MainActivity.this, mobileNum);
                            } else {
                                LoginResultActivity.start(MainActivity.this, null);
                            }
                        }
                    }, AuthManager.getInstance().getRequestBodyMap(), AuthManager.getInstance().getRequestHeaderMap());
                } else {
                    if (errorMsg.equals(IAuth.LOGIN_PHONE_LOGIN)) {
                        Intent intent = new Intent(MainActivity.this, PhoneNumLoginActivity.class);
                        startActivity(intent);
                    } else if (errorMsg.equals(IAuth.LOGIN_CANCEL)) {
                        Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                    } else if (errorMsg.equals(IAuth.LOGIN_SWITCH_ACCOUNT)) {
                        Intent intent = new Intent(MainActivity.this, PhoneNumLoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
                        LoginResultActivity.start(MainActivity.this, null);
                    }
                }
            }

        });
    }
}
