package ar.edu.utn.frbb.tup.Utils;

import java.util.Scanner;

public class Input {
    protected static Scanner input = new Scanner(System.in);

    protected static void print(String text) {
        System.out.print(text);
    }

    protected static void printLn(String text) {
        System.out.println(text);
    }

    protected static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    protected static String readLn() {
        return input.nextLine();
    }

    protected static String read() {
        return input.next();
    }
}
