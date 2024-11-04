package pk;

import java.awt.EventQueue;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            // Initialize the Frame for client and server display
            Frame frameClient = new Frame(200 , true);
            frameClient.setVisible(true);

            Frame frameServer = new Frame(1000 , false);
            frameServer.setVisible(true);

            // Set up the server instance with a specified port
            int serverPort = 8888;
            if (serverPort <= 0) {
                System.err.println("Invalid port specified for server.");
                return;
            }else {
            	System.out.println("port server : "+serverPort);
            }
            
            // Create the UDPServer instance and start it in a new thread
            UDPServer server = new UDPServer(serverPort);
            server.setMessageListener(message -> {
                // Update the received message on the server's frame
                frameServer.appendReceivedMessage(message);
            });
            
            new Thread(server::startServer).start(); // Start the server to listen for incoming messages

            // Initialize the client once and start receiving messages
            UDPClient client = new UDPClient(frameClient.getSenderIP().toString(), frameClient.getReceiverIP().toString(), serverPort, 6000);
            
            // Set message listener for client to update the client frame when a message is received
            client.setMessageListener(message -> {
                frameClient.appendReceivedMessage(message); // Append received message to client frame
            });
            
            client.startReceiving(); // Start listening for incoming messages
            
            // Client "Send" button functionality
            frameClient.getButtonSend().addActionListener(e -> {
                String message = frameClient.getMessage();
                if (message.isEmpty()) {
                    System.err.println("Please fill in the message field before sending.");
                    return;
                }
                
                // Send message from client to server
                client.sendMessage(message);
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
