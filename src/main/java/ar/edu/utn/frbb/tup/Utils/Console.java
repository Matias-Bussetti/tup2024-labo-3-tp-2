package ar.edu.utn.frbb.tup.Utils;

public class Console extends Input {
    public static void out(String text) {
        print(text);
    }

    public static void outLn(String text) {
        printLn(text);
    }

    public static String inLn() {
        return readLn();
    }

    public static String in() {
        return read();
    }

    public static void clearScreen() {
        Input.clearScreen();
    }
}
