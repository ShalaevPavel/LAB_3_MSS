package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FunctionLoader {

    private Properties functionProperties;

    public FunctionLoader(String propertiesFilePath) {
        functionProperties = new Properties();
        try {
            functionProperties.load(new FileInputStream(propertiesFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFunction(String functionName) {
        return functionProperties.getProperty(functionName);
    }
}
