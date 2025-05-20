import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

public class structure extends JFrame {
    double result;
    JTextField tf1;
    Stack<Double> s = new Stack<>();
    Stack<Character> s1 = new Stack<>();

    public structure() {
       setTitle("Calculator");
        setSize(315 ,420);  // Window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close window on exit
        setLocationRelativeTo(null); // Center the window
       setResizable(false); // Disable window resizing
        setLayout(new BorderLayout());

        Font myFont = new Font("Times New Roman", Font.BOLD, 35);

        // Top TextField
        tf1 = new JTextField();
        tf1.setFont(myFont);
        tf1.setPreferredSize(new Dimension(400,70));
        tf1.setEditable(false);
        tf1.setBackground(Color.BLACK);
        tf1.setForeground(Color.GREEN);
        add(tf1, BorderLayout.NORTH);

        // Grid for buttons
         JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 10, 10)); // Now 6x4 for extra row
        String[] buttons = {
            "7", "8", "9", "+",
            "4", "5", "6", "-",
            "1", "2", "3", "*",
            "0", ".", "=", "/",
            "",  "",  "",  "C" 
        };

        ButtonClickListener listener = new ButtonClickListener();

        for (String label : buttons) {
            JButton b = new JButton(label);
            b.setFont(myFont);
            b.setBackground(Color.LIGHT_GRAY);
            b.setForeground(Color.BLACK);
             b.setBackground(label.equals("C") ? Color.PINK : Color.LIGHT_GRAY);
            b.addActionListener(listener);
            if (label.equals("")) {
            b.setEnabled(false); // disable dummy buttons
            b.setVisible(true); // optionally hide them completely
            }
            buttonPanel.add(b);
        }

        add(buttonPanel, BorderLayout.CENTER); // Add button panel to center
    }

    class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            String current = tf1.getText();
            if ((command.charAt(0) >= '0' && command.charAt(0) <= '9') || command.equals(".")) //check number in between 0 to 9
            {
                 if (command.equals(".") && current.contains(".")) return; 
                tf1.setText(current + command);//Check if any operator press or not
            } else if (command.equals("-")) {
                // Handle unary minus
                if (current.isEmpty() || current.equals("-")) {
                    tf1.setText("-");
                } else {
                    s.push(Double.parseDouble(current));
                    s1.push('-');
                    tf1.setText("");
                }
            }
                else if (command.equals("+") || command.equals("-") || command.equals("*") || command.equals("/")) {
                 if (!current.isEmpty()) {
                    s.push(Double.parseDouble(current));
                    s1.push(command.charAt(0));
                    tf1.setText("");
                }
            } else if (command.equals("=")) {
    if (s.isEmpty() || s1.isEmpty() || tf1.getText().isEmpty()) {
        tf1.setText("Incomplete");
        return;
    }
                s.push(Double.parseDouble(tf1.getText()));
                char operator = s1.pop();
                Double num2 = s.pop();
                Double num1 = s.pop();
        //check which oprator used by user
                switch (operator) {
                    case '+': result = num1 + num2; break;
                    case '-': result = num1 - num2; break;
                    case '*': result = num1 * num2; break;
                    case '/':
                        if (num2 != 0) {
                            result = num1 / num2;
                        } else {
                            tf1.setText("Error: Division by 0");
                            return;
                        }
                        break;
                }
                
           if (result == Math.floor(result)) {
        tf1.setText(String.format("%.0f", result));  // Display without decimal
    } else {
        tf1.setText(String.format("%.4f", result));  // Display up to 4 decimal places
    }

            } else if (command.equals("C")) //check if 'C' button is press or not
            {
                tf1.setText("");//Clear display
                s.clear();//Clear both stack and make them empty 
                s1.clear();
            }
        }
    }
 // Main method to start the GUIs
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                structure frame = new structure();
                frame.setVisible(true); // Show the JFrame
            }
        });
    }
}
