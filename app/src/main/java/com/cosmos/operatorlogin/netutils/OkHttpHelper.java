package com.cosmos.operatorlogin.netutils;

import com.cosmos.authbase.LogHelper;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;

public class OkHttpHelper implements IHttpHelper {

    private OkHttpClient okHttpClient;

    public OkHttpHelper() {
//        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new GetDataFromServer.HttpLogger());
//        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.sslSocketFactory(new TlsSniSocketFactory("cosmos-im-demo.immomo.com"), new com.cosmos.photonim.imbase.utils.http.SSLUtil.TrustAllManager())
//                .hostnameVerifier(new com.cosmos.photonim.imbase.utils.http.TrueHostnameVerifier("cosmos-im-demo.immomo.com"));
        okHttpClient = builder
//                .addNetworkInterceptor(logInterceptor)
                .build();
    }

    private <T extends JsonRequestResult> JsonResult<T> postInner(String url, Map<String, String> formBodyMap,
                                                                  Map<String, String> headerMap, Class<T> jsonClass) {
        Request request = getRequest(url, formBodyMap, headerMap);
        final Response[] response = new Response[1];
        String responseBody;
        JsonResult<T> jsonResult = new JsonResult(ERROR_IO);
        try {
            response[0] = okHttpClient.newCall(request).execute();
            responseBody = response[0].body().string();
        } catch (UnknownHostException exception) {
            exception.printStackTrace();
            return jsonResult;
        } catch (IOException e) {
            e.printStackTrace();
            return jsonResult;
        }
        LogHelper.i("requestResult:", responseBody);
        if (jsonClass != null) {
            try {
                T fromJson = new Gson().fromJson(responseBody, jsonClass);
                jsonResult.set(fromJson);
                jsonResult.setHttpErrorCode(SUCCESS);
                LogHelper.i("requestResult json:", fromJson.toString());
                return jsonResult;
            } catch (JsonSyntaxException e) {
            }
        }
        return new JsonResult(ERROR_JSON);
    }

    @Override
    public <T extends JsonRequestResult> JsonResult<T> post(String url, Map<String, String> formBodyMap, Map<String, String> headerMap, Class<T> jsonClass) {
        return postInner(url, formBodyMap, headerMap, jsonClass);
    }

    private Request getRequest(String url, Map<String, String> formBodyMap, Map<String, String> headerMap) {
        FormBody.Builder formBuilder = new FormBody.Builder();
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url);
        if (formBodyMap != null && !formBodyMap.isEmpty()) {
            for (String key : formBodyMap.keySet()) {
                formBuilder.add(key, formBodyMap.get(key));
            }
            requestBuilder.post(formBuilder.build());

        }
        if (headerMap != null && !headerMap.isEmpty()) {
            for (String key : headerMap.keySet()) {
                requestBuilder.header(key, headerMap.get(key));
            }
        }
        return requestBuilder.build();
    }
}
