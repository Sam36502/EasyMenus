package ch.pearcenet.easymenus;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Menu Class
 * Instantiate this to display a list of
 * options and to redirect the user to
 * some code or another menu
 */
public class Menu implements MenuPage {

    private String title;

    private ArrayList<String> options;

    public Menu(String title, String... options) {
        this.title = title;
        this.options = new ArrayList<>(Arrays.asList(options));
    }

    @Override
    public void callPage() {

    }
}
