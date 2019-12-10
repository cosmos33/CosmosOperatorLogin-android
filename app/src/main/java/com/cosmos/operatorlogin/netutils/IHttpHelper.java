package com.cosmos.operatorlogin.netutils;


import java.util.Map;

public interface IHttpHelper {
    int SUCCESS = 0;
    int ERROR_IO = 1;
    int ERROR_JSON = 2;

    <T extends JsonRequestResult> JsonResult<T> post(String url,
                                                     Map<String, String> formBodyMap,
                                                     Map<String, String> headerMap,
                                                     Class<T> jsonClass);
}
