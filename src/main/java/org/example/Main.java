package org.example;

public class Main {
    public static void main(String[] args) {
        try {
            FunctionLoader loader = new FunctionLoader("src/main/resources/calc.properties");

            Object result1 = loader.invokeFunction("function1");
            System.out.println("Result of function 1: " + result1);

            Object result2 = loader.invokeFunction("function2", Math.PI/2);
            System.out.println("Result of function 2: " + result2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

