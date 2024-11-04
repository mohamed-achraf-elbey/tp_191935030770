package pk;

import java.awt.Color;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class Frame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldSenderIP;
    private JTextField textFieldPort;
    private JTextField textFieldReceiverIP;
    private JTextField textFieldMessage;
    private JTextArea labelReceivedMessage;
    private JScrollPane scrollPane;
    private JButton btnSend;
    private JButton localhost;
    public boolean GG;

    @SuppressWarnings("deprecation")
	public Frame(int position, boolean SC) {
    	
        GG = SC;
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

        // Adding labels
        addLabels(panel);

        // Adding text fields
        addTextFields(panel);

        // Adding buttons
        addButtons(panel);

        // Initial state of components
        setComponentsState(true);
        
    }

    private void addLabels(JPanel panel) {
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
    }

    private void addTextFields(JPanel panel) {
        textFieldSenderIP = new JTextField();
        textFieldSenderIP.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldSenderIP.setBounds(203, 49, 198, 29);
        panel.add(textFieldSenderIP);

        textFieldPort = new JTextField();
        textFieldPort.setEditable(false);
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

        labelReceivedMessage = new JTextArea();
        labelReceivedMessage.setForeground(Color.WHITE);
        labelReceivedMessage.setBackground(Color.BLACK);
        labelReceivedMessage.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
        labelReceivedMessage.setEditable(false); // منع التعديل على الرسائل المستقبلة

        // وضع JTextArea في JScrollPane
        scrollPane = new JScrollPane(labelReceivedMessage);
        scrollPane.setBounds(215, 456, 500, 80);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scrollPane);
    }

    private void addButtons(JPanel panel) {
        btnSend = new JButton("Send");
        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validateInputs()) {
                    // Input is valid, send the message
                    textFieldMessage.setText("");
                    // Additional logic for sending message can be added here
                }
            }
        });
        btnSend.setBounds(163, 360, 85, 21);
        panel.add(btnSend);

        localhost = new JButton("localhost");
        localhost.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    InetAddress localHost = InetAddress.getLocalHost();
                    String localIp = localHost.getHostAddress();
                    if (GG) {
                        textFieldPort.setText("8888");
                        textFieldSenderIP.setText(localIp);
                        textFieldReceiverIP.setText(localIp);
                    } else {
                        textFieldPort.setText("8888");
                        textFieldSenderIP.setText(localIp);
                        textFieldReceiverIP.setText(localIp);
                    }
                } catch (UnknownHostException e1) {
                    e1.printStackTrace();
                }
            }
        });

        localhost.setBounds(630, 49, 85, 21);
        panel.add(localhost);
    }

    // Getter methods for text fields
    public String getSenderIP() {
        return textFieldSenderIP.getText();
    }

    

    public String getReceiverIP() {
        return textFieldReceiverIP.getText();
    }

    public String getMessage() {
        return textFieldMessage.getText();
    }

    public JButton getButtonSend() {
        return btnSend;
    }

    // Method to add a new received message to the label
    public void appendReceivedMessage(String message) {
        labelReceivedMessage.append(message + "\n");
    }

    // Method to provide message listener interface for receiving messages
    public MessageListener getMessageListener() {
        return this::appendReceivedMessage;
    }

    // Validation for input fields
    private boolean validateInputs() {
        String senderIP = getSenderIP();
        String receiverIP = getReceiverIP();
        String portText = textFieldPort.getText();
        String message = getMessage();

        if (senderIP.isEmpty() || receiverIP.isEmpty() || portText.isEmpty() || message.isEmpty()) {
            showErrorDialog("All fields must be filled.");
            return false;
        }

        if (!isValidIPAddress(senderIP)) {
            showErrorDialog("Invalid sender IP address.");
            return false;
        }

        if (!isValidIPAddress(receiverIP)) {
            showErrorDialog("Invalid receiver IP address.");
            return false;
        }

        int port;
        try {
            port = Integer.parseInt(portText);
            if (port < 0 || port > 65535) {
                showErrorDialog("Port number must be between 0 and 65535.");
                return false;
            }
        } catch (NumberFormatException e) {
            showErrorDialog("Port must be a valid number.");
            return false;
        }

        return true; // All inputs are valid
    }

    // Method to check if the IP address is valid
    private boolean isValidIPAddress(String ip) {
        String ipPattern = 
            "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + 
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + 
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + 
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        return ip.matches(ipPattern);
    }

    // Method to display an error dialog
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }

    // Method to enable or disable components
    private void setComponentsState(boolean enabled) {
        textFieldSenderIP.setEnabled(enabled);
        textFieldReceiverIP.setEnabled(enabled);
        textFieldMessage.setEnabled(enabled);
        btnSend.setEnabled(enabled);
        localhost.setEnabled(enabled);
    }

    // Interface to allow receiving messages in real-time
    public interface MessageListener {
        void onMessageReceived(String message);
    }
}
