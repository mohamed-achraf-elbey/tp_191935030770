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

            // Set up the server instance with a specified port
            int serverPort = frameServer.getPort();
            if (serverPort <= 0) {
                System.err.println("Invalid port specified for server.");
                return;
            }
            
            // Create the UDPServer instance and start it in a new thread
            UDPServer server = new UDPServer(serverPort);
            server.setMessageListener(message -> {
                // Update the received message on the server's frame
                frameServer.setReceivedMessage(message);
            });
            
            new Thread(server::startServer).start(); // Start the server to listen for incoming messages

            // Client "Send" button functionality
            frameClient.getButtonSend().addActionListener(e -> {
                String senderIP = frameClient.getSenderIP();
                String receiverIP = frameClient.getReceiverIP();
                int port = frameClient.getPort();
                String message = frameClient.getMessage();

                if (senderIP.isEmpty() || receiverIP.isEmpty() || message.isEmpty()) {
                    System.err.println("Please fill all fields before sending.");
                    return;
                }

                // Initialize and set up the client
                UDPClient client = new UDPClient(senderIP, receiverIP, serverPort, 6000);
                client.setMessageListener(response -> {
                    frameClient.setReceivedMessage(response); // Display server response on client UI
                });
                
                client.startReceiving(); // Start client thread to listen for responses
                client.sendMessage(message); // Send initial message from client to server
            });

            // Server "Send" button functionality (to respond back to the client)
            frameServer.getButtonSend().addActionListener(e -> {
                String message = frameServer.getMessage();
                if (message.isEmpty()) {
                    System.err.println("Message field is empty. Please type a message.");
                    return;
                }

                // Use the existing server instance to send a response back to the client
                server.sendMessage(message);
            });
        });
    }
}
