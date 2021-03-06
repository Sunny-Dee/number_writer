package com.deliana.codingchallenge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final int PORT = 4000;
    private static final String TERMINATE = "terminate";

    private static String parseHostName(String[] args) {
        if (args.length < 1) {
            return "localhost";
        }

        return args[0];
    }

    private static boolean validateInput(String input) {
        return input.matches("\\d+") && input.length() <= 9;
    }

    private static void storeInput(String input, String hostname) {
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void terminate(String hostname) {
        try {
            Socket sock = new Socket(hostname, PORT);
            PrintStream toServer = new PrintStream(sock.getOutputStream());
            toServer.println(TERMINATE);
            toServer.flush();
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String hostName = parseHostName(args);
        System.out.flush();

        Scanner in = new Scanner(System.in);
        System.out.println("Please enter 1-9 digits");
        String inputNumber = in.next();

        if (inputNumber.toLowerCase().equals(TERMINATE)) {
            terminate(hostName);
            return;
        }

        if (validateInput(inputNumber)){
            storeInput(inputNumber, hostName);
        }
    }

}
