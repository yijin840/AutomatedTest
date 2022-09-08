package org.wys.automated;

/**
 * @author wys
 * @date 2022/09/08
 * @desc
 */
public class SwaggerConfig {

    private Boolean enable;

    private String path;

    private String docPath;

    public String getDocPath() {
        return docPath;
    }

    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "SwaggerConfig{" +
                "swaggerEnable=" + enable +
                ", swaggerPath='" + path + '\'' +
                ", swaggerDoc='" + docPath + '\'' +
                '}';
    }
}
