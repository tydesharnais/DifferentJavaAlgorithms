package edu.miracosta.cs113.change;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class ChangeCalculator {

    private static ArrayList<int[]> coinCombos = new ArrayList<int[]>();
    private static ArrayList<int[]> temp = new ArrayList<int[]>();
    private static ArrayList<String> combinations = new ArrayList<>();

    public static int calculateChange(int cents) {

        System.out.println("\nPrinting all the possible combinations for " + cents + " cents: ");
        System.out.println("\n==========================================================");

        recCalculateChange(cents, 0, 0, 0, cents); //pennies start out as amount
        printCombos(coinCombos);

        //Create separate variable so list can be cleared and number of combos is saved and returned
        int numCombos = 0;

        for (int i = 0; i < coinCombos.size(); i++) {
            numCombos++;
        }

        System.out.println("==========================================================");
        System.out.println("\nTotal number of combinations: " + numCombos);

        temp.addAll(coinCombos);
        coinCombos.clear();

        return numCombos;


    }
    public static void printCombinationsToFile(int cents) {
        calculateChange(cents);

        BufferedWriter output = null;
        try {
            File file = new File("src/edu/miracosta/cs113/change/CoinCombinations.txt");
            output = new BufferedWriter(new FileWriter(file));

            for (int[] index : temp) {
                String str = "[ " + index[0] + "Q " + index[1] + "D " + index[2] + "N " + index[3] + "P ]\n";
                if(!combinations.contains(str)) {
                    combinations.add(str);
                    output.write(str);
                }
            }
            output.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e2) {
            e2.printStackTrace();
        }

    }
    public static void recCalculateChange(int total, int quarters, int dimes, int nickels, int pennies) {

        // Base Case
        if (quarters * 25 + dimes * 10 + nickels * 5 + pennies > total) {
            return;
        }

        int[] newCombo = {quarters, dimes, nickels, pennies};

        for (int[] combo : coinCombos) {
            if (Arrays.equals(newCombo, combo)) {
                return;
            }
        }
        coinCombos.add(newCombo);
        // Recursive Cases
        if (pennies >= 5) {
            recCalculateChange(total, quarters, dimes, nickels + 1, pennies - 5);
        }
        if (pennies >= 10) {
            recCalculateChange(total, quarters, dimes + 1, nickels, pennies - 10);
        }
        if (pennies >= 25) {
            recCalculateChange(total, quarters + 1, dimes, nickels, pennies - 25);
        }
    }
    public static void printCombos(ArrayList<int[]> list) {
        for (int[] index : list) {
            System.out.println("[ " + index[0] + "Q " + index[1] + "D " + index[2] + "N " + index[3] + "P ]");
        }
    }
} // End of class ChangeCalculator

