// Simple calculator using java.

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleCalculator extends JFrame implements ActionListener {

    private JTextField display;
    private JButton[] numberButtons;
    private JButton[] functionButtons;
    private JButton addButton, subButton, mulButton, divButton, eqButton, clrButton, decButton, sqrtButton, sqrButton, invButton;
    private JPanel panel;

    private Font myFont = new Font("Arial", Font.PLAIN, 30);
    private double num1 = 0, num2 = 0, result = 0;
    private char operator;

    public SimpleCalculator() {
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 550);
        setLayout(null);
        setLocationRelativeTo(null); // Center the window

        display = new JTextField("0");
        display.setBounds(50, 25, 300, 75);
        display.setFont(myFont);
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);

        functionButtons = new JButton[10]; // Increased the size to 10
        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("x");
        divButton = new JButton("÷");
        eqButton = new JButton("=");
        clrButton = new JButton("C");
        decButton = new JButton(".");
        sqrtButton = new JButton("√");
        sqrButton = new JButton("x²");
        invButton = new JButton("¹/x");

        functionButtons[0] = addButton;
        functionButtons[1] = subButton;
        functionButtons[2] = mulButton;
        functionButtons[3] = divButton;
        functionButtons[4] = eqButton;
        functionButtons[5] = clrButton;
        functionButtons[6] = decButton;
        functionButtons[7] = sqrtButton;
        functionButtons[8] = sqrButton;
        functionButtons[9] = invButton; // Now this index is valid

        for (int i = 0; i < 9; i++) {
            functionButtons[i].addActionListener(this);
            functionButtons[i].setFont(myFont);
            functionButtons[i].setFocusable(false);
        }

        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(myFont);
            numberButtons[i].setFocusable(false);
        }

        panel = new JPanel();
        panel.setBounds(50, 125, 300, 350);
        panel.setLayout(new GridLayout(5, 4, 10, 10)); // 5 rows, 4 columns, horizontal and vertical gap

        panel.add(sqrButton);
        panel.add(invButton);
        panel.add(clrButton);
        panel.add(divButton);

        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(mulButton);

        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);

        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(addButton);

        panel.add(sqrtButton);
        panel.add(numberButtons[0]);
        panel.add(decButton);
        panel.add(eqButton);

        add(display);
        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        // Use SwingUtilities.invokeLater to ensure thread safety for GUI updates
        SwingUtilities.invokeLater(() -> new SimpleCalculator());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source instanceof JButton) {
            String buttonText = ((JButton) source).getText();

            if ((buttonText.matches("[0-9]")) || buttonText.equals(".")) {
                if (display.getText().equals("0")) {
                    display.setText(buttonText);
                } else {
                    display.setText(display.getText() + buttonText);
                }
            } else if (buttonText.equals("+") || buttonText.equals("-") || buttonText.equals("×") || buttonText.equals("÷")) {
                num1 = Double.parseDouble(display.getText());
                operator = buttonText.charAt(0);
                display.setText("0");
            } else if (buttonText.equals("=")) {
                num2 = Double.parseDouble(display.getText());
                switch (operator) {
                    case '+':
                        result = num1 + num2;
                        break;
                    case '-':
                        result = num1 - num2;
                        break;
                    case '×':
                        result = num1 * num2;
                        break;
                    case '÷':
                        if (num2 != 0) {
                            result = num1 / num2;
                        } else {
                            display.setText("Error");
                            return;
                        }
                        break;
                }
                display.setText(String.valueOf(result));
                num1 = result; // Store result for potential chaining
                operator = '\0'; // Reset operator
            } else if (buttonText.equals("C")) {
                display.setText("0");
                num1 = 0;
                num2 = 0;
                result = 0;
                operator = '\0';
            } else if (buttonText.equals("√")) {
                num1 = Double.parseDouble(display.getText());
                if (num1 >= 0) {
                    result = Math.sqrt(num1);
                    display.setText(String.valueOf(result));
                    num1 = result;
                } else {
                    display.setText("Error");
                }
            } else if (buttonText.equals("x²")) {
                num1 = Double.parseDouble(display.getText());
                result = Math.pow(num1, 2);
                display.setText(String.valueOf(result));
                num1 = result;
            } else if (buttonText.equals("¹/x")) {
                num1 = Double.parseDouble(display.getText());
                if (num1 != 0) {
                    result = 1 / num1;
                    display.setText(String.valueOf(result));
                    num1 = result;
                } else {
                    display.setText("Error");
                }
            }
        }
    }
}
