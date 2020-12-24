package ch.pearcenet.easymenus.pages;

import ch.pearcenet.easymenus.util.InputUtils;
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
public class MenuPage implements LoadedPage {

    private String title;

    private String optionName;

    private ArrayList<Page> options;

    private String exitOptionName;

    public MenuPage(String title, Page... options) {
        this(title, title, options);
    }

    public MenuPage(String title, String optionName, Page... options) {
        this(title, optionName, AnsiUtils.getSettingsString(Constants.STRINGS_DEF_EXIT_MSG), options);
    }

    public MenuPage(String title, String optionName, String exitOptionName, Page... options) {
        this.title = title;
        this.optionName = optionName;
        this.exitOptionName = exitOptionName;
        this.options = new ArrayList<>(Arrays.asList(options));
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
            int titleLen = newtitle.length();
            if (title.length() > AnsiUtils.getSettingsInt(Constants.LAYOUT_MENU_TITLE_MAX_WIDTH)) {
                newtitle = newtitle.substring(
                        0,
                        AnsiUtils.getSettingsInt(Constants.LAYOUT_MENU_TITLE_MAX_WIDTH) - 3)
                        + "...";
                titleLen = AnsiUtils.getSettingsInt(Constants.LAYOUT_MENU_TITLE_MAX_WIDTH);
            }
            AnsiUtils.printInBox(
                    newtitle,
                    AnsiUtils.getSettingsInt(Constants.LAYOUT_PAGE_MARGIN_LEFT),
                    AnsiUtils.getSettingsInt(Constants.LAYOUT_PAGE_MARGIN_TOP),
                    titleLen);

            String optionStr = "";
            for (int i = 0; i < options.size() + 1; i++) {
                String opt;
                if (i == options.size()) {
                    opt = exitOptionName;
                } else {
                    opt = options.get(i).getOptionName();
                }

                optionStr += "[" + (i + 1) + "] " + opt + "\n";
            }

            optionStr += "\n(Enter option number and press enter)\n";
            AnsiUtils.printWithMargins(
                    optionStr,
                    AnsiUtils.getSettingsInt(Constants.LAYOUT_PAGE_MARGIN_LEFT),
                    AnsiUtils.getSettingsInt(Constants.LAYOUT_PAGE_MARGIN_TOP)
                            + 3
                            + AnsiUtils.getSettingsInt(Constants.LAYOUT_TITLE_CONTENT_GAP));

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

    public ArrayList<Page> getOptions() {
        return options;
    }

    public String getTitle() {
        return title;
    }

    public MenuPage setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public void load(LoadingPage loadingPage) {
        loadingPage.completeAll();
    }
}
