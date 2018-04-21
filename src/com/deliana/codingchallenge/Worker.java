package com.deliana.codingchallenge;

import java.io.*;
import java.net.Socket;
import java.util.Hashtable;

public class Worker extends Thread {
    Socket sock;
    Hashtable<String, Integer> numbers;
    BufferedWriter writer;

    Worker(Socket sock, Hashtable<String, Integer> numbers, BufferedWriter writer) {
        this.sock = sock;
        this.numbers = numbers;
        this.writer = writer;
    }

    private String addLeadingZeros(String input) {
        if (input.length() < 9) {
            int need = 9 - input.length();
            String leadinZeros = new String(new char[need]).replace("\0", "0");
            input = leadinZeros + input;
        }
        return input;
    }


    public void run() {

        try {
            PrintStream out = new PrintStream(sock.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

            String inputNumber = in.readLine();

            if (numbers.containsKey(inputNumber)){
                out.println("Number already in table. Try again!");
            } else {
                numbers.put(inputNumber, 1);

                synchronized (this){
                    writer.write(addLeadingZeros(inputNumber));
                    writer.newLine();
                    writer.flush();
                }

                out.println("Got your input number: " + inputNumber +
                        " Saving big ol' number to a log");
            }

            sock.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
