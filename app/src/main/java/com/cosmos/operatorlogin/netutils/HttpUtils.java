package com.cosmos.operatorlogin.netutils;

import java.util.Map;

public class HttpUtils {
    private static final HttpUtils ourInstance = new HttpUtils();

    public static HttpUtils getInstance() {
        return ourInstance;
    }

    private IHttpHelper iHttpHelper;

    private HttpUtils() {
        iHttpHelper = new OkHttpHelper();
    }

    public <T extends JsonRequestResult> JsonResult<T> getUserInfo(String url,
                                                                   Map<String, String> formBodyMap,
                                                                   Map<String, String> headerMap,
                                                                   Class<T> jsonClass) {
        return iHttpHelper.post(url, formBodyMap, headerMap, jsonClass);
    }
}
