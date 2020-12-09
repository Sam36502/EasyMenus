package ch.pearcenet.easymenus.input;

/**
 * Input Interface
 * Interface for an input field
 * to be nested in pages
 */
public interface Input<T> {

    void displayPrompt();

    T getAnswer();

}
