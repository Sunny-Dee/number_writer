package com.deliana.codingchallenge;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Set;

public class Server {
    public static final int Q_LENGTH = 5;
    public static final int PORT = 4000;
    public static final String LOGFILE = "logs.txt";

    private static Hashtable<String, Integer> numbers;

//    public static void makeLog(){
//        try {
//            System.out.println("creating log file");
//            writer = new BufferedWriter(new FileWriter(LOGFILE));
//            writer.write("Logs for numbers generated");
//            writer.write("**************************");
//            writer.newLine();
//            System.out.println("log file created");
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) {
        Socket sock;
        numbers = new Hashtable<>();
//        makeLog();

        try {
            ServerSocket serverSocket = new ServerSocket(PORT, Q_LENGTH);

            System.out.println("creating log file");
            BufferedWriter writer = new BufferedWriter(new FileWriter(LOGFILE));
            writer.write("Logs for numbers generated\n");
            writer.write("**************************\n");
            writer.flush();
            System.out.println("log file created");


            try {

                System.out.println("Starting up server listening on port " + PORT);
                int connections = 0;
                while (true) {
                    sock = serverSocket.accept();
                    new Worker(sock, numbers, writer).start();

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
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
