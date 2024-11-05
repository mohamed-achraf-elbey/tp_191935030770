# UDP Messaging Application

This project implements a simple UDP messaging application with a graphical user interface (GUI) using Java Swing. The application allows two servers to send and receive messages over UDP.

## Features

- User-friendly GUI for entering sender and receiver details.
- Send messages over UDP to specified IP addresses and ports.
- Automatically fill in local IP addresses.
- Display incoming messages in the GUI.
- Open URLs in the default web browser if the received message is a URL.

## Components

### Frame Class
- Represents the main GUI for each server.
- Contains text fields for sender IP, server port, receiver IP, and message input.
- Buttons for sending messages and auto-filling local IP addresses.

### Main Class
- Entry point of the application.
- Validates port numbers and starts the servers.

### PortServer Class
- Provides a GUI for entering server port numbers.
- Checks if the ports are available before starting the servers.

### UDPServer Class
- Handles UDP server functionality.
- Listens for incoming messages and processes them.
- Sends messages to specified IP addresses and ports.

### Message Listener
- Interface used by both `Frame` and `UDPServer` classes to handle received messages and update the GUI.

## Getting Started

### Prerequisites
- Java Development Kit (JDK) installed on your machine.
- An IDE such as IntelliJ IDEA or Eclipse (optional but recommended).

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/mohamed-achraf-elbey/tp_191935030770.git

