package pk;

import java.awt.EventQueue;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            // Initialize the frames for both servers
            Frame frameServer1 = new Frame(200, false);
            frameServer1.setVisible(true);

            Frame frameServer2 = new Frame(1000, true);
            frameServer2.setVisible(true);

            // Set up two server instances with different ports
            int serverPort1 = 8888;
            int serverPort2 = 9999;

            // Check for valid ports
            if (serverPort1 <= 0 || serverPort2 <= 0) {
                System.err.println("Invalid port specified for one of the servers.");
                return;
            } else {
                System.out.println("Server 1 port: " + serverPort1);
                System.out.println("Server 2 port: " + serverPort2);
            }

            // Create the first UDPServer instance and start it in a new thread
            UDPServer server1 = new UDPServer(serverPort1);
            server1.setMessageListener(message -> {
                // Update the received message on the first server's frame
                frameServer1.appendReceivedMessage(message);
            });
            new Thread(server1::startServer).start(); // Start server 1

            // Create the second UDPServer instance and start it in a new thread
            UDPServer server2 = new UDPServer(serverPort2);
            server2.setMessageListener(message -> {
                // Update the received message on the second server's frame
                frameServer2.appendReceivedMessage(message);
            });
            new Thread(server2::startServer).start(); // Start server 2

            // Server 1 "Send" button functionality to send messages to Server 2
            frameServer1.getButtonSend().addActionListener(e -> {
                String message = frameServer1.getMessage();
                if (message.isEmpty()) {
                    System.err.println("Please type a message in Server 1 frame before sending.");
                    return;
                }
                // Server 1 sends a message to Server 2
                server1.sendMessageTo(message, "localhost", serverPort2);
            });

            // Server 2 "Send" button functionality to send messages to Server 1
            frameServer2.getButtonSend().addActionListener(e -> {
                String message = frameServer2.getMessage();
                if (message.isEmpty()) {
                    System.err.println("Please type a message in Server 2 frame before sending.");
                    return;
                }
                // Server 2 sends a message to Server 1
                server2.sendMessageTo(message, "localhost", serverPort1);
            });
        });
    }
}
