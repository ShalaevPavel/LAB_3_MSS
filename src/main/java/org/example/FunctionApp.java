package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FunctionApp {

    private FunctionLoader functionLoader;
    private JFrame frame;
    private JComboBox<String> functionComboBox;
    private JTextField argumentsField;
    private JLabel resultLabel;

    public FunctionApp() {
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Function Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        functionComboBox = new JComboBox<>();
        functionComboBox.addItem("function1");
        functionComboBox.addItem("function2");
        // Добавьте здесь другие функции

        argumentsField = new JTextField();
        JButton executeButton = new JButton("Execute");

        resultLabel = new JLabel("Result: ");

        executeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeFunction();
            }
        });

        frame.add(functionComboBox);
        frame.add(argumentsField);
        frame.add(executeButton);
        frame.add(resultLabel);

        frame.setVisible(true);
    }

    private void executeFunction() {
        String functionName = (String) functionComboBox.getSelectedItem();
        String argument = argumentsField.getText();

    }

    public static void main(String[] args) {
        new FunctionApp();
    }
}
