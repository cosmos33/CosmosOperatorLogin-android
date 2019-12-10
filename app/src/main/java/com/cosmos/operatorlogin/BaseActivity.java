package com.cosmos.operatorlogin;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setColor(this, getResources().getColor(getStatusColor()));
    }

    public int getStatusColor() {
        return R.color.bg_blue;
    }
}
