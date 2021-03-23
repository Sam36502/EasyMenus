package ch.pearcenet.easymenus.util;

import ch.pearcenet.easymenus.pages.Page;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.FileInputStream;
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

    private static String COL_TEXT_STR;
    private static String COL_DECO_STR;

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
            loadDefaultStyle();
            printWithMargins(
                    renderColourString(
                    getSettingsColour(Constants.COLOUR_ERROR_FG),
                    getSettingsColour(Constants.COLOUR_ERROR_BG)
                    ) +
                    "\n\nERROR: Unable to load style file '" + filename + "'.\n" +
                    "Would you like to use the default style instead?",
                    getSettingsInt(Constants.LAYOUT_INPUT_MARGIN_LEFT));
            boolean cont = InputUtils.getBool();
            if (!cont) {
                setCursorColour(
                        getSettingsColour(Constants.COLOUR_ERROR_FG),
                        getSettingsColour(Constants.COLOUR_ERROR_BG)
                );
                printWithMargins(
                        renderColourString(
                            getSettingsColour(Constants.COLOUR_ERROR_FG),
                            getSettingsColour(Constants.COLOUR_ERROR_BG)
                        ) +
                        "\nExiting...\n" +
                        "Please inform the developers, if possible.",
                        getSettingsInt(Constants.LAYOUT_INPUT_MARGIN_LEFT));
                System.exit(1);
            }
            clearScreen();
        }

        COL_TEXT_STR = renderColourString(
                getSettingsColour(Constants.COLOUR_TEXT_FG),
                getSettingsColour(Constants.COLOUR_TEXT_BG)
        );
        COL_DECO_STR = renderColourString(
                getSettingsColour(Constants.COLOUR_DECO_FG),
                getSettingsColour(Constants.COLOUR_DECO_BG)
        );
    }

    /// Settings Methods ///

    private static Properties STYLE_SETTINGS;

    public static void loadDefaultStyle() {
        STYLE_SETTINGS = new Properties();
        STYLE_SETTINGS.put(Constants.STYLE_BORDER_CHARSTR, "+-+|+-+|");
        STYLE_SETTINGS.put(Constants.STYLE_LINE_BRK_CHARS, " -");

        STYLE_SETTINGS.put(Constants.LAYOUT_TEXT_WIDTH, "100");
        STYLE_SETTINGS.put(Constants.LAYOUT_TITLE_CONTENT_GAP, "1");
        STYLE_SETTINGS.put(Constants.LAYOUT_CONTENT_PROMPT_GAP, "1");
        STYLE_SETTINGS.put(Constants.LAYOUT_LOAD_MARGIN_SIDE, "6");
        STYLE_SETTINGS.put(Constants.LAYOUT_LOAD_MARGIN_TOP, "3");
        STYLE_SETTINGS.put(Constants.LAYOUT_PAGE_MARGIN_LEFT, "6");
        STYLE_SETTINGS.put(Constants.LAYOUT_PAGE_MARGIN_TOP, "3");
        STYLE_SETTINGS.put(Constants.LAYOUT_MENU_TITLE_MAX_WIDTH, "50");
        STYLE_SETTINGS.put(Constants.LAYOUT_INPUT_MARGIN_LEFT, "6");
        STYLE_SETTINGS.put(Constants.LAYOUT_POPUP_X, "18");
        STYLE_SETTINGS.put(Constants.LAYOUT_POPUP_Y, "9");
        STYLE_SETTINGS.put(Constants.LAYOUT_POPUP_WIDTH, "25");

        STYLE_SETTINGS.put(Constants.COLOUR_TEXT_FG, "WHITE");
        STYLE_SETTINGS.put(Constants.COLOUR_TEXT_BG, "BLACK");
        STYLE_SETTINGS.put(Constants.COLOUR_DECO_FG, "WHITE");
        STYLE_SETTINGS.put(Constants.COLOUR_DECO_BG, "BLACK");
        STYLE_SETTINGS.put(Constants.COLOUR_ERROR_FG, "BRIGHT_RED");
        STYLE_SETTINGS.put(Constants.COLOUR_ERROR_BG, "BLACK");
        STYLE_SETTINGS.put(Constants.COLOUR_PROMPT_FG, "WHITE");
        STYLE_SETTINGS.put(Constants.COLOUR_PROMPT_BG, "BLACK");
        STYLE_SETTINGS.put(Constants.COLOUR_LOADING_FG, "GREEN");
        STYLE_SETTINGS.put(Constants.COLOUR_LOADING_BG, "BLACK");

        STYLE_SETTINGS.put(Constants.MARKUP_HIGHLIGHT_FG, "BLACK");
        STYLE_SETTINGS.put(Constants.MARKUP_HIGHLIGHT_BG, "YELLOW");

        STYLE_SETTINGS.put(Constants.STRINGS_LOADING_DEF_MSG, "Loading...");
        STYLE_SETTINGS.put(Constants.STRINGS_LOADING_BAR_CHAR, "#");
        STYLE_SETTINGS.put(Constants.STRINGS_PROMPT_CONTINUE, "Press [ENTER] to continue.");
        STYLE_SETTINGS.put(Constants.STRINGS_PROMPT_BACK, "Press [ENTER] to go back.");
        STYLE_SETTINGS.put(Constants.STRINGS_DEF_EXIT_MSG, "Back");

        COL_TEXT_STR = renderColourString(
                getSettingsColour(Constants.COLOUR_TEXT_FG),
                getSettingsColour(Constants.COLOUR_TEXT_BG)
        );
        COL_DECO_STR = renderColourString(
                getSettingsColour(Constants.COLOUR_DECO_FG),
                getSettingsColour(Constants.COLOUR_DECO_BG)
        );
    }

    /**
     * Gets a string from the settings map.
     * @param key The key of the requested string
     * @return The requested string
     */
    public static String getSettingsString(String key) {

        if (STYLE_SETTINGS == null) {
            popupBox(
                    "No style file file loaded.\n" +
                        "Try adding `AnsiUtils.loadDefaultStyle();` to your initialisation.\n" +
                        "Or if you're using a custom style file, check you've used the correct " +
                        "filename and that the file can be found and read.",
                    "! ERROR !",
                    getSettingsColour(Constants.COLOUR_ERROR_FG),
                    getSettingsColour(Constants.COLOUR_ERROR_BG),
                    getSettingsColour(Constants.COLOUR_ERROR_FG),
                    getSettingsColour(Constants.COLOUR_ERROR_BG)
            );
            System.exit(1);
        }

        if (!STYLE_SETTINGS.containsKey(key)) {
            popupBox(
                    "Non-existent key requested from Settings: '" + key + "'.\n" +
                        "Please make sure your style file is being loaded correctly.",
                    "! ERROR !",
                    getSettingsColour(Constants.COLOUR_ERROR_FG),
                    getSettingsColour(Constants.COLOUR_ERROR_BG),
                    getSettingsColour(Constants.COLOUR_ERROR_FG),
                    getSettingsColour(Constants.COLOUR_ERROR_BG)
            );
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
            popupBox(
                    "Attempted to read integer setting, but setting was string:\n" +
                        " Key: '" + key + "', Value: '" + getSettingsString(key) + "'",
                    "! ERROR !",
                    getSettingsColour(Constants.COLOUR_ERROR_FG),
                    getSettingsColour(Constants.COLOUR_ERROR_BG),
                    getSettingsColour(Constants.COLOUR_ERROR_FG),
                    getSettingsColour(Constants.COLOUR_ERROR_BG)
            );
            System.exit(1);
        }
        return i;
    }

    public static Colour getSettingsColour(String key) {
        return Colour.parseColour(getSettingsString(key));
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
     * Renders a string which can be printed to set the cursor's colour
     * @param fg The foreground (text) colour
     * @param bg The background colour
     * @return The ANSI codes in a string
     */
    public static String renderColourString(final Colour fg, final Colour bg) {
        String render = "";
        if (fg.isBright()) {
            render += Ansi.ansi().fgDefault().fgBright(fg.getAnsiColour());
        } else {
            render += Ansi.ansi().fgDefault().fg(fg.getAnsiColour());
        }

        if (bg.isBright()) {
            render += Ansi.ansi().bgDefault().bgBright(bg.getAnsiColour());
        } else {
            render += Ansi.ansi().bgDefault().bg(bg.getAnsiColour());
        }
        return render;
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

        render += " " + COL_TEXT_STR + str + "\n" + COL_DECO_STR + "--";
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
        render += COL_TEXT_STR + str + "\n" + COL_DECO_STR + leftCap;
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
        System.out.print(Ansi.ansi().cursorToColumn(leftMargin) + COL_TEXT_STR);
        for (String s: strs) {
            System.out.print(s);
            System.out.print(Ansi.ansi().cursorDownLine().cursorToColumn(leftMargin));
        }
        resetCursorColour();
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
        setCursorColour(
                getSettingsColour(Constants.COLOUR_TEXT_FG),
                getSettingsColour(Constants.COLOUR_TEXT_BG)
        );
        for (String s: strs) {
            System.out.print(s);
            System.out.print(Ansi.ansi().cursorDownLine().cursorToColumn(leftMargin));
        }
        resetCursorColour();
    }

    /**
     * Prints the given string in an ASCII-art box
     * with the given margins and width.
     * @param inStr The string to print
     * @param leftMargin The left margin of the box
     * @param topMargin The top margin of the box
     * @param width The width of the box
     */
    public static void printInBox(
            final String inStr,
            final int leftMargin,
            final int topMargin,
            final int width
    ) {
        printInBox(
                inStr,
                leftMargin, topMargin,
                width,
                getSettingsColour(Constants.COLOUR_TEXT_FG), getSettingsColour(Constants.COLOUR_TEXT_BG)
        );
    }

    /**
     * Prints the given string in an ASCII-art box
     * with the given margins and width.
     * @param inStr The string to print
     * @param leftMargin The left margin of the box
     * @param topMargin The top margin of the box
     * @param width The width of the box
     * @param fg The foreground colour of the text
     * @param bg The background colour of the text
     */
    public static void printInBox(
            final String inStr,
            final int leftMargin,
            final int topMargin,
            final int width,
            final Colour fg,
            final Colour bg
    ) {
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
                while (!getSettingsString(Constants.STYLE_LINE_BRK_CHARS).contains("" + inStr.charAt(ind))) {
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

        String textColour = renderColourString(fg, bg);

        // Place text in ASCII-art box (Border configuration in Constants)
        String render = "";
        render += COL_DECO_STR + getSettingsString(Constants.STYLE_BORDER_CHARSTR).charAt(0);
        for (int i=0; i<width + 2; i++) render += getSettingsString(Constants.STYLE_BORDER_CHARSTR).charAt(1);
        render += getSettingsString(Constants.STYLE_BORDER_CHARSTR).charAt(2) + "\n";

        for (String line: lines) {
            render += COL_DECO_STR + getSettingsString(Constants.STYLE_BORDER_CHARSTR).charAt(7) + textColour + " " + line;
            for (int i=0; i<width - line.length() + 1; i++) render += " ";
            render += COL_DECO_STR + getSettingsString(Constants.STYLE_BORDER_CHARSTR).charAt(3) + "\n";
        }

        render += getSettingsString(Constants.STYLE_BORDER_CHARSTR).charAt(6);
        for (int i=0; i<width + 2; i++) render += getSettingsString(Constants.STYLE_BORDER_CHARSTR).charAt(5);
        render += getSettingsString(Constants.STYLE_BORDER_CHARSTR).charAt(4);

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
                while (!getSettingsString(Constants.STYLE_LINE_BRK_CHARS).contains("" + inStr.charAt(ind))) {
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
        render += COL_DECO_STR + getSettingsString(Constants.STYLE_BORDER_CHARSTR).charAt(0);
        for (int i=0; i<width + 2; i++) render += getSettingsString(Constants.STYLE_BORDER_CHARSTR).charAt(1);
        render += getSettingsString(Constants.STYLE_BORDER_CHARSTR).charAt(2) + "\n";

        for (String line: lines) {
            render += COL_DECO_STR + getSettingsString(Constants.STYLE_BORDER_CHARSTR).charAt(7) + COL_TEXT_STR + " " + line;
            for (int i=0; i<width - line.length() + 1; i++) render += " ";
            render += COL_DECO_STR + getSettingsString(Constants.STYLE_BORDER_CHARSTR).charAt(3) + "\n";
        }

        render += getSettingsString(Constants.STYLE_BORDER_CHARSTR).charAt(6);
        for (int i=0; i<width + 2; i++) render += getSettingsString(Constants.STYLE_BORDER_CHARSTR).charAt(5);
        render += getSettingsString(Constants.STYLE_BORDER_CHARSTR).charAt(4);

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
        printInBoxWithTitle(str, title, leftMargin, topMargin, getSettingsInt(Constants.LAYOUT_TEXT_WIDTH));
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
        if (title.length() > width) {
            newtitle = newtitle.substring(0, width - 3) + "...";
        }

        printInBox(newtitle, leftMargin, topMargin, width);
        for (int i = 0; i<getSettingsInt(Constants.LAYOUT_TITLE_CONTENT_GAP); i++) { System.out.print("\n"); }
        printInBox(str, leftMargin, width);
    }

    /**
     * Displays the text and title in an offset popup box and
     * returns to the given page when enter is pressed
     * @param str The text to display
     * @param title The popup box title
     * @param titleFg The foreground colour of the title
     * @param titleBg The background colour of the title
     * @param textFg The foreground colour of the text
     * @param textBg The background colour of the text
     */
    public static void popupBox(
            final String str,
            final String title,
            final Colour titleFg, final Colour titleBg,
            final Colour textFg, final Colour textBg
    ) {
        printInBox(
                title,
                getSettingsInt(Constants.LAYOUT_POPUP_X),
                getSettingsInt(Constants.LAYOUT_POPUP_Y),
                getSettingsInt(Constants.LAYOUT_POPUP_WIDTH),
                titleFg, titleBg
        );
        printInBox(
                str
                        + "\n\n" + getSettingsString(Constants.STRINGS_PROMPT_CONTINUE),
                getSettingsInt(Constants.LAYOUT_POPUP_X),
                getSettingsInt(Constants.LAYOUT_POPUP_Y) + 3,
                getSettingsInt(Constants.LAYOUT_POPUP_WIDTH),
                textFg, textBg
        );
        InputUtils.waitForEnter();
    }

}
