package com.glory.consoleapp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BufferedExample {
    public static void main(String[] args) {
        String line = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader("example.txt"));
            while(br.readLine() != null){
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
