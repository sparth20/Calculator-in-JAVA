// Advanced calculator using java.

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JCalculator extends JFrame implements ActionListener {
    private JTextField display;
    private double firstNumber = 0;
    private String operation = "";
    private boolean startNewNumber = true;

    public JCalculator() {
        // Set up the frame
        setTitle("Scientific JCalculator");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.GRAY);

        // Create display
        display = new JTextField();
        display.setFont(new Font("Times-New-Roman", Font.BOLD, 34));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(Color.WHITE);
        add(display, BorderLayout.NORTH);

        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 5, 5, 5));
        buttonPanel.setBackground(Color.LIGHT_GRAY);

        // Button labels
        String[] buttons = {
            "7", "8", "9", "/", "sin",
            "4", "5", "6", "*", "cos",
            "1", "2", "3", "-", "tan",
            "0", ".", "=", "+", "log",
            "C", "CE", "√", "x²", "ln",
            "x^y", "%", "n!", "(", ")"
        };

        // Create and add buttons
        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.addActionListener(this);
            button.setBackground(Color.WHITE);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // Handle digits, decimal point, and parentheses
        if (command.matches("[0-9]") || command.equals(".") || command.equals("(") || command.equals(")")) {
            if (startNewNumber) {
                display.setText(command);
                startNewNumber = false;
            } else {
                display.setText(display.getText() + command);
            }
        }
        // Handle unary operations (sin, cos, etc.)
        else if (command.equals("sin")) {
            unaryOperation(Math::sin);
        } else if (command.equals("cos")) {
            unaryOperation(Math::cos);
        } else if (command.equals("tan")) {
            unaryOperation(Math::tan);
        } else if (command.equals("log")) {
            unaryOperation(Math::log10);
        } else if (command.equals("ln")) {
            unaryOperation(Math::log);
        } else if (command.equals("√")) {
            unaryOperation(Math::sqrt);
        } else if (command.equals("x²")) {
            unaryOperation(n -> n * n);
        } else if (command.equals("n!")) {
            try {
                int num = (int) Double.parseDouble(display.getText());
                if (num >= 0) {
                    display.setText(String.valueOf(factorial(num)));
                } else {
                    display.setText("Error");
                }
                startNewNumber = true;
            } catch (NumberFormatException | ArithmeticException ex) {
                display.setText("Error");
                startNewNumber = true;
            }
        }
        // Handle clear and equals buttons
        else if (command.equals("C")) {
            display.setText("0");
            firstNumber = 0;
            operation = "";
            startNewNumber = true;
        } else if (command.equals("CE")) {
            display.setText("0");
            startNewNumber = true;
        } else if (command.equals("=")) {
            if (!operation.isEmpty()) {
                calculate();
                operation = "";
                startNewNumber = true;
            }
        }
        // Handle binary operations (+, -, *, /, x^y, %)
        else {
            if (!operation.isEmpty() && !startNewNumber) {
                calculate();
            }
            try {
                firstNumber = Double.parseDouble(display.getText());
                operation = command;
                startNewNumber = true;
            } catch (NumberFormatException ex) {
                display.setText("Error");
                startNewNumber = true;
                operation = "";
            }
        }
    }

    private void unaryOperation(java.util.function.DoubleFunction<Double> func) {
        try {
            double num = Double.parseDouble(display.getText());
            display.setText(String.valueOf(func.apply(num)));
            startNewNumber = true;
        } catch (NumberFormatException ex) {
            display.setText("Error");
            startNewNumber = true;
        }
    }
    
    private long factorial(int n) {
        if (n < 0) throw new ArithmeticException("Factorial of negative number is undefined");
        if (n == 0 || n == 1) return 1;
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    private void calculate() {
        try {
            double secondNumber = Double.parseDouble(display.getText());
            double result = 0;

            switch (operation) {
                case "+":
                    result = firstNumber + secondNumber;
                    break;
                case "-":
                    result = firstNumber - secondNumber;
                    break;
                case "*":
                    result = firstNumber * secondNumber;
                    break;
                case "/":
                    if (secondNumber != 0) {
                        result = firstNumber / secondNumber;
                    } else {
                        display.setText("Error");
                        return;
                    }
                    break;
                case "x^y":
                    result = Math.pow(firstNumber, secondNumber);
                    break;
                case "%":
                    if (secondNumber != 0) {
                        result = firstNumber % secondNumber;
                    } else {
                        display.setText("Error");
                        return;
                    }
                    break;
            }

            // Check if the result is an integer to display without decimal
            if (result == (int) result) {
                display.setText(String.valueOf((int) result));
            } else {
                display.setText(String.valueOf(result));
            }
        } catch (NumberFormatException ex) {
            display.setText("Error");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JCalculator calculator = new JCalculator();
            calculator.setVisible(true);
        });
    }
}
