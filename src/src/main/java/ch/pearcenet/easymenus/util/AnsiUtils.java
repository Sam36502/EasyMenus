package ch.pearcenet.easymenus.util;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 * ANSI Utilities
 * Utility methods for fancy display
 */
public class AnsiUtils {

    /// Setup Methods ///

    /**
     * Installs the ANSI console onto standard out.
     */
    public static void installConsole() { AnsiConsole.systemInstall(); }

    /**
     * Uninstalls the ANSI console from standard out.
     */
    public static void uninstallConsole() { AnsiConsole.systemUninstall(); }

    /**
     * Loads console style settings from a file.
     * @param filename The file to load settings from
     */
    public static void loadStyleFile(final String filename) {
        STYLE_SETTINGS = new Properties();
        try {
            InputStream is = new FileInputStream(filename);
            STYLE_SETTINGS.load(is);
        } catch (IOException e) {
            // TODO: Competent Error-Handling
            System.out.println("Error: Unable to load settings from file: '" + filename + "'");
            System.exit(1);
        }
    }

    /// Settings Methods ///

    private static Properties STYLE_SETTINGS;

    /**
     * Gets a string from the settings map.
     * @param key The key of the requested string
     * @return The requested string
     */
    public static String getSettingsString(String key) {

        if (!STYLE_SETTINGS.containsKey(key)) {
            // TODO: Competent Error-Handling
            System.out.println("Error: Invalid Key requested from STYLE_SETTINGS: '" + key + "'");
            System.exit(1);
        }

        return STYLE_SETTINGS.getProperty(key);
    }

    /**
     * Gets an integer from the settings map.
     * @param key The key of the requested integer
     * @return The requested integer
     */
    public static int getSettingsInt(String key) {
        int i = 0;
        try {
            i = Integer.parseInt(getSettingsString(key));
        } catch (NumberFormatException e) {
            // TODO: Competent Error-Handling
            System.out.println("Error: Attempted to get non-integer value from STYLE_SETTINGS: '" + key + "' is actually: '" + getSettingsString(key) + "'");
            System.exit(1);
        }
        return i;
    }

    /// Basic Formatting Methods ///

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
     * Prints the given string in an ASCII-art box
     * with the given margins and width.
     * @param inStr The string to print
     * @param leftMargin The left margin of the box
     * @param topMargin The top margin of the box
     * @param width The width of the box
     */
    public static void printInBox(final String inStr, final int leftMargin, final int topMargin, final int width) {
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
                    lastInd = ind;
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

        printWithMargins(render, leftMargin, topMargin);
    }

    /**
     * Prints the given string in an ASCII-art box with the given
     * margin and with a given width.
     * @param inStr The string to print
     * @param leftMargin The left margin
     * @param width The width of the box
     */
    public static void printInBox(final String inStr, final int leftMargin, final int width) {
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
                    lastInd = ind;
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

        printWithMargins(render, leftMargin);
    }

    /**
     * Prints the given string in an ASCII-art box at the given coordinates,
     * with a title box over the main text box.
     * @param str String to put in the box
     * @param title Title to display above the box
     * @param leftMargin The left margin of the box
     * @param topMargin The top margin of the box
     */
    public static void printInBoxWithTitle(final String str, final String title, final int leftMargin, final int topMargin) {
        printInBoxWithTitle(str, title, leftMargin, topMargin, Constants.DEFAULT_TXT_WIDTH);
    }

    /**
     * Prints the given string in an ASCII-art box at the given coordinates
     * and a given width, with a title box over the main text box.
     * @param str String to put in the box
     * @param title Title to display above the box
     * @param leftMargin The left margin of the box
     * @param topMargin The top margin of the box
     * @param width Width of the box
     */
    public static void printInBoxWithTitle(final String str, final String title, final int leftMargin, final int topMargin, final int width) {

        // Check title will fit in title box
        String newtitle = title;
        int titleWidth = width / 2;
        if (title.length() > titleWidth) {
            newtitle = newtitle.substring(0, titleWidth - 3) + "...";
        }

        printInBox(newtitle, leftMargin, topMargin, titleWidth);
        for (int i=0; i<Constants.TITLE_CONTENT_GAP; i++) { System.out.print("\n"); }
        printInBox(str, leftMargin, width);
    }

}
