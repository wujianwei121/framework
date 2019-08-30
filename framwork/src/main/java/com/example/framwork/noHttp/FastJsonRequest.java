/*
 * Copyright 2015 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.framwork.noHttp;

import com.example.framwork.utils.DLog;
import com.example.framwork.utils.EncryptUtil;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.StringRequest;
import com.yanzhenjie.nohttp.tools.MultiValueMap;

/**
 * <p>自定义请求对象.</p>
 * Created in Feb 1, 2016 8:53:17 AM.
 *
 * @author Yan Zhenjie.
 */
public class FastJsonRequest extends Request<String> {
    private String post;
    public static final String TAG = "Network request";

    public FastJsonRequest(String url, RequestMethod requestMethod, String post) {
        super(url, requestMethod);
        this.post = post;
    }

    @Override
    public void onPreExecute() {
        // 这个方法会在真正请求前被调用，在这里可以做一些加密之类的工作。这个方法在子线程被调用。
        // 比如，我们做个模拟加密：
        MultiValueMap<String, Object> multiValueMap = getParamKeyValues();
        String apidata = EncryptUtil.getInstance().encode(post);
        DLog.d(TAG, "APIDATA：" + apidata);
        multiValueMap.set("APIDATA", apidata);

    }


    @Override
    public String parseResponse(Headers responseHeaders, byte[] responseBody) {
        String res = StringRequest.parseResponseString(responseHeaders, responseBody);
        return res;
    }
}
