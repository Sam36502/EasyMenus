package ch.pearcenet.easymenus;

import ch.pearcenet.easymenus.util.AnsiUtils;
import ch.pearcenet.easymenus.util.Constants;
import org.fusesource.jansi.Ansi;

import java.util.Scanner;

/**
 * Input Handler
 * Handles all kinds of input
 * and validates them.
 */
public class Input {

    private static Scanner input;

    public static void openScanner() { input = new Scanner(System.in); }
    public static void closeScanner() { input.close(); }

    public static String getString() {
        System.out.println(Ansi.ansi().cursorToColumn(Constants.DEFAULT_INPUT_MARGIN) + "> ");
        return input.nextLine();
    }

    public static int getInt() {
        String in = getString();
        boolean isValid = false;
        int i = 0;

        while (!isValid) {
            isValid = true;

            try {
                i = Integer.parseInt(in);
            } catch (NumberFormatException ex) {
                isValid = false;
                System.out.println(Ansi.ansi().cursorToColumn(Constants.DEFAULT_INPUT_MARGIN) + "ERROR: Invalid number");
                in = getString();
            }
        }

        return i;
    }
    public static int getInt(final int min, final int max) {
        boolean isValid = false;
        int i = getInt();

        while (!isValid) {
            isValid = true;

            if (i < min || i > max) {
                isValid = false;
                System.out.println(Ansi.ansi().cursorToColumn(Constants.DEFAULT_INPUT_MARGIN) + "ERROR: Number out range");
                i = getInt();
            }
        }

        return i;
    }

}
