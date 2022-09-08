package org.wys.automated;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
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
            fileName = "test.json";
        } else {
            fileName =  name + ".json";
        }
    }

    @Override
    public void buildConfigFile() {
        if (new File(fileName).exists()) {
            throw new RuntimeException("当前配置文件已存在!");
        }
        JSONObject testConfig = new JSONObject();
        if(config.getSwaggerConfig().getEnable()) {
            generateSwaggerApi(testConfig);
        }
        //给配置的接口生成配置项
        generateConfigApi(testConfig);
        FileUtil.saveTestJson(fileName, testConfig);
    }
    private void generateConfigApi(JSONObject testConfig) {
        for (UrlEntity entity : config.getProjectConfig().getTestUrl()) {
            String key = entity.getUrl();
            String type = entity.getType();
            //TODO 这个地方有很大的优化空间
            if(type == null || type.length() == 0) {
                type = "get";
            }
            testConfig.put(key, new AutomatedEntity().builder().type(type).build());
        }
    }


    private void generateSwaggerApi(JSONObject testConfig) {
        String projectPath = config.getProjectConfig().getPath();
        String swaggerDoc = config.getSwaggerConfig().getDocPath();
        HttpGet httpGet = new HttpGet(projectPath + swaggerDoc);
        JSONObject jsonObject = null;
        jsonObject = HttpClientUtil.execute(httpGet);
        JSONObject paths = jsonObject.getJSONObject("paths");
        Set<String> urlKeys = paths.keySet();
        for (String key : urlKeys) {
            //TODO 这个地方有很大的优化空间,现在默认全用get
            testConfig.put(key, new AutomatedEntity().builder().type("get").build());
        }
    }
    @Override
    public void automatedTest() {
        if (!new File(fileName).exists()) {
            throw new RuntimeException("当前测试配置文件不存在!");
        }
        JSONObject testConfig = FileUtil.loadTestJson(fileName);
        System.out.println(testConfig);
        testConfig.forEach((k, v) ->{
            try{
                testRequest(k, (JSONObject) v);
            }catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void testRequest(String path, JSONObject reqJson) {
        String type = reqJson.getString("type");
        String url = config.getProjectConfig().getPath() + path;
        JSONObject headers = reqJson.getJSONObject("headers");
        JSONObject params = reqJson.getJSONObject("params");
        JSONObject result = reqJson.getJSONObject("result");
        JSONObject responseData = null;
        if (type.equalsIgnoreCase(HttpGet.METHOD_NAME)) {
            responseData = testGet(url, headers, params);
        } else if (type.equalsIgnoreCase(HttpPost.METHOD_NAME)) {
            responseData = testPost(url, headers, params);
        }

        assertResult(responseData, result);
    }

    private JSONObject testGet(String url, JSONObject headers, JSONObject params) {
        HttpGet httpGet = new HttpGet();
        headers.forEach((k, v) -> httpGet.addHeader(k, v.toString()));
        List<NameValuePair> list = new LinkedList<>();
        params.forEach((k, v) -> list.add(new BasicNameValuePair(k, v.toString())));
        try{
            httpGet.setURI(new URIBuilder(url).setParameters(list).build());
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return HttpClientUtil.execute(httpGet);
    }

    private JSONObject testPost(String url, JSONObject headers, JSONObject params) {
        HttpPost httpPost = new HttpPost();
        headers.forEach((k, v) -> httpPost.addHeader(k, v.toString()));
        List<NameValuePair> list = new LinkedList<>();
        params.forEach((k, v) -> list.add(new BasicNameValuePair(k, v.toString())));
        try{
            httpPost.setURI(new URIBuilder(url).setParameters(list).build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return HttpClientUtil.execute(httpPost);
    }

    private void assertResult(JSONObject origin, JSONObject target) {
        System.out.println(origin);
        System.out.println("=======================================================================================");
        System.out.println(target);
    }
}
