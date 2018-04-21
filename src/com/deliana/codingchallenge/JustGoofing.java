package com.deliana.codingchallenge;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class JustGoofing {
    public static void main(String[] args) {
//        String regex = "\\d+";
//        String someBignum = "123-45678900";
//        System.out.println(someBignum.matches("\\d+"));
//
//        String input = "1234";
//        System.out.println(input);
//
//
//        if (input.length() < 9) {
//            int need = 9 - input.length();
//            String leadinZeros = new String(new char[need]).replace("\0", "0");
//            input = leadinZeros + input;
//        }
//        System.out.println(input);

        // The name of the file to open.
        String fileName = "temp.txt";

        try {
            // Assume default encoding.
            FileWriter fileWriter =
                    new FileWriter(fileName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                    new BufferedWriter(fileWriter);

            // Note that write() does not automatically
            // append a newline character.
            bufferedWriter.write("Hello there,");
            bufferedWriter.write(" here is some text.");
            bufferedWriter.newLine();
            bufferedWriter.write("We are writing");
            bufferedWriter.write(" the text to the file.");

            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                    "Error writing to file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }

    }
}
