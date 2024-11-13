package pk;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {

    private void createServer(int initialCapacity) {
        Frame frame = new Frame(initialCapacity, false, -1, -1);
        frame.setVisible(true);
        frame.disableAllComponents();

        frame.getButtonConnect().addActionListener(e -> {
            int serverPort = frame.getConnectPort(); // Get the port from the frame
            if (serverPort <= 0) {
                //JOptionPane.showMessageDialog(null, "Invalid port specified.", "Port Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!isPortAvailable(serverPort)) {
               // JOptionPane.showMessageDialog(null, "Port " + serverPort + " is already in use. Cannot start server.", "Port Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            UDPServer server = new UDPServer(serverPort);
            frame.setText1_4("Server listening in port " + serverPort);
            frame.enableAllComponents();
            server.setMessageListener(message -> 
                SwingUtilities.invokeLater(() -> frame.appendReceivedMessage(message))
            );
            new Thread(server::startServer).start();

            frame.getButtonSend().addActionListener(event -> {
                String message = frame.getMessage();
                if (message.isEmpty()) {
                    System.err.println("Please type a message in the frame before sending.");
                    return;
                }

                String receiverIP = frame.getReceiverIP(); // Assuming receiver IP is specified in this frame
                int receiverPort = frame.getTextFieldPort();
                if (receiverPort <= 0) {
                   // JOptionPane.showMessageDialog(null, "Invalid port specified.", "Port listening Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                server.sendMessageTo(message, receiverIP, receiverPort);

              //  captureScreenshot(receiverIP); // Pass the receiver's IP address for file naming
            });
        });
    }

    private static boolean isPortAvailable(int port) {
        try (java.net.DatagramSocket socket = new java.net.DatagramSocket(port)) {
            return true;
        } catch (java.net.SocketException e) {
            return false;
        }
    }

   /* private void captureScreenshot(String receiverIP) {
        try {
            // Capture the full screen
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            Image screenshot = robot.createScreenCapture(screenRect);

            // Create a unique file name using the receiver's IP address
            String fileName = "C:\\Users\\Achraf\\git\\tp_191935030770\\TpGitHub\\screen\\" + receiverIP + ".png";
            File screenshotFile = new File(fileName);

            // Save the screenshot to a file
            ImageIO.write((java.awt.image.BufferedImage) screenshot, "png", screenshotFile);

            System.out.println("Screenshot captured and saved to: " + screenshotFile.getAbsolutePath());
        } catch (AWTException | IOException e) {
            e.printStackTrace();
            System.err.println("Error capturing screenshot.");
        }
    }*/

    public static void main(String[] args) {
        Main main = new Main();

        // Create two servers
        main.createServer(200);  
        main.createServer(1000);  
    }
}
