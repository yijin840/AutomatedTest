package org.wys.automated;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

/**
 * ClassName ProjectConfig
 * Package org.wys.automated
 * Description
 *
 * @author wys
 * @date 2022/9/8 7:40
 */
public class AutomatedConfig {

    private ProjectConfig projectConfig;

    public ProjectConfig getProjectConfig() {
        return projectConfig;
    }

    public void setProjectConfig(ProjectConfig projectConfig) {
        this.projectConfig = projectConfig;
    }

    public boolean containsUrl(String url) {
        return projectConfig.getTestUrl().stream().map(UrlEntity::getUrl).anyMatch(Predicate.isEqual(url));
    }

    public UrlEntity getUrlEntity(String url) {
        return projectConfig.getTestUrl().stream().filter(u->u.getUrl().equals(url)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return "AutomatedConfig{" +
                "projectConfig=" + projectConfig +
                '}';
    }
}
