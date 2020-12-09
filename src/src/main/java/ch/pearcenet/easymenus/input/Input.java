package ch.pearcenet.easymenus.input;

import java.util.Scanner;

/**
 * Input Interface
 * Interface for an input field
 * to be nested in pages
 */
public interface Input<T> {

    void displayPrompt(String title, Scanner input);

    T getAnswer();

    String getName();

}
