package com.deliana.codingchallenge;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

public class Server {
    private static final int Q_LENGTH = 5;
    private static final int PORT = 4000;
    private static final int SLEEP_MILLISECONDS = 10000;
    private static final String LOGFILE = "logs.txt";
    private static volatile boolean terminate = false;
    private static Hashtable<String, Integer> numbers = new Hashtable<>();
    private static volatile int totalDuplicates = 0;

    public static void main(String[] args) {
        Socket sock;

        try {

            System.out.println("creating log file");
            BufferedWriter writer = new BufferedWriter(new FileWriter(LOGFILE));
            writer.write("Logs for numbers generated\n");
            writer.write("**************************\n");
            writer.flush();

            System.out.println("starting out reporter");
            new Reporter(numbers).start();

            try (ServerSocket serverSocket = new ServerSocket(PORT, Q_LENGTH); writer) {
                System.out.println("Starting up server listening on port " + PORT);
                while (!terminate) {
                    sock = serverSocket.accept();
                    new Worker(sock, numbers, writer).start();
                }

                System.out.println("Terminating program");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static class Worker extends Thread {
        private Socket sock;
        Hashtable<String, Integer> numbers;
        BufferedWriter writer;
        private final Object lock;
        private static final String TERMINATE = "terminate";

        Worker(Socket sock, Hashtable<String, Integer> numbers,
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

                if (inputNumber.equals(TERMINATE)) {
                    terminate = true;
                    out.println("Terminating program");
                    return;
                }

                if (numbers.containsKey(inputNumber)) {
                    synchronized (lock) {
                        totalDuplicates++;
                    }
                    out.println("Number already in table. Try again!");
                } else {

                    synchronized (lock) {
                        numbers.put(inputNumber, 1);
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

    static class Reporter extends Thread {
        Hashtable<String, Integer> numbers;
        private Hashtable<String, Integer> local;
        private int localDups;

        Reporter(Hashtable<String, Integer> numbers) {
            this.numbers = numbers;
            local = new Hashtable<>();
            local.putAll(numbers);
            localDups = 0;
        }

        private void makeReport() {
            if (local.equals(numbers) && totalDuplicates == localDups) {
                System.out.println("Nothing new to report:");
            } else {
                System.out.println("New nums have come to light");
                int newUniq = numbers.size() - local.size();
                int newDups = totalDuplicates - localDups;
                System.out.println("New unique: " + newUniq +
                        ". New duplicates: " + newDups);

                local.putAll(numbers);
                localDups = totalDuplicates;
            }
            System.out.println("Total unique: " + numbers.size() +
                    ". Total duplicates: " + totalDuplicates + "\n");
        }

        public void run() {
            while (!terminate) {
                makeReport();
                try {
                    Thread.sleep(SLEEP_MILLISECONDS);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
