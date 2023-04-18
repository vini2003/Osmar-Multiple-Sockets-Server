package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static server.Main.SERVERS;

public class Server extends Thread {
    private final Socket socket;

    private final BufferedReader socketReader;
    private final PrintWriter socketWriter;

    public Server(Socket socket) {
        this.socket = socket;

        try {
            this.socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.socketWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        String message;

        socketWriter.println("Bem vindo!\n");

        while (true) {
            try {
                if ((message = socketReader.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            for (var server : SERVERS) {
                if (server != this) {
                    server.socketWriter.println(message);
                }
            }

            System.out.println(message);
        }
    }
}
