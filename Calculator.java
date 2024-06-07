import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class Calculator extends JFrame {
    private final Font LARGER_FONT = new Font("arial", Font.PLAIN, 20);
    private JTextField tfText;
    private boolean hasNumber = true;
    private String equalOp = "=";

    private CalculatorOp op = new CalculatorOp();

    public Calculator() {
        tfText = new JTextField("", 12);
        tfText.setHorizontalAlignment(JTextField.RIGHT);
        tfText.setFont(LARGER_FONT);

        ActionListener numberListener = new NumberListener();

        String buttonOrder = "1234567890 ";
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 4, 4));

        for (int i = 0; i < buttonOrder.length(); i++) {
            String key = buttonOrder.substring(i, i+1);
            if (key.equals(" ")) {
                buttonPanel.add(new JLabel(""));
            } else {
                JButton button = new JButton(key);
                button.addActionListener(numberListener);
                button.setFont(LARGER_FONT);
                buttonPanel.add(button);
            }
        }

        ActionListener operatorListener = new OperatorListener();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 4, 4));

        String[] opOrder = {"+", "-", "*", "/","=","C","sin","cos","log"};
        for (int i = 0; i < opOrder.length; i++) {
            JButton button = new JButton(opOrder[i]);
            button.addActionListener(operatorListener);
            button.setFont(LARGER_FONT);
            panel.add(button);
        }

        JPanel pan = new JPanel();
        pan.setLayout(new BorderLayout(4, 4));
        pan.add(tfText, BorderLayout.NORTH );
        pan.add(buttonPanel , BorderLayout.CENTER);
        pan.add(panel , BorderLayout.EAST);
        this.setContentPane(pan);
        this.pack();
        this.setTitle("Calculator");
        this.setResizable(false);
    }

    private void action() {
        hasNumber = true;
        tfText.setText("");
        equalOp = "=";
        op.setTotal("");
    }

    class OperatorListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String displayText = tfText.getText();
            if(e.getActionCommand().equals("sin")) {
                tfText.setText("" + Math.sin(Double.valueOf(displayText).doubleValue()));
            } else if(e.getActionCommand().equals("cos")) {
                tfText.setText("" + Math.cos(Double.valueOf(displayText).doubleValue()));
            } else if(e.getActionCommand().equals("log")) {
                tfText.setText("" + Math.log(Double.valueOf(displayText).doubleValue()));
            } else if(e.getActionCommand().equals("C")) {
                tfText.setText("");
            } else {
                if(hasNumber) {
                    action();
                    tfText.setText("");
                } else {
                    hasNumber = true;
                    if(equalOp.equals("=")) {
                        op.setTotal(displayText);
                    } else if(equalOp.equals("+")) {
                        op.add(displayText);
                    } else if(equalOp.equals("-")) {
                        op.subtract(displayText);
                    } else if(equalOp.equals("*")) {
                        op.multiply(displayText);
                    } else if(equalOp.equals("/")) {
                        op.divide(displayText);
                    }

                    tfText.setText("" + op.getTotalString());
                    equalOp = e.getActionCommand();
                }
            }
        }
    }

    class NumberListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String digit = event.getActionCommand();

            if (hasNumber) {
                tfText.setText(digit);
                hasNumber = false;
            } else {
                tfText.setText(tfText.getText() + digit);
            }
        }
    }

    public class CalculatorOp {
        private int total;
        public CalculatorOp() {
            total = 0;
        }
        public String getTotalString() {
            return "" + total;
        }
        public void setTotal(String n) {
            total = convertToNumber(n);
        }
        public void add(String n) {
            total += convertToNumber(n);
        }
        public void subtract(String n) {
            total -= convertToNumber(n);
        }
        public void multiply(String n) {
            total *= convertToNumber(n);
        }
        public void divide(String n) {
            // Handle divide by zero error
            int num = convertToNumber(n);
            if(num == 0) {
                total = 0;
            } else {
                total /= num;
            }
        }
        private int convertToNumber(String n) {
            return Integer.parseInt(n);
        }
    }
}
