package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class FunctionApp {

    private JFrame frame;
    private JLabel dateLabel;
    private JLabel currencyLabel;
    private JComboBox<String> functionComboBox;
    private JTextField argumentsField;
    private JLabel resultLabel;
    private FunctionLoader functionLoader;
    private ResourceBundle messages;
    private JButton languageButton;
    private JButton executeButton;
    private Locale currentLocale = Locale.ENGLISH;

    public FunctionApp() {
        functionLoader = new FunctionLoader("src/main/resources/calc.properties");
        messages = ResourceBundle.getBundle("messages", Locale.getDefault());
        initializeUI();
        initializeLanguageButton();

    }

    private void initializeLanguageButton() {
        languageButton = new JButton("Сambiar idioma");
        languageButton.addActionListener(this::toggleLanguage);

        frame.add(languageButton);
    }

    private void toggleLanguage(ActionEvent e) {
        if (currentLocale.equals(Locale.ENGLISH)) {
            currentLocale = new Locale("ru", "RU");
        } else {
            currentLocale = Locale.ENGLISH;
        }
        updateLanguage();
    }

    private void updateLanguage() {
        messages = ResourceBundle.getBundle("messages", currentLocale);

        // Обновление текстов интерфейса
        frame.setTitle(messages.getString("title"));
        executeButton.setText(messages.getString("execute"));
        resultLabel.setText(messages.getString("result"));
        languageButton.setText(messages.getString("change_language"));
        dateLabel.setText(messages.getString("date"));
        currencyLabel.setText(messages.getString("currency"));

    }
    private void initializeUI() {
        dateLabel = new JLabel();
        currencyLabel = new JLabel();



        updateDateAndCurrency();
        frame = new JFrame(messages.getString("title"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.add(dateLabel);
        frame.add(currencyLabel);

        functionComboBox = new JComboBox<>(new String[]{"function1", "function2"});
        argumentsField = new JTextField();
        executeButton = new JButton(messages.getString("execute"));

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
            Object result;
            if (argument.isEmpty()) {
                result = functionLoader.invokeFunction(functionName);
            } else {
                result = functionLoader.invokeFunction(functionName, Double.parseDouble(argument));
            }
            resultLabel.setText(messages.getString("result") + ": " + result);
        } catch (NumberFormatException ex) {
            resultLabel.setText(messages.getString("error") + ": Invalid number format");
        } catch (Exception ex) {
            resultLabel.setText(messages.getString("error") + ": " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    private void updateDateAndCurrency() {
        // Форматирование и отображение текущей даты
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, currentLocale);
        String currentDate = dateFormat.format(new Date());
        dateLabel.setText(currentDate);

        try {
            // Форматирование и отображение примера валюты
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(currentLocale);
            currencyFormat.setCurrency(Currency.getInstance(currentLocale));
            String exampleCurrency = currencyFormat.format(1234.56);
            currencyLabel.setText(exampleCurrency);
        } catch (IllegalArgumentException e) {
            // В случае отсутствия поддержки валюты, установите текст по умолчанию
            currencyLabel.setText("Currency not available for " + currentLocale.toString());
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(FunctionApp::new);
    }
}
