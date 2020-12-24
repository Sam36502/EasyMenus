package ch.pearcenet.easymenus.input;

import ch.pearcenet.easymenus.util.AnsiUtils;
import ch.pearcenet.easymenus.util.Constants;

import java.util.Scanner;

public class DoubleInput extends Input<Double> {

    private double min;

    private double max;

    public DoubleInput(String name) {
        this(name, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    public DoubleInput(String name, double min, double max) {
        super(name);
        this.min = min;
        this.max = max;
    }

    @Override
    public void displayPrompt(String title, Scanner input) {
        double result = 0.0;
        boolean validAnswer = false;
        while (!validAnswer) {
            validAnswer = true;

            super.displayPrompt(title, input);

            String in = input.nextLine();
            try {
                result = Double.parseDouble(in);
            } catch (NumberFormatException numEx) {
                validAnswer = false;
                String errnum = in;

                if (errnum.length() > Constants.MAX_ERR_MSG_LENGTH) {
                    errnum = errnum.substring(0, errnum.length() - Constants.MAX_ERR_MSG_LENGTH + 3);
                    errnum += "...";
                }
                AnsiUtils.printWithMargins(
                        "Error: '" + errnum + "' is not a valid number.",
                        AnsiUtils.getSettingsInt(Constants.LAYOUT_PAGE_MARGIN_LEFT)
                );
                try { Thread.sleep(Constants.ERROR_MSG_DISPLAY_TIME);
                } catch (InterruptedException e) { e.printStackTrace(); }
                continue;
            }

            if (result > max || result < min) {
                validAnswer = false;
                AnsiUtils.printWithMargins(
                        "Error: Number must be within range: (" + min + " - " + max + ")",
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
