package org.wys.automated;

/**
 * @author wys
 * @date 2022/09/08
 * @desc
 */
public class UrlEntity {

    private String url;

    private String type;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UrlEntity{" +
                "url='" + url + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
