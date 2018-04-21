package com.deliana.codingchallenge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Worker extends Thread {
    Socket sock;

    Worker(Socket s) {
        this.sock = s;
    }


    private boolean validateInput(String input){
        if (!input.matches("\\d+") || input.length() > 9){
            return false;
        }
        return true;
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

//            while (!validateInput(inputNumber)) {
////                out.println("Please enter 1-9 digits");
////                inputNumber = in.readLine();
////            }

//            boolean correctInput = false;
//            while(true) {
//                out.println("Please enter 1-9 digits");
//                inputNumber = sc.next();
//                out.println(inputNumber);

//                correctInput = validateInput(inputNumber);
//            }


//            inputNumber = addLeadingZeros(inputNumber);
            out.println("Got your input number: " + inputNumber);
            out.println("Saving big ol' number to a log");

            sock.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
