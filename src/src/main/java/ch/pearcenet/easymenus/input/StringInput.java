package ch.pearcenet.easymenus.input;

import ch.pearcenet.easymenus.InputUtils;
import ch.pearcenet.easymenus.util.AnsiUtils;
import ch.pearcenet.easymenus.util.Constants;

import java.util.Scanner;

/**
 * String Input
 * An input field that gets a string and returns it
 */
public class StringInput implements Input<String> {

    private String name;

    private String answer;

    private String regex;

    private String format;

    private int maxLength;

    public StringInput(String name, int maxLength, String regex, String format) {
        this.name = name;
        this.answer = "";
        this.regex = regex;
        this.format = format;
        this.maxLength = maxLength;
    }

    public StringInput(String name, int maxLength) {
        this.name = name;
        this.answer = "";
        this.regex = ".*";
        this.format = "Anything.";
        this.maxLength = maxLength;
    }

    public StringInput(String name) {
        this.name = name;
        this.answer = "";
        this.regex = ".*";
        this.format = "Anything.";
        this.maxLength = Constants.DEFAULT_MAX_INPUT_LEN;
    }

    @Override
    public void displayPrompt(String title, Scanner input) {
        String in = "";
        boolean validAnswer = false;
        while (!validAnswer) {

            validAnswer = true;
            AnsiUtils.clearScreen();
            AnsiUtils.printInBox(title,
                    Constants.DEFAULT_PAGE_MARGIN_LEFT,
                    Constants.DEFAULT_PAGE_MARGIN_TOP,
                    title.length());
            AnsiUtils.printWithMargins(
                    "\n" + AnsiUtils.renderWithUnderline(name) + "\n",
                    Constants.DEFAULT_PAGE_MARGIN_LEFT
            );
            System.out.print("  > ");

            in = input.nextLine();
            if (in.length() > maxLength) {
                validAnswer = false;
                System.out.println("Error: That answer's too long; please keep input within " + maxLength + " characters.");
                try { Thread.sleep(Constants.ERROR_MSG_WAIT);
                } catch (InterruptedException e) { e.printStackTrace(); }
                continue;
            }

            if (!in.matches(regex)) {
                validAnswer = false;
                System.out.println("Error: That answer doesn't match the required format: '" + format + "'.");
                try { Thread.sleep(Constants.ERROR_MSG_WAIT);
                } catch (InterruptedException e) { e.printStackTrace(); }
                continue;
            }

        }
        answer = in;
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public String getName() {
        return name;
    }
}
