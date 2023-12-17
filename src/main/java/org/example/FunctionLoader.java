package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
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

    public Object invokeFunction(String functionName, Object... args) throws Exception {
        String functionDefinition = getFunction(functionName);
        if (functionDefinition == null) {
            throw new IllegalArgumentException("Function " + functionName + " not defined");
        }

        String[] parts = functionDefinition.split("\\.");
        String className = String.join(".", Arrays.copyOf(parts, parts.length - 1));
        String methodName = parts[parts.length - 1];

        Class<?> clazz = Class.forName(className);
        Method method = findMethod(clazz, methodName, args);
        return method.invoke(null, args); // null for static methods
    }

    private Method findMethod(Class<?> clazz, String methodName, Object... args) throws NoSuchMethodException {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName) && method.getParameterCount() == args.length) {
                boolean matches = true;
                Class<?>[] parameterTypes = method.getParameterTypes();
                for (int i = 0; i < parameterTypes.length; i++) {
                    Class<?> paramType = parameterTypes[i];
                    if (paramType.isPrimitive()) {
                        paramType = wrapPrimitiveType(paramType);
                    }
                    if (!paramType.isInstance(args[i])) {
                        matches = false;
                        break;
                    }
                }
                if (matches) {
                    return method;
                }
            }
        }
        throw new NoSuchMethodException(clazz.getName() + "." + methodName);
    }

    private Class<?> wrapPrimitiveType(Class<?> type) {
        if (type == int.class) return Integer.class;
        if (type == long.class) return Long.class;
        if (type == double.class) return Double.class;
        if (type == float.class) return Float.class;
        if (type == boolean.class) return Boolean.class;
        if (type == byte.class) return Byte.class;
        if (type == char.class) return Character.class;
        if (type == short.class) return Short.class;
        return type;
    }
}
