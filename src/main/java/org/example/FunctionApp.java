package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class FunctionApp {

    private JFrame frame;
    private JComboBox<String> functionComboBox;
    private JTextField argumentsField;
    private JLabel resultLabel;
    private FunctionLoader functionLoader;
    private ResourceBundle messages;

    public FunctionApp() {
        functionLoader = new FunctionLoader("src/main/resources/calc.properties");
        messages = ResourceBundle.getBundle("messages", Locale.getDefault());
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame(messages.getString("title"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        functionComboBox = new JComboBox<>(new String[]{"function1", "function2"});
        argumentsField = new JTextField();
        JButton executeButton = new JButton(messages.getString("execute"));
        resultLabel = new JLabel(messages.getString("result"));

        executeButton.addActionListener(this::executeFunction);

        frame.add(functionComboBox);
        frame.add(argumentsField);
        frame.add(executeButton);
        frame.add(resultLabel);

        frame.setVisible(true);
    }

    private void executeFunction(ActionEvent e) {
        String functionName = (String) functionComboBox.getSelectedItem();
        String argument = argumentsField.getText();
        try {
            Object result = functionLoader.invokeFunction(functionName, Double.parseDouble(argument));
            resultLabel.setText(messages.getString("result") + ": " + result);
        } catch (Exception ex) {
            resultLabel.setText(messages.getString("error") + ": " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FunctionApp::new);
    }
}
