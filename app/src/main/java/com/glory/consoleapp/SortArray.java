package com.glory.consoleapp;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class SortArray {



    public static void main(String[] args) {

        int [] myArray = {10, 5, 20, 3, 0, 43};
        int max = 0;
        Arrays.sort(myArray);
        for(int x : myArray){
            System.out.println(x);
        }

        for(int i = 0; i< myArray.length; i++){
            if(myArray[i] > max){
               max = myArray[i];
            }
        }
        System.out.println( "The max value is " + max);
        System.out.println("Enter Height: ");
        Scanner input = new Scanner(System.in);
        int height = input.nextInt();

        if(height >= 50){
            System.out.println("you are welcome");
        }else{
            System.out.println("you are not welcome");
        }
    }


}
