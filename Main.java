package server;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final List<Server> SERVERS = new ArrayList<>();

    public static void main(String[] args) {
        var port = Integer.parseInt(JOptionPane.showInputDialog("Enter server port: "));

        try {
            var socket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
            System.out.println("Waiting for clients...");

            while (true) {
                var clientSocket = socket.accept();
                var client = new Server(clientSocket);
                client.start();
                System.out.println("Client connected!");

                SERVERS.add(client);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
