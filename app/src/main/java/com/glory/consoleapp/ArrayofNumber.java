package com.glory.consoleapp;

import java.util.Arrays;

public class ArrayofNumber {
    public static void main(String[] args) {
        int [] numbers = {15,10,11,3,1,1,15};
        int occurence = 0;
        Arrays.sort(numbers);
        for(int i = 0; i< numbers.length; i++){
            occurence = i;
            Arrays.sort(numbers);
            System.out.println(occurence);
        }
    }



}
