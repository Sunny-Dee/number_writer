package com.deliana.codingchallenge;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int Q_LENGTH = 5;
    public static final int PORT = 4000;

    public static void main(String[] args) {
        Socket sock;

        try {
            ServerSocket serverSocket = new ServerSocket(PORT, Q_LENGTH);

            try {

                System.out.println("Starting up server listening on port " + PORT);
                int connections = 0;
                while (true) {
                    sock = serverSocket.accept();
                    new Worker(sock).start();

                    connections++;
                    System.out.println("Just accepted a new connection. Total count: "
                            + connections);
                }
            } finally {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
