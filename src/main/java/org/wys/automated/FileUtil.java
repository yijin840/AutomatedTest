package org.wys.automated;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author wys
 * @date 2022/09/08
 * @desc 文件工具类
 */
public class FileUtil {

    public static void saveTestJson(String fileName, JSONObject testConfig) {
        try(FileOutputStream fos = new FileOutputStream(fileName);
            BufferedOutputStream bos = new BufferedOutputStream(fos)){
            String formatJson = JSON.toJSONString(testConfig, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue
                    , SerializerFeature.WriteDateUseDateFormat);
            bos.write(formatJson.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject loadTestJson(String fileName) {
        StringBuilder testConfig = new StringBuilder();
        try(FileInputStream fis = new FileInputStream(fileName);
            BufferedInputStream bis = new BufferedInputStream(fis)) {
            int read;
            while((read = bis.read()) != -1) {
                testConfig.append((char) read);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return JSON.parseObject(testConfig.toString());
    }

}
