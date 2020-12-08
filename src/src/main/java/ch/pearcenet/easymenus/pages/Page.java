package ch.pearcenet.easymenus.pages;

/**
 * Page Interface
 * implement this to make your code callable
 * by menu options
 */
public interface Page {

    public String getOptionName();

    public void callPage();

}
