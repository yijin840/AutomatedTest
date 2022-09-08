package org.wys;

import org.wys.automated.AutomatedTest;
import org.wys.automated.AutomatedTestImpl;

import java.io.IOException;

/**
 * @author wys
 * @date ${DATE} ${TIME}
 */
public class Main {
    public static void main(String[] args) throws IOException {
        AutomatedTest automatedTest = new AutomatedTestImpl();
        automatedTest.buildConfigFile();
        automatedTest.automatedTest();
    }

}