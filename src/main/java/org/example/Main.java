package org.example;

public class Main {
    public static void main(String[] args) {
        try {
            FunctionLoader loader = new FunctionLoader("/home/e/IdeaProjects/Evseev_LAB3_Olegovich/calc.properties");

            // Вызов метода без аргументов
            Object result1 = loader.invokeFunction("function1");
            System.out.println("Result of function 1: " + result1);

            // Вызов метода с одним аргументом (например, Math.sin)
            Object result2 = loader.invokeFunction("function2", Math.PI/2);
            System.out.println("Result of function 2: " + result2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

