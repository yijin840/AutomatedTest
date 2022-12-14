package org.wys.automated;

/**
 * ClassName AutomatedHandler
 * Package org.wys.automated
 * Description
 *
 * @author wys
 * @date 2022/9/7 23:55
 */
public interface AutomatedTest {

    /**
     * 构建配置文件
     */
    void buildConfigFile();


    /**
     * 自动测试
     */
    void automatedTest();

}
