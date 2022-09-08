package org.wys.automated;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * @author wys
 * @date 2022/09/08
 * @desc
 */
public class AutomatedEntity implements Serializable {

    private static final long serialVersionUID = -1220625228930062897L;
    private JSONObject params;
    private JSONObject result;

    private String type;

    private JSONObject headers;

    private AutomatedEntity entityBuild;

    public AutomatedEntity() {
        params = new JSONObject();
        result = new JSONObject();
        headers = new JSONObject();
    }

    public JSONObject getParams() {
        return params;
    }

    public void setParams(JSONObject params) {
        this.params = params;
    }

    public JSONObject getResult() {
        return result;
    }

    public void setResult(JSONObject result) {
        this.result = result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JSONObject getHeaders() {
        return headers;
    }

    public void setHeaders(JSONObject headers) {
        this.headers = headers;
    }
    public AutomatedEntityBuild builder() {
        return new AutomatedEntityBuild();
    }

    static class AutomatedEntityBuild {
        private final AutomatedEntity entity;
        public AutomatedEntityBuild() {
            entity = new AutomatedEntity();
        }
        public AutomatedEntityBuild type(String type) {
            entity.type = type;
            return this;
        }
        public AutomatedEntity build() {
            return entity;
        }
    }

    @Override
    public String toString() {
        return "AutomatedEntity{" +
                "params=" + params +
                ", result=" + result +
                ", type='" + type + '\'' +
                ", headers='" + headers + '\'' +
                '}';
    }
}
