package ch.pearcenet.easymenus.util;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * ANSI Utilities
 * Utility methods for fancy display
 */
public class AnsiUtils {

    /// AnsiConsole Un-/Install ///
    public static void installConsole() { AnsiConsole.systemInstall(); }
    public static void uninstallConsole() { AnsiConsole.systemUninstall(); }

    ///Basic Formatting Methods ///

    /**
     * Clears the terminal
     */
    public static void clearScreen() {
        System.out.println(Ansi.ansi().eraseScreen().cursor(1, 1));
    }

    /**
     * Sets the cursor position.
     * @param row Screen row to move to
     * @param col Screen column to move to
     */
    public static void setCursorPos(final int row, final int col) {
        System.out.print(Ansi.ansi().cursor(row, col));
    }

    /**
     * Sets the cursor's colour to the default.
     */
    public static void resetCursorColour() {
        System.out.print(Ansi.ansi().bgDefault().fgDefault());
    }

    /**
     * Sets the cursor's colour.
     * @param fg Foreground colour (Text)
     * @param bg Background colour
     */
    public static void setCursorColour(final Colour fg, final Colour bg) {
        if (fg.isBright()) {
            System.out.print(Ansi.ansi().fgDefault().fgBright(fg.getAnsiColour()));
        } else {
            System.out.print(Ansi.ansi().fgDefault().fg(fg.getAnsiColour()));
        }

        if (bg.isBright()) {
            System.out.print(Ansi.ansi().bgDefault().bgBright(bg.getAnsiColour()));
        } else {
            System.out.print(Ansi.ansi().bgDefault().bg(bg.getAnsiColour()));
        }
    }

    /**
     * Moves a cursor relative to its current position.
     * Positive moves right/down; negative moves left/up.
     * @param x Number of columns to move left/right
     * @param y Number of columns to move up/down
     */
    public static void moveCursor(int x, int y) {
        if (x < 0) {
            System.out.print(Ansi.ansi().cursorLeft(Math.abs(x)));
        } else {
            System.out.print(Ansi.ansi().cursorRight(x));
        }
        if (y < 0) {
            System.out.print(Ansi.ansi().cursorUp(Math.abs(y)));
        } else {
            System.out.print(Ansi.ansi().cursorDown(y));
        }
    }

    /// Advanced Formatting Methods ///

    /**
     * Renders a string with an underline of hyphens
     * @param str String to be printed
     * @return The rendered string
     */
    public static String renderWithUnderline(final String str) {
        String render = "";
        render += " " + str + "\n--";
        for (int i=0; i<str.length(); i++) { render += "-"; }
        return render;
    }

    /**
     * Renders a string with an underline of given characters
     * @param str The string to underline
     * @param leftCap The string to cap the left side of the underline with
     * @param line The character to use for the underline
     * @param rightCap The string to cap the right side of the underline with
     * @return The rendered string
     */
    public static String renderWithUnderline(final String str, final String leftCap, final char line, final String rightCap) {
        String render = "";
        for (int i=0; i<leftCap.length(); i++) { render += " "; }
        render += str + "\n" + leftCap;
        for (int i=0; i<str.length(); i++) { render += line; }
        render += rightCap;
        return render;
    }

    /**
     * Prints a block of text offset from the left side of the screen.
     * @param str The text to print
     * @param leftMargin The number of columns to skip before writing
     */
    public static void printWithMargins(final String str, final int leftMargin) {
        String[] strs = str.split("\\n");
        System.out.print(Ansi.ansi().cursorToColumn(leftMargin));
        for (String s: strs) {
            System.out.print(s);
            System.out.print(Ansi.ansi().cursorDownLine().cursorToColumn(leftMargin));
        }
    }

    /**
     * Prints a block of text offset from the left and top side of the screen.
     * @param str The text to print
     * @param leftMargin The number of columns to skip before writing
     * @param topMargin The number of rows to skip before printing
     */
    public static void printWithMargins(final String str, final int leftMargin, final int topMargin) {
        String[] strs = str.split("\\n");
        setCursorPos(topMargin, leftMargin);
        for (String s: strs) {
            System.out.print(s);
            System.out.print(Ansi.ansi().cursorDownLine().cursorToColumn(leftMargin));
        }
    }

    /**
     * Prints the given string in an ASCII-art box at the given coordinates.
     * @param str The string to print
     * @param x The x-coordinate of the top left corner
     * @param y The y-coordinate of the top left corner
     */
    public static void printInBox(final String str, final int x, final int y) {
        printInBox(str, x, y, Constants.DEFAULT_TXT_WIDTH);
    }

    /**
     * Prints the given string in an ASCII-art box at the given coordinates
     * and with a given width.
     * @param inStr The string to print
     * @param x The x-coordinate of the top left corner
     * @param y The y-coordinate of the top left corner
     * @param width The width of the box
     */
    public static void printInBox(final String inStr, final int x, final int y, final int width) {
        // Split text into equal-length strings split on newlines.
        ArrayList<String> lines = new ArrayList<>();

        int ind=0;
        int lastInd = 0;
        while (ind <= inStr.length()) {

            if (ind == inStr.length()) {
                lines.add(inStr.substring(lastInd));
                break;
            }

            if (ind - lastInd >= width) {
                int oldInd = ind;
                boolean foundSplit = true;
                while (!Constants.LINE_BREAK_CHARACTERS.contains("" + inStr.charAt(ind))) {
                    ind--;
                    if (ind == lastInd) {
                        ind = oldInd;
                        foundSplit = false;
                        break;
                    }
                }
                if (foundSplit) {
                    ind++;
                    lines.add(inStr.substring(lastInd, ind));
                    lastInd = ind + 1;
                } else {
                    lines.add(inStr.substring(lastInd, ind) + '-');
                    lastInd = ind;
                }
            }

            if (inStr.charAt(ind) == '\n') {
                lines.add(inStr.substring(lastInd, ind));
                lastInd = ind + 1;
            }

            ind++;
        }

        // Place text in ASCII-art box (Border configuration in Constants)
        String render = "";
        render += Constants.DEFAULT_BORDER[0];
        for (int i=0; i<width + 2; i++) render += Constants.DEFAULT_BORDER[1];
        render += Constants.DEFAULT_BORDER[2] + "\n";

        for (String line: lines) {
            render += Constants.DEFAULT_BORDER[7] + " " + line;
            for (int i=0; i<width - line.length() + 1; i++) render += " ";
            render += Constants.DEFAULT_BORDER[3] + "\n";
        }

        render += Constants.DEFAULT_BORDER[6];
        for (int i=0; i<width + 2; i++) render += Constants.DEFAULT_BORDER[5];
        render += Constants.DEFAULT_BORDER[4];

        printWithMargins(render, x, y);
    }

    /**
     * Prints the given string in an ASCII-art box at the given coordinates,
     * with a title box over the main text box.
     * @param str String to put in the box
     * @param title Title to display above the box
     * @param x X-coordinate of the top left corner
     * @param y Y-coordinate of the top left corner
     */
    public static void printInBoxWithTitle(final String str, final String title, final int x, final int y) {
        printInBoxWithTitle(str, title, x, y, Constants.DEFAULT_TXT_WIDTH);
    }

    /**
     * Prints the given string in an ASCII-art box at the given coordinates
     * and a given width, with a title box over the main text box.
     * @param str String to put in the box
     * @param title Title to display above the box
     * @param x X-coordinate of the top left corner
     * @param y Y-coordinate of the top left corner
     * @param width Width of the box
     */
    public static void printInBoxWithTitle(final String str, final String title, final int x, final int y, final int width) {

        // Check title will fit in title box
        String newtitle = title;
        int titleWidth = width / 2;
        if (title.length() > titleWidth) {
            newtitle = newtitle.substring(0, titleWidth - 3) + "...";
        }

        printInBox(str, x, y + 2, width);
        printInBox(newtitle, x, y, titleWidth);
    }

}
