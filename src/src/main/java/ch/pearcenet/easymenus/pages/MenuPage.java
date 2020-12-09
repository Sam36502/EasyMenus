package ch.pearcenet.easymenus.pages;

import ch.pearcenet.easymenus.InputUtils;
import ch.pearcenet.easymenus.util.AnsiUtils;
import ch.pearcenet.easymenus.util.Constants;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Menu Class
 * Instantiate this to display a list of
 * options and to redirect the user to
 * some code or another Page
 */
public class MenuPage implements Page {

    private String title;

    private String optionName;

    private ArrayList<Page> options;

    public MenuPage(String title, Page... options) {
        this.title = title;
        this.optionName = title;
        this.options = new ArrayList<Page>(Arrays.asList(options));
    }

    public MenuPage(String title, String optionName, Page... options) {
        this.title = title;
        this.optionName = optionName;
        this.options = new ArrayList<Page>(Arrays.asList(options));
    }

    @Override
    public String getOptionName() {
        return optionName;
    }

    @Override
    public void callPage() {

        boolean onPage = true;
        while (onPage) {
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
                    opt = "Back";
                } else {
                    opt = options.get(i).getOptionName();
                }

                optionStr += "[" + (i + 1) + "] " + opt + "\n";
            }

            optionStr += "\n(Enter option number and press enter)\n";
            AnsiUtils.printWithMargins(optionStr, Constants.DEFAULT_MENU_OPTS_X, Constants.DEFAULT_MENU_OPTS_Y);

            // Get user's answer and call page's method
            int chosen = InputUtils.getInt(1, options.size() + 1) - 1;

            // Check if exit option is chosen
            if (chosen == options.size()) {
                onPage = false;
                continue;
            }

            options.get(chosen).callPage();
        }
    }
}
