package pk;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.awt.event.ActionListener;

public class PortServer extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldPort1;
    private JTextField textFieldPort2;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                PortServer frame = new PortServer();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public PortServer() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 0, 0));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblPortServer1 = new JLabel("Port Server 1");
        lblPortServer1.setForeground(Color.WHITE);
        lblPortServer1.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
        lblPortServer1.setBounds(10, 54, 170, 42);
        contentPane.add(lblPortServer1);
        
        JLabel lblPortServer2 = new JLabel("Port Server 2");
        lblPortServer2.setForeground(Color.WHITE);
        lblPortServer2.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
        lblPortServer2.setBounds(10, 167, 170, 42);
        contentPane.add(lblPortServer2);
        
        textFieldPort1 = new JTextField();
        textFieldPort1.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldPort1.setFont(new Font("Tahoma", Font.BOLD, 14));
        textFieldPort1.setBounds(190, 57, 198, 29);
        contentPane.add(textFieldPort1);
        
        textFieldPort2 = new JTextField();
        textFieldPort2.setHorizontalAlignment(SwingConstants.CENTER);
        textFieldPort2.setFont(new Font("Tahoma", Font.BOLD, 14));
        textFieldPort2.setBounds(190, 170, 198, 29);
        contentPane.add(textFieldPort2);
        
        JButton btnConnect = new JButton("Connect");
        btnConnect.setBounds(335, 280, 100, 30);
        contentPane.add(btnConnect);

        btnConnect.addActionListener(e -> {
            try {
                int serverPort1 = Integer.parseInt(textFieldPort1.getText());
                int serverPort2 = Integer.parseInt(textFieldPort2.getText());
                checkPorts(serverPort1, serverPort2);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid integer port numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void checkPorts(int serverPort1, int serverPort2) {
        if (serverPort1 <= 0 || serverPort2 <= 0) {
            JOptionPane.showMessageDialog(this, "Please enter valid positive port numbers.", "Port Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isPortAvailable(serverPort1) || !isPortAvailable(serverPort2)) {
            JOptionPane.showMessageDialog(this, "One or both ports are already in use. Please choose different ports.", "Port Error", JOptionPane.ERROR_MESSAGE);
            textFieldPort1.setText(""); 
            textFieldPort2.setText(""); 
            return;
        }

        System.out.println("Starting servers...");        
        Main mainInstance = new Main();
        mainInstance.startServers(serverPort1, serverPort2);
    }

    private boolean isPortAvailable(int port) {
        try (DatagramSocket socket = new DatagramSocket(port)) {
            return true;
        } catch (SocketException e) {
            return false;
        }
    }
}
