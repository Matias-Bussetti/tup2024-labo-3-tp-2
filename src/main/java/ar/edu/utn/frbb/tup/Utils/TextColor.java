package ar.edu.utn.frbb.tup.Utils;

public class TextColor {
    private static String ANSI_RESET = "\u001B[0m";
    private static String ANSI_BLACK = "\u001B[30m";
    private static String ANSI_RED = "\u001B[31m";
    private static String ANSI_GREEN = "\u001B[32m";
    private static String ANSI_YELLOW = "\u001B[33m";
    private static String ANSI_BLUE = "\u001B[34m";
    private static String ANSI_PURPLE = "\u001B[35m";
    private static String ANSI_CYAN = "\u001B[36m";
    private static String ANSI_WHITE = "\u001B[37m";

    public static String reset() {
        return ANSI_RESET;
    }

    public static String inBlack(String text) {
        return ANSI_BLACK + text + reset();
    }

    public static String inRed(String text) {
        return ANSI_RED + text + reset();
    }

    public static String inGreen(String text) {
        return ANSI_GREEN + text + reset();
    }

    public static String inYellow(String text) {
        return ANSI_YELLOW + text + reset();
    }

    public static String inBlue(String text) {
        return ANSI_BLUE + text + reset();
    }

    public static String inPurple(String text) {
        return ANSI_PURPLE + text + reset();
    }

    public static String inCyan(String text) {
        return ANSI_CYAN + text + reset();
    }

    public static String inWhite(String text) {
        return ANSI_WHITE + text + reset();
    }
}
