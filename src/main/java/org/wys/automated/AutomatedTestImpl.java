package org.wys.automated;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * ClassName AutomatedTestImpl
 * Package org.wys.automated
 * Description
 *
 * @author wys
 * @date 2022/9/7 23:57
 */
public class AutomatedTestImpl implements AutomatedTest {

    private final AutomatedConfig config;
    private final String fileName;

    public AutomatedTestImpl() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        config = objectMapper.readValue(this.getClass().getClassLoader().getResourceAsStream("config.yml"),
                AutomatedConfig.class);
        String name = config.getProjectConfig().getName();
        if(Objects.isNull(name) || name.length() == 0) {
            fileName = "test";
        } else {
            fileName =  name + ".json";
        }
    }

    @Override
    public void buildConfigFile() {
        if (new File(fileName).exists()) {
            throw new RuntimeException("当前配置文件已存在!");
        }
        String swaggerDoc = config.getProjectConfig().getSwaggerDoc();
        if(Objects.isNull(swaggerDoc) || swaggerDoc.length() == 0) {
            swaggerDoc = "/v2/api-docs";
        }
        String projectPath = config.getProjectConfig().getPath();
        HttpGet httpGet = new HttpGet(projectPath + swaggerDoc);
        JSONObject jsonObject = null;
        try {
            jsonObject = HttpClientUtil.execute(httpGet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JSONObject paths = jsonObject.getJSONObject("paths");
        Set<String> urlKeys = paths.keySet();
        JSONObject testConfig = new JSONObject();
        for (String key : urlKeys) {
            if (config.containsUrl(key)) {
                testConfig.put(key, new AutomatedEntity().builder().type(config.getUrlEntity(key).getType()).build());
            }
            System.out.println(key);
        }
        FileUtil.saveTestJson(config.getProjectConfig().getName(), testConfig);
    }

    @Override
    public void automatedTest() {
        if (!new File(fileName).exists()) {
            throw new RuntimeException("当前测试配置文件不存在!");
        }
        JSONObject testConfig = FileUtil.loadTestJson(fileName);
        System.out.println(testConfig);
        testConfig.forEach((k, v) -> testRequest(k, (JSONObject) v));
    }

    private void testRequest(String path, JSONObject reqJson) {
        String type = reqJson.getString("type");
        String url = config.getProjectConfig().getPath() + path;
        JSONObject headers = reqJson.getJSONObject("headers");
        JSONObject params = reqJson.getJSONObject("params");
        JSONObject result = reqJson.getJSONObject("result");
        JSONObject responseData = null;
        try {
            if (type.equalsIgnoreCase(HttpGet.METHOD_NAME)) {
                responseData = testGet(url, headers, params);
            } else if (type.equalsIgnoreCase(HttpPost.METHOD_NAME)) {
                responseData = testPost(url, headers, params);
            }
        } catch (Exception e) {
            throw new RuntimeException("测试异常！" + e);
        }
        assertResult(responseData, result);
    }

    private JSONObject testGet(String url, JSONObject headers, JSONObject params) throws URISyntaxException, IOException {
        HttpGet httpGet = new HttpGet();
        headers.forEach((k, v) -> httpGet.addHeader(k, v.toString()));
        List<NameValuePair> list = new LinkedList<>();
        params.forEach((k, v) -> list.add(new BasicNameValuePair(k, v.toString())));
        httpGet.setURI(new URIBuilder(url).setParameters(list).build());
        return HttpClientUtil.execute(httpGet);
    }

    private JSONObject testPost(String url, JSONObject headers, JSONObject params) throws URISyntaxException, IOException {
        HttpPost httpPost = new HttpPost();
        headers.forEach((k, v) -> httpPost.addHeader(k, v.toString()));
        List<NameValuePair> list = new LinkedList<>();
        params.forEach((k, v) -> list.add(new BasicNameValuePair(k, v.toString())));
        httpPost.setURI(new URIBuilder(url).setParameters(list).build());
        return HttpClientUtil.execute(httpPost);
    }

    private void assertResult(JSONObject origin, JSONObject target) {
        System.out.println(origin);
        System.out.println("=======================================================================================");
        System.out.println(target);
    }
}
