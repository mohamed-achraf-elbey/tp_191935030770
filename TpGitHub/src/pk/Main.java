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
                UDPServer server = new UDPServer(port);

                // Update GUI with received messages
                server.setMessageListener(message -> {
                    frameServer.setReceivedMessage(message);
                });

                server.startServer();
            }).start();

            // Start client in a new thread
            new Thread(() -> {
                String senderIP = frameClient.getSenderIP();
                String receiverIP = frameClient.getReceiverIP();
                int port = frameClient.getPort();

                UDPClient client = new UDPClient(senderIP, receiverIP, port);

                // Send message when button is clicked
                frameClient.getButtonSend().addActionListener(e -> {
                    String message = frameClient.getMessage();
                    client.sendMessage(message);
                });
            }).start();
        });
    }
}
