package ch.pearcenet.easymenus.input;

import ch.pearcenet.easymenus.util.AnsiUtils;
import ch.pearcenet.easymenus.util.Constants;

import java.util.Scanner;

public class BoolInput extends Input<Boolean> {

    public BoolInput(String name) {
        super(name + " (y/n)");
    }

    @Override
    public void displayPrompt(String title, Scanner input) {
        boolean result = false;
        boolean validAnswer = false;
        while (!validAnswer) {
            validAnswer = true;

            super.displayPrompt(title, input);

            String in = input.nextLine().toLowerCase();

            if (
                    !"y".equals(in)
                    && !"yes".equals(in)
                    && !"n".equals(in)
                    && !"no".equals(in)) {
                validAnswer = false;
                AnsiUtils.printWithMargins(
                        "Error: Input must be one of the following (case insensitive): (y, yes, n, no)",
                        AnsiUtils.getSettingsInt(Constants.LAYOUT_PAGE_MARGIN_LEFT)
                );
                try { Thread.sleep(Constants.ERROR_MSG_DISPLAY_TIME);
                } catch (InterruptedException e) { e.printStackTrace(); }
                continue;
            }

        }
        setAnswer(result);
    }

}
