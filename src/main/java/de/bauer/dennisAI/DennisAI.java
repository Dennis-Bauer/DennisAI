package de.bauer.dennisAI;

import java.util.Arrays;
import java.util.Random;

public class DennisAI {

    private static final int[][] numbers = {
        { // 0
            0, 1, 1, 1, 0,
            1, 0, 0, 0, 1,
            1, 0, 0, 0, 1,
            1, 0, 0, 0, 1,
            0, 1, 1, 1, 0
        },
        { // 1
            0, 0, 1, 0, 0,
            0, 1, 1, 0, 0,
            0, 0, 1, 0, 0,
            0, 0, 1, 0, 0,
            0, 1, 1, 1, 0
        },
        { // 2
            0, 1, 1, 1, 0,
            1, 0, 0, 0, 1,
            0, 0, 0, 1, 0,
            0, 0, 1, 0, 0,
            1, 1, 1, 1, 1
        },
        { // 3
            1, 1, 1, 1, 0,
            0, 0, 0, 0, 1,
            0, 0, 1, 1, 0,
            0, 0, 0, 0, 1,
            1, 1, 1, 1, 0
        },
        { // 4
            0, 0, 0, 1, 0,
            0, 0, 1, 1, 0,
            0, 1, 0, 1, 0,
            1, 1, 1, 1, 1,
            0, 0, 0, 1, 0
        },
        { // 5
            1, 1, 1, 1, 1,
            1, 0, 0, 0, 0,
            1, 1, 1, 1, 0,
            0, 0, 0, 0, 1,
            1, 1, 1, 1, 0
        },
        { // 6
            0, 0, 1, 1, 0,
            0, 1, 0, 0, 0,
            1, 1, 1, 1, 0,
            1, 0, 0, 0, 1,
            0, 1, 1, 1, 0
        },
        { // 7
            1, 1, 1, 1, 1,
            0, 0, 0, 0, 1,
            0, 0, 0, 1, 0,
            0, 0, 1, 0, 0,
            0, 0, 1, 0, 0
        },
        { // 8
            0, 1, 1, 1, 0,
            1, 0, 0, 0, 1,
            0, 1, 1, 1, 0,
            1, 0, 0, 0, 1,
            0, 1, 1, 1, 0
        },
        { // 9
            0, 1, 1, 1, 0,
            1, 0, 0, 0, 1,
            0, 1, 1, 1, 1,
            0, 0, 0, 0, 1,
            0, 1, 1, 1, 0
        },
    };

    private static final int screenSize = 5;

    private static double[] weights = new double[screenSize * screenSize];

    private static double bais = 0;

    private static final double learningRate = 0.1;

    // -- -- -- --

    private static final Random random = new Random();

    public static void main(String[] args) {
        Arrays.fill(weights, 0.1);

        // printAllNumbers();
        
        int runs = 100000;
        for (int r = 0; r < runs; r++) {
            learn();

            printProgressBar(r, runs);
        }

        System.out.println("###");
        System.out.println("Bais: " + bais);
        System.out.println("Weights: " + Arrays.toString(weights));
        System.out.println("###");

        for (int r = 0; r < 5; r++) {
            int number = random.nextInt(10);

            System.out.println("###");
            System.out.println("Number Picked: " + number);
            System.out.println("Result: " + run(numbers[number]));
            System.out.println("###");
        }

        int[] ownNumber = { // 0
            0, 2, 2, 2, 0,
            2, 0, 0, 2, 0,
            0, 0, 2, 0, 0,
            0, 2, 0, 0, 0,
            2, 2, 2, 2, 2
        };

        System.out.println("###");
        System.out.println("Own number");
        System.out.println("Number Picked: " + 2);
        System.out.println("Result: " + run(ownNumber));
        System.out.println("###");
    }

    private static double run(int[] givenNum) {

        double result = bais;

        for (int i = 0; i < givenNum.length; i++) {
            result += weights[i] * (double) givenNum[i];               
        }

        return result;
    }

    private static void learn() {
      
        int number = random.nextInt(10);
        int[] numberPixels = numbers[number]; 

        double result = bais;

        for (int i = 0; i < numberPixels.length; i++) {
            result += weights[i] * (double) numberPixels[i];               
        }

        double error = number - result;

        for (int i = 0; i < weights.length; i++) {
            weights[i] += learningRate * error * (double) numberPixels[i];
        }

        bais += learningRate * error;
    }

    private static void printAllNumbers() {
        for (int x = 0; x < 10; x++) {
            System.out.println("Number " + x);
            printNumber(numbers[x]);
            System.out.println("--- --- --- --- --- ---");
        }
    }

    private static void printNumber(int[] number) {
        StringBuilder s = new StringBuilder();
        for (int i = 0, j = 0; i < number.length; i++, j++) {
            int num = number[i];

            if (num == 1) s.append("█");
            else s.append(" ");
            
            if (j == screenSize - 1) {
                System.out.println(s.toString());
                j = -1;
                s = new StringBuilder();
            }

        }
    }

    private static void printProgressBar(int current, int total) {
        double progress = (double) current / total;
        int filled = (int) (progress * 50);

        StringBuilder bar = new StringBuilder("\r[");
        for (int i = 0; i < 50; i++) {
            bar.append(i < filled ? "█" : " ");
        }
        bar.append("] ");
        bar.append(String.format("%6.2f%%", progress * 100));

        System.out.print(bar);
    }
}
