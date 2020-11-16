package ch.pearcenet.easymenus.util;

import ch.pearcenet.easymenus.Colour;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * ANSI Utilities
 * Utility methods for fancy display
 */
public class AnsiUtils {

    public static void clearScreen() {
        AnsiConsole.out.println(Ansi.ansi().eraseScreen().cursor(1, 1));
    }

    public static void setCursorPos(final int row, final int col) {
        AnsiConsole.out.print(Ansi.ansi().cursor(row, col));
    }

    public static void printWithMargins(final String str, final int leftMargin, final int topMargin) {
        String[] strs = str.split("\\n");
        setCursorPos(topMargin, leftMargin);
        for (String s: strs) {
            AnsiConsole.out.print(s);
            AnsiConsole.out.print(Ansi.ansi().cursorDownLine().cursorToColumn(leftMargin));
        }
    }

    public static void printInBox(final String str, final int x, final int y) {
        printInBox(str, x, y, Constants.DEFAULT_TXT_WIDTH);
    }

    public static void printInBox(final String str, final int x, final int y, final int maxWidth) {
        // Split text into equal-length strings split on newlines.
        ArrayList<String> lines = new ArrayList<>();
        String[] words = str.split("\\n|\\s");

        for (String word: words) {
            if (lines.size() == 0){
                lines.add(word + " ");
                continue;
            }
            // If new word would pass the limit, make a new line
            // otherwise, add the word to the current line
            int lastLine = lines.size() - 1;
            if (lines.get(lastLine).length() + word.length() > maxWidth
                    || word.length() == 0
            ) {
                lines.set(lastLine, lines.get(lastLine).trim());
                lines.add(word + " ");
            } else {
                lines.set(lastLine, lines.get(lastLine) + word + " ");
            }
        }

        // Place text in ASCII-art box (Border configuration in Constants)
        String render = "";
        render += Constants.DEFAULT_BORDER[0];
        for (int i=0; i<maxWidth; i++) render += Constants.DEFAULT_BORDER[1];
        render += Constants.DEFAULT_BORDER[2] + '\n';

        for (String line: lines) {
            render += Constants.DEFAULT_BORDER[7] + line + Constants.DEFAULT_BORDER[3] + '\n';
        }

        render += Constants.DEFAULT_BORDER[6];
        for (int i=0; i<maxWidth; i++) render += Constants.DEFAULT_BORDER[5];
        render += Constants.DEFAULT_BORDER[4];
    }

    public static void resetCursorColour() {
        AnsiConsole.out.print(Ansi.ansi().bgDefault().fgDefault());
    }

    public static void setCursorColour(final Colour fg, final Colour bg) {
        if (fg.isBright()) {
            AnsiConsole.out.print(Ansi.ansi().fgDefault().fgBright(fg.getAnsiColour()));
        } else {
            AnsiConsole.out.print(Ansi.ansi().fgDefault().fg(fg.getAnsiColour()));
        }

        if (bg.isBright()) {
            AnsiConsole.out.print(Ansi.ansi().bgDefault().bgBright(bg.getAnsiColour()));
        } else {
            AnsiConsole.out.print(Ansi.ansi().bgDefault().bg(bg.getAnsiColour()));
        }
    }

}
