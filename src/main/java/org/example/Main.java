package org.example;

public class Main {
    public static void main(String[] args) {
        FunctionLoader loader = new FunctionLoader("/home/e/IdeaProjects/Evseev_LAB3_Olegovich/calc.properties");
        String function1 = loader.getFunction("function1");
        System.out.println("Function 1: " + function1);

        String function2 = loader.getFunction("function2");
        System.out.println("Function 2: " + function2);
    }
}
