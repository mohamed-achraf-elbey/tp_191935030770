package pk;

import java.awt.EventQueue;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            // Initialize the Frame for client and server display
            Frame frameClient = new Frame(200);
            frameClient.setVisible(true);

            Frame frameServer = new Frame(1000);
            frameServer.setVisible(true);

            // Start server in a new thread
            new Thread(() -> {
                int port = frameServer.getPort();
                
                // Check if the port is valid
                if (port < 0) {
                    System.err.println("Invalid port specified for server.");
                    return; // Exit if the port is invalid
                }

                UDPServer server = new UDPServer(port);

                // Update GUI with received messages
                server.setMessageListener(message -> {
                    frameServer.setReceivedMessage(message);
                });

                server.startServer();
            }).start();

            // Start client in a new thread
            new Thread(() -> {
                frameClient.getButtonSend().addActionListener(e -> {
                    // Retrieve inputs from the GUI
                    String senderIP = frameClient.getSenderIP();
                    String receiverIP = frameClient.getReceiverIP();
                    int port = frameClient.getPort();
                    String message = frameClient.getMessage();

                    // Debugging: Print values to check if fields are filled correctly
                    System.out.println("Sender IP: " + senderIP);
                    System.out.println("Receiver IP: " + receiverIP);
                    System.out.println("Port: " + port);
                    System.out.println("Message: " + message);

                    // Validate inputs before sending
                    if (senderIP.isEmpty() || receiverIP.isEmpty() || message.isEmpty()) {
                        System.err.println("Please fill all fields before sending.");
                        return; // Exit if any field is invalid
                    }

                    // Check if the port is valid
                    if (port < 0) {
                        System.err.println("Invalid port specified for client.");
                        return; // Exit if the port is invalid
                    }

                    // Initialize the client and send the message
                    UDPClient client = new UDPClient(senderIP, receiverIP, port);
                    client.sendMessage(message);
                });
            }).start();
        });
    }
}
