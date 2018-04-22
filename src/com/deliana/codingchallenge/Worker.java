package com.deliana.codingchallenge;

import java.io.*;
import java.net.Socket;
import java.util.Hashtable;

public class Worker extends Thread {
    private Socket sock;
    Hashtable<String, Integer> numbers;
    BufferedWriter writer;
    private final Object lock;
    private static final String TERMINATE = "terminate";
    boolean terminate;

    Worker(Socket sock, Hashtable<String, Integer> numbers,
           BufferedWriter writer, boolean terminate) {
        this.sock = sock;
        this.numbers = numbers;
        this.writer = writer;
        this.terminate = terminate;
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
