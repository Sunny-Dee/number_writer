package com.deliana.codingchallenge;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

public class Server {
    private static final int Q_LENGTH = 5;
    private static final int PORT = 4000;
    private static final String LOGFILE = "logs.txt";
    public static volatile boolean terminate = false;
    private static Hashtable<String, Integer> numbers;

    public static void main(String[] args) {
        Socket sock;
        numbers = new Hashtable<>();

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
                while (!terminate) {
                    sock = serverSocket.accept();
                    new Worker2(sock, numbers, writer).start();

                    connections++;
                    System.out.println("Just accepted a new connection on worker 2. Total count: "
                            + connections);
                }

                System.out.println("Terminating program");
            } finally {
                serverSocket.close();
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static class Worker2 extends Thread {
        private Socket sock;
        Hashtable<String, Integer> numbers;
        BufferedWriter writer;
        private final Object lock;
        private static final String TERMINATE = "terminate";

        Worker2(Socket sock, Hashtable<String, Integer> numbers,
               BufferedWriter writer) {
            this.sock = sock;
            this.numbers = numbers;
            this.writer = writer;
            lock = new Object();
        }

        private String addLeadingZeros(String input) {
            if (input.length() < 9) {
                int need = 9 - input.length();
                String leadingZeros = new String(new char[need]).replace("\0", "0");
                input = leadingZeros + input;
            }
            return input;
        }


        public void run() {

            try {
                PrintStream out = new PrintStream(sock.getOutputStream());
                BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

                String inputNumber = in.readLine();

                if (inputNumber.equals(TERMINATE)){
                    terminate = true;
                    out.println("Terminating program");
                    return;
                }

                if (numbers.containsKey(inputNumber)){
                    out.println("Number already in table. Try again!");
                } else {
                    numbers.put(inputNumber, 1);

                    synchronized (lock){
                        writer.write(addLeadingZeros(inputNumber));
                        writer.newLine();
                        writer.flush();
                    }

                    out.println("Got your input number: " + inputNumber +
                            ". Saving big ol' number to a log");
                }

                sock.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
