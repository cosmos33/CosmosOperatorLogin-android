package com.cosmos.operatorlogin;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;

import com.cosmos.operatorlogin.netutils.HttpUtils;
import com.cosmos.operatorlogin.netutils.JsonResult;

import java.util.Map;

public class GetUserInfoHelper {

    public static class GetUserInfoHelperHolder{
        static GetUserInfoHelper getUserInfoHelper = new GetUserInfoHelper();
    }
    public static GetUserInfoHelper getInstance() {
        return GetUserInfoHelperHolder.getUserInfoHelper;
    }
    private Handler mainHandler;
    private GetUserInfoHelper() {
        mainHandler = new android.os.Handler(Looper.getMainLooper());
    }

    public void getUserInfo(final IGetUserInfoListener iGetUserInfoListener,
                            final Map<String, String> requestMap,
                            final Map<String, String> headerMap) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JsonResult<JsonUserInfo> userInfo = HttpUtils.getInstance().getUserInfo(Constant.URL_BASE, requestMap, headerMap,
                        JsonUserInfo.class);
                JsonUserInfo jsonUserInfo = userInfo.get();
                if (jsonUserInfo != null) {
                    if (userInfo.success()) {
                        callUserInfoListener(iGetUserInfoListener, jsonUserInfo.getData().getMobile(), null);
                    } else {
                        callUserInfoListener(iGetUserInfoListener, null, jsonUserInfo.getEm());
                    }
                } else {
                    callUserInfoListener(iGetUserInfoListener, null, userInfo.getHttpErrorCode() + "");
                }
            }
        }).start();
    }

    private void callUserInfoListener(final IGetUserInfoListener iGetUserInfoListener, final String mobile, final String msg) {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                if (iGetUserInfoListener != null) {
                    iGetUserInfoListener.onUserInfoResult(mobile, msg);
                }
            }
        });
    }
}
