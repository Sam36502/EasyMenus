package ch.pearcenet.easymenus.input;

import ch.pearcenet.easymenus.util.Constants;

/**
 * String Input
 * An input field that gets a string and returns it
 */
public class StringInput implements Input<String> {

    private String name;

    private String answer;

    private String regex;

    private int maxLength;

    public StringInput(String name, int maxLength, String regex) {
        this.name = name;
        this.answer = "";
        this.regex = regex;
        this.maxLength = maxLength;
    }

    public StringInput(String name, int maxLength) {
        this.name = name;
        this.answer = "";
        this.regex = ".*";
        this.maxLength = maxLength;
    }

    public StringInput(String name) {
        this.name = name;
        this.answer = "";
        this.regex = ".*";
        this.maxLength = Constants.DEFAULT_MAX_INPUT_LEN;
    }

    @Override
    public void displayPrompt() {

    }

    @Override
    public String getAnswer() {
        return answer;
    }
}
