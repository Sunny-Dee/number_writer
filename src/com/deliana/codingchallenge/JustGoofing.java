package com.deliana.codingchallenge;

public class JustGoofing {
    public static void main(String[] args) {
        String regex = "\\d+";
        String someBignum = "123-45678900";
        System.out.println(someBignum.matches("\\d+"));

        String input = "1234";
        System.out.println(input);


        if (input.length() < 9) {
            int need = 9 - input.length();
            String leadinZeros = new String(new char[need]).replace("\0", "0");
            input = leadinZeros + input;
        }
        System.out.println(input);
    }
}
