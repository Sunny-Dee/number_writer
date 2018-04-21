package com.deliana.codingchallenge;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Set;

public class Server {
    public static final int Q_LENGTH = 5;
    public static final int PORT = 4000;

    public static void main(String[] args) {
        Socket sock;
        Hashtable<String, Integer> numbers = new Hashtable<String, Integer>();

        try {
            ServerSocket serverSocket = new ServerSocket(PORT, Q_LENGTH);

            try {

                System.out.println("Starting up server listening on port " + PORT);
                int connections = 0;
                while (true) {
                    sock = serverSocket.accept();
                    new Worker(sock, numbers).start();

                    connections++;
                    System.out.println("Just accepted a new connection. Total count: "
                            + connections);
                    Set<String> keys = numbers.keySet();
                    for (String key : keys){
                        System.out.println(key);
                    }
                }
            } finally {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
