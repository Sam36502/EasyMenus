package ch.pearcenet.easymenus.input;

import ch.pearcenet.easymenus.util.AnsiUtils;
import ch.pearcenet.easymenus.util.Constants;

import java.util.Scanner;

/**
 * String Input
 * An input field that gets a string and returns it
 */
public class StringInput extends Input<String> {

    private String regex;

    private String format;

    private int maxLength;

    public StringInput(String name) {
        this(name, Constants.DEFAULT_MAX_INPUT_LEN);
    }

    public StringInput(String name, int maxLength) {
        this(name, maxLength, ".*", "anything");
    }

    public StringInput(String name, int maxLength, String regex, String format) {
        super(name);
        this.regex = regex;
        this.format = format;
        this.maxLength = maxLength;
    }

    @Override
    public void displayPrompt(String title, Scanner input) {
        String in = "";
        boolean validAnswer = false;
        while (!validAnswer) {
            validAnswer = true;

            super.displayPrompt(title, input);

            in = input.nextLine();
            if (in.length() > maxLength) {
                validAnswer = false;
                AnsiUtils.printWithMargins(
                        "Error: That answer's too long; please keep input within " + maxLength + " characters.",
                        AnsiUtils.getSettingsInt(Constants.LAYOUT_PAGE_MARGIN_LEFT)
                );
                try { Thread.sleep(Constants.ERROR_MSG_DISPLAY_TIME);
                } catch (InterruptedException e) { e.printStackTrace(); }
                continue;
            }

            if (!in.matches(regex)) {
                validAnswer = false;
                AnsiUtils.printWithMargins(
                        "Error: That answer doesn't match the required format: '" + format + "'.",
                        AnsiUtils.getSettingsInt(Constants.LAYOUT_PAGE_MARGIN_LEFT)
                );
                try { Thread.sleep(Constants.ERROR_MSG_DISPLAY_TIME);
                } catch (InterruptedException e) { e.printStackTrace(); }
                continue;
            }

        }
        setAnswer(in);
    }

}
