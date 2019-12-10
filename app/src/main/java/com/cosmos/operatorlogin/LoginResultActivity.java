package com.cosmos.operatorlogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginResultActivity extends BaseActivity {
    private static final String EXTRA_LOGINRESULT = "EXTRA_LOGINRESULT";
    private ImageView ivLoginResult;
    private TextView tvLoginResult;
    private TextView tvTip;
    private String phoneNum;

    public static void start(Activity context,String phoneNum) {
        Intent intent = new Intent(context, LoginResultActivity.class);
        intent.putExtra(EXTRA_LOGINRESULT,phoneNum);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_result);

        initView();
        phoneNum = getIntent().getStringExtra(EXTRA_LOGINRESULT);
        init();
    }

    @Override
    public int getStatusColor() {
        return R.color.white;
    }

    private void initView() {
        ivLoginResult = findViewById(R.id.ivLoginResult);
        tvLoginResult = findViewById(R.id.tvLoginResult);
        tvTip = findViewById(R.id.tvTip);
    }

    private void init() {
        if (!TextUtils.isEmpty(phoneNum)) {
            ivLoginResult.setImageResource(R.drawable.login_success);
            tvLoginResult.setText("登录成功");
            tvTip.setText(String.format("手机号码为:%s",phoneNum));
        } else {
            ivLoginResult.setImageResource(R.drawable.login_faild);
            tvLoginResult.setText("登录失败");
            tvTip.setVisibility(View.INVISIBLE);
        }
    }

    public void onReturnClick(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
