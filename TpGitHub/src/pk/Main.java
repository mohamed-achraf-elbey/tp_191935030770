package pk;

import java.awt.EventQueue;
import java.net.DatagramSocket;
import java.net.SocketException;
import javax.swing.JOptionPane;

public class Main {

    public void startServers( int serverPort1 , int serverPort2 ) {
        EventQueue.invokeLater(() -> {
      
            
            Frame frameServer1 = new Frame(200, false , serverPort1 , serverPort2 );
            frameServer1.setVisible(true);

            Frame frameServer2 = new Frame(1000, true , serverPort2 , serverPort1);
            frameServer2.setVisible(true);

            

            if (serverPort1 <= 0) {
                JOptionPane.showMessageDialog(null, "Invalid port specified for Server 1.", "Port Error", JOptionPane.ERROR_MESSAGE);
                frameServer1.dispose();
                frameServer2.dispose(); 
                return;
            } else {
                System.out.println("Server 1 port: " + serverPort1);
            }

            if (serverPort2 <= 0) {
                JOptionPane.showMessageDialog(null, "Invalid port specified for Server 2.", "Port Error", JOptionPane.ERROR_MESSAGE);
                frameServer1.dispose();
                frameServer2.dispose(); 
                return;
            } else {
                System.out.println("Server 2 port: " + serverPort2);
            }

            if (!isPortAvailable(serverPort1)) {
                JOptionPane.showMessageDialog(null, "Port  " + serverPort1 + "  and  "+ serverPort2 + "  is already in use. Cannot start Server 1 and Server 2 .", "Port Error", JOptionPane.ERROR_MESSAGE);
                frameServer1.dispose(); 
                frameServer2.dispose(); 
            }

            if (!isPortAvailable(serverPort2)) {
                JOptionPane.showMessageDialog(null, "Port " + serverPort2 + " is already in use. Cannot start Server 2.", "Port Error", JOptionPane.ERROR_MESSAGE);
                frameServer1.dispose();
                frameServer2.dispose();
                return;
            }

            UDPServer server1 = new UDPServer(serverPort1);
            server1.setMessageListener(message -> {
                frameServer1.appendReceivedMessage(message);
            });
            new Thread(server1::startServer).start(); 

            UDPServer server2 = new UDPServer(serverPort2);
            server2.setMessageListener(message -> {
                frameServer2.appendReceivedMessage(message);
            });
            new Thread(server2::startServer).start(); 

            frameServer1.getButtonSend().addActionListener(e -> {
                String message = frameServer1.getMessage();
                if (message.isEmpty()) {
                    System.err.println("Please type a message in Server 1 frame before sending.");
                    return;
                }
                server1.sendMessageTo(message, frameServer1.getReceiverIP(), frameServer1.getServerPort());
            });

            frameServer2.getButtonSend().addActionListener(e -> {
                String message = frameServer2.getMessage();
                if (message.isEmpty()) {
                    System.err.println("Please type a message in Server 2 frame before sending.");
                    return;
                }
                server2.sendMessageTo(message, frameServer2.getReceiverIP(), frameServer2.getServerPort());
            });
        });
    }

    private static boolean isPortAvailable(int port) {
        try (DatagramSocket socket = new DatagramSocket(port)) {
            return true;
        } catch (SocketException e) {
            return false;
        }
    }
}
