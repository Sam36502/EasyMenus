package ch.pearcenet.easymenus.util;

import ch.pearcenet.easymenus.util.AnsiUtils;
import ch.pearcenet.easymenus.util.Constants;
import org.fusesource.jansi.Ansi;

import java.util.Scanner;

/**
 * Input Handler
 * Handles all kinds of input
 * and validates them.
 */
public class InputUtils {

    private static Scanner input;

    public static void openScanner() { input = new Scanner(System.in); }
    public static void closeScanner() { input.close(); }

    /**
     * Waits until the enter key is pressed
     */
    public static void waitForEnter() {
        input.nextLine();
    }

    /**
     * Prompts the user for input and returns it.
     * @return The string the user entered
     */
    public static String getString() {
        if (input == null) { openScanner(); }

        System.out.print(
                Ansi.ansi().cursorToColumn(AnsiUtils.getSettingsInt(Constants.LAYOUT_INPUT_MARGIN_LEFT))
                        + "> ");
        return input.nextLine();
    }

    /**
     * Prompts the user for an integer and
     * handles any invalid input.
     * @return The integer the user entered
     */
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
                System.out.println(
                        Ansi.ansi().cursorToColumn(AnsiUtils.getSettingsInt(Constants.LAYOUT_INPUT_MARGIN_LEFT))
                                + "ERROR: Invalid number");
                in = getString();
            }
        }

        return i;
    }

    /**
     * Prompts the user for an integer within a range and
     * handles any invalid or out-of-range input.
     * @param min Minimum allowed input value
     * @param max Maximum allowed input value
     * @return The integer the user entered
     */
    public static int getInt(final int min, final int max) {
        boolean isValid = false;
        int i = getInt();

        while (!isValid) {
            isValid = true;

            if (i < min || i > max) {
                isValid = false;
                System.out.print(
                        Ansi.ansi().cursorToColumn(AnsiUtils.getSettingsInt(Constants.LAYOUT_INPUT_MARGIN_LEFT))
                                + "ERROR: Number out range");
                i = getInt();
            }
        }

        return i;
    }

    /**
     * Prompts the user with a yes/no prompt
     * @return If the 'yes' answer was received
     */
    public static boolean getBool() {
        System.out.println(
                Ansi.ansi().cursorToColumn(AnsiUtils.getSettingsInt(Constants.LAYOUT_INPUT_MARGIN_LEFT))
                        + "(y/n)");

        String in = getString();
        boolean isValid = false;
        boolean answer = false;

        while (!isValid) {

            if ("yes".equals(in) || "y".equals(in)) {
                isValid = true;
                answer = true;
            }

            if ("no".equals(in) || "n".equals(in)) {
                isValid = true;
                answer = false;
            }

            if (isValid == false) {
                System.out.println(
                        Ansi.ansi().cursorToColumn(AnsiUtils.getSettingsInt(Constants.LAYOUT_INPUT_MARGIN_LEFT))
                                + "ERROR: Invalid answer; answer must be 'yes' or 'no'.");
                System.out.println(
                        Ansi.ansi().cursorToColumn(AnsiUtils.getSettingsInt(Constants.LAYOUT_INPUT_MARGIN_LEFT))
                                + "(y/n)");
                in = getString();
            }
        }

        return answer;
    }

}
