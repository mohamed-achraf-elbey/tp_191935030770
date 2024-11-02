package pk;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter 'server' to start the server or 'client' to start the client:");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("server")) {
            System.out.print("Enter port for the server: ");
            int port = scanner.nextInt();

            UDPServer server = new UDPServer(port);
            server.startServer();
        } else if (choice.equalsIgnoreCase("client")) {
            scanner.nextLine();  // consume newline

            System.out.print("Enter sender IP: ");
            String senderIP = scanner.nextLine();

            System.out.print("Enter receiver IP: ");
            String receiverIP = scanner.nextLine();

            System.out.print("Enter port for the server: ");
            int port = scanner.nextInt();
            scanner.nextLine();  // consume newline

            UDPClient client = new UDPClient(senderIP, receiverIP, port);

            while (true) {
                System.out.print("Enter message to send (type 'exit' to quit): ");
                String message = scanner.nextLine();

                if (message.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting client...");
                    break;
                }

                client.sendMessage(message);
            }
        } else {
            System.out.println("Invalid choice. Please enter 'server' or 'client'.");
        }

        scanner.close();
    }
}
