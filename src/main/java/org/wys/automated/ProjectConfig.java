package org.wys.automated;

/**
 * ClassName ProjectConfig
 * Package org.wys.automated
 * Description
 *
 * @author wys
 * @date 2022/9/8 7:40
 */
public class ProjectConfig {

    private String projectName;

    private String projectPath;

    private String swaggerPath;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getSwaggerPath() {
        return swaggerPath;
    }

    public void setSwaggerPath(String swaggerPath) {
        this.swaggerPath = swaggerPath;
    }
}
