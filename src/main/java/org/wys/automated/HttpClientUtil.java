package org.wys.automated;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author wys
 * @date 2022/09/08
 * @desc
 */
public class HttpClientUtil {

    /**
     * 发送请求
     *
     * @param request post请求
     * @return json序列化结果
     * @throws IOException IO异常
     */
    public static JSONObject execute(HttpUriRequest request) throws IOException {
        CloseableHttpResponse response = HttpClients.createDefault().execute(request);
        response.setHeader("ContentType", "application/json; charset=UTF-8");
        HttpEntity entity = response.getEntity();
        String res = EntityUtils.toString(entity, StandardCharsets.UTF_8);
        return JSON.parseObject(res);
    }

}
