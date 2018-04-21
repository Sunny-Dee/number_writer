package com.deliana.codingchallenge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class Client {
    public static final int PORT = 4000;

    private static String parseHostName(String[] args) {
        if (args.length < 1) {
            return "localhost";
        }

        return args[0];
    }

    private boolean validateInput(String input){
        if (!input.matches("\\d+") || input.length() > 9){
            return false;
        }
        return true;
    }

    private static String addLeadingZeros(String input) {
        if (input.length() < 9) {
            int need = 9 - input.length();
            String leadinZeros = new String(new char[need]).replace("\0", "0");
            input = leadinZeros + input;
        }
        return input;
    }

    public static void storeInput(String input, String hostname) {
        Socket sock;
        BufferedReader fromServer;
        PrintStream toServer;
        String textFromServer;

        try {
            sock = new Socket(hostname, PORT);

            fromServer = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            toServer = new PrintStream(sock.getOutputStream());

            toServer.println(input);
            toServer.flush();

            textFromServer = fromServer.readLine();
            if (textFromServer != null)
                System.out.println(textFromServer);

            sock.close();

        }  catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
       String hostName = parseHostName(args);

        System.out.println("Feeling a connection...");

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));


        System.out.println("Please enter a number from 1-9");
        System.out.flush();
        try {
            String number = in.readLine(); // TODO get input update to 9 digit num later
            number = addLeadingZeros(number);
            storeInput(number, hostName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
