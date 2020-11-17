package ch.pearcenet.easymenus;

import ch.pearcenet.easymenus.util.AnsiUtils;
import ch.pearcenet.easymenus.util.Constants;

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

        AnsiUtils.clearScreen();

        // Fit title into title box
        String newtitle = title;
        if (title.length() > Constants.DEFAULT_MENU_TITLE_WIDTH) {
            newtitle = newtitle.substring(0, Constants.DEFAULT_MENU_TITLE_WIDTH - 3) + "...";
        }
        AnsiUtils.printInBox(newtitle, Constants.DEFAULT_MENU_TITLE_X, Constants.DEFAULT_MENU_TITLE_Y, Constants.DEFAULT_MENU_TITLE_WIDTH);

        String optionStr = "";
        for (int i = 0; i < options.size() + 1; i++) {
            String opt;
            if (i == options.size()) {
                opt = "Exit";
            } else {
                opt = options.get(i);
            }

            optionStr += "[" + (i + 1) + "] " + opt + "\n";
        }

        optionStr += "\n(Enter option number and press enter)\n";
        AnsiUtils.printWithMargins(optionStr, Constants.DEFAULT_MENU_OPTS_X, Constants.DEFAULT_MENU_OPTS_Y);

        // Get user's answer
        int chosen = Input.getInt(1, options.size() + 1) - 1;
        AnsiUtils.printInBoxWithTitle(options.get(chosen), "You Chode:", 10, 5, 20);
    }
}
