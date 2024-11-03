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

                if (port < 0) {
                    System.err.println("Invalid port specified for server.");
                    return;
                }

                UDPServer server = new UDPServer(port);

                // Set the server to update its frame with received messages
                server.setMessageListener(message -> {
                    // Update the received message on the server's frame
                    frameServer.setReceivedMessage(message);
                });

                server.startServer();
            }).start();

            // Start client in a new thread
         // Start client in a new thread
         // Start client in a new thread
            new Thread(() -> {
                frameClient.getButtonSend().addActionListener(e -> {
                    String senderIP = frameClient.getSenderIP();
                    String receiverIP = frameClient.getReceiverIP();
                    int port = frameClient.getPort();
                    String message = frameClient.getMessage();

                    System.out.println("Sender IP: " + senderIP);
                    System.out.println("Receiver IP: " + receiverIP);
                    System.out.println("Port: " + port);
                    System.out.println("Message: " + message);

                    if (senderIP.isEmpty() || receiverIP.isEmpty() || message.isEmpty()) {
                        System.err.println("Please fill all fields before sending.");
                        return;
                    }

                    if (port < 0) {
                        System.err.println("Invalid port specified for client.");
                        return;
                    }

                    // Initialize the client
                    UDPClient client = new UDPClient(senderIP, receiverIP,frameServer.getPort(), 6000);

                    // Set up the message listener to display received responses in the client UI
                    client.setMessageListener(response -> {
                        frameClient.setReceivedMessage(response);
                    });

                    // Start listening for incoming messages on the client
                    client.startReceiving();

                    // Send the initial message from the client to the server
                    client.sendMessage(message);
                });
            }).start();



            // Start server-to-client response in another thread
            new Thread(() -> {
                frameServer.getButtonSend().addActionListener(e -> {
                    String message = frameServer.getMessage();

                    if (message.isEmpty()) {
                        System.err.println("Message field is empty. Please type a message.");
                        return;
                    }

                    // Send the server's response back to the client
                    UDPServer server = new UDPServer(frameServer.getPort());
                    server.sendMessage(message);
                    System.out.println("Server message sent to client: " + message);
                });
            }).start();
        });
    }
}
