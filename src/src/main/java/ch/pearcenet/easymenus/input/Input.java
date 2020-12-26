package ch.pearcenet.easymenus.input;

import ch.pearcenet.easymenus.util.AnsiUtils;
import ch.pearcenet.easymenus.util.Constants;

import java.util.Scanner;

/**
 * Input Interface
 * Interface for an input field
 * to be nested in pages
 */
public class Input<T> {

    private String name;

    private T answer;

    public Input(String name) {
        this.name = name;
        this.answer = null;
    }

    public void displayPrompt(String title, Scanner input) {
            AnsiUtils.clearScreen();
            AnsiUtils.printInBox(title,
                    AnsiUtils.getSettingsInt(Constants.LAYOUT_PAGE_MARGIN_LEFT),
                    AnsiUtils.getSettingsInt(Constants.LAYOUT_PAGE_MARGIN_TOP),
                    title.length());
            AnsiUtils.printWithMargins(
                    "\n" + AnsiUtils.renderWithUnderline(name) + "\n",
                    AnsiUtils.getSettingsInt(Constants.LAYOUT_PAGE_MARGIN_LEFT)
            );
            AnsiUtils.setCursorColour(
                    AnsiUtils.getSettingsColour(Constants.COLOUR_PROMPT_FG),
                    AnsiUtils.getSettingsColour(Constants.COLOUR_PROMPT_BG)
            );
            System.out.print("  > ");
    }

    public T getAnswer() {
        return answer;
    }

    protected void setAnswer(T answer) {
        this.answer = answer;
    }

    public String getName() {
        return name;
    }

}
