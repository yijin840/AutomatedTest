package org.wys.automated;

import java.util.List;

/**
 * @author wys
 * @date 2022/09/08
 * @desc
 */
public class ProjectConfig {

    private String name;

    private String path;

    private List<UrlEntity> testUrl;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<UrlEntity> getTestUrl() {
        return testUrl;
    }

    public void setTestUrl(List<UrlEntity> testUrl) {
        this.testUrl = testUrl;
    }

    @Override
    public String toString() {
        return "ProjectConfig{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", testUrl=" + testUrl +
                '}';
    }
}
