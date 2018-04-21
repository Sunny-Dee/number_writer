package com.deliana.codingchallenge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Hashtable;

public class Worker extends Thread {
    Socket sock;
    Hashtable<String, Integer> numbers;

    Worker(Socket sock, Hashtable<String, Integer> numbers) {
        this.sock = sock;
        this.numbers = numbers;
    }


    public void run() {

        try {
            PrintStream out = new PrintStream(sock.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

            String inputNumber = in.readLine();

            if (numbers.containsKey(inputNumber)){
                out.println("Number already in table.");
            } else {
                numbers.put(inputNumber, 1);
                out.println("Got your input number: " + inputNumber +
                        " Saving big ol' number to a log");
            }


//            synchronized (fw) {
//                fw.write
//            }

            sock.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
