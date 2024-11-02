package pk;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class Frame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldSenderIP;
    private JTextField textFieldPort;
    private JTextField textFieldReceiverIP;
    private JTextField textFieldMessage;
    private JLabel labelReceivedMessage;
    private JButton btnSend;

    public Frame(int position) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(position, 200, 800, 690);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 0, 0));
        panel.setBounds(0, 0, 1050, 653);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel text1 = new JLabel("IP Address Sender");
        text1.setForeground(Color.WHITE);
        text1.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
        text1.setBounds(37, 49, 170, 42);
        panel.add(text1);

        JLabel text1_1 = new JLabel("Port Server");
        text1_1.setForeground(Color.WHITE);
        text1_1.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
        text1_1.setBounds(37, 119, 170, 42);
        panel.add(text1_1);

        JLabel text1_2 = new JLabel("IP Address Receiver");
        text1_2.setForeground(Color.WHITE);
        text1_2.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
        text1_2.setBounds(37, 204, 170, 42);
        panel.add(text1_2);

        JLabel text1_3 = new JLabel("Message to Send");
        text1_3.setForeground(Color.WHITE);
        text1_3.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
        text1_3.setBounds(37, 270, 170, 42);
        panel.add(text1_3);

        JLabel text1_5 = new JLabel("Message Received");
        text1_5.setForeground(Color.WHITE);
        text1_5.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
        text1_5.setBounds(37, 443, 170, 42);
        panel.add(text1_5);

        textFieldSenderIP = new JTextField();
        textFieldSenderIP.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldSenderIP.setBounds(203, 49, 198, 29);
        panel.add(textFieldSenderIP);

        textFieldPort = new JTextField();
        textFieldPort.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldPort.setBounds(203, 119, 198, 29);
        panel.add(textFieldPort);

        textFieldReceiverIP = new JTextField();
        textFieldReceiverIP.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldReceiverIP.setBounds(203, 209, 198, 29);
        panel.add(textFieldReceiverIP);

        textFieldMessage = new JTextField();
        textFieldMessage.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldMessage.setBounds(203, 277, 198, 29);
        panel.add(textFieldMessage);

        labelReceivedMessage = new JLabel("");
        labelReceivedMessage.setForeground(Color.WHITE);
        labelReceivedMessage.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
        labelReceivedMessage.setBounds(215, 456, 500, 80);
        panel.add(labelReceivedMessage);

        btnSend = new JButton("Send");
        btnSend.setBounds(163, 360, 85, 21);
        panel.add(btnSend);
    }

    // Getter methods for text fields
    public String getSenderIP() {
        return textFieldSenderIP.getText();
    }

    public int getPort() {
        String portText = textFieldPort.getText().trim();  // Trim whitespace
        try {
            int port = Integer.parseInt(portText);
            System.out.println("Parsed port: " + port);  // Debug statement
            return port;
        } catch (NumberFormatException e) {
            System.out.println("Invalid port number. Defaulting to 0.");
            return 0; // Default or handle as appropriate
        }
    }


    public String getReceiverIP() {
        return textFieldReceiverIP.getText();
    }

    public String getMessage() {
        return textFieldMessage.getText();
    }

    // Method to set received message on the label
    public void setReceivedMessage(String message) {
        labelReceivedMessage.setText(message);
    }

    // Getter for Send button
    public JButton getButtonSend() {
        return btnSend;
    }
}
