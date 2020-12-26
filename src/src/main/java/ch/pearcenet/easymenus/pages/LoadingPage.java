package ch.pearcenet.easymenus.pages;

import ch.pearcenet.easymenus.util.InputUtils;
import ch.pearcenet.easymenus.util.AnsiUtils;
import ch.pearcenet.easymenus.util.Constants;

/**
 * Loading Page
 * A Page that simply displays a loading bar and
 * some optional content while something loads in
 * the background.
 */
public class LoadingPage implements Page {

    private String title;

    private String optionName;

    private String content;

    private LoadedPage nextPage;

    private int numTasks;

    private int tasksCompleted;

    private int prevNumChars = 0;

    public LoadingPage(String title, LoadedPage nextPage, int numTasks) {
        this(title, title, AnsiUtils.getSettingsString(Constants.STRINGS_LOADING_DEF_MSG), nextPage, numTasks);
    }

    public LoadingPage(String title, String content, LoadedPage nextPage, int numTasks) {
        this(title, title, content, nextPage, numTasks);
    }

    public LoadingPage(String title, String optionName, String content, LoadedPage nextPage, int numTasks) {
        this.title = title;
        this.optionName = optionName;
        this.content = content;
        this.nextPage = nextPage;
        this.numTasks = numTasks;
        this.tasksCompleted = 0;
    }

    /**
     * Signals to this loading page
     * that on of its tasks has been completed.
     */
    public void taskDone() {
        tasksCompleted++;
        drawScreen();
    }

    /**
     * Sets all tasks to complete.
     */
    public void completeAll() {
        tasksCompleted = numTasks;
        drawScreen();
    }

    /**
     * Resets all task progress to the beginning.
     */
    public void resetProgress() {
        tasksCompleted = 0;
        drawScreen();
    }

    private void drawScreen() {
        int length = AnsiUtils.getSettingsInt(Constants.LAYOUT_TEXT_WIDTH)
                - (2 * AnsiUtils.getSettingsInt(Constants.LAYOUT_LOAD_MARGIN_SIDE))
                - 2;
        int numChars = (int) Math.round(((double) tasksCompleted / (double) numTasks) * (double) length);
        if (numChars == prevNumChars) {
            return;
        }
        prevNumChars = numChars;

        // Render Contents
        String render = "";
        for (
                int i = 0;
                i<AnsiUtils.getSettingsInt(Constants.LAYOUT_LOAD_MARGIN_TOP)
                        + AnsiUtils.getSettingsInt(Constants.LAYOUT_TITLE_CONTENT_GAP) + 1;
                i++) {

            render += "\n";
        }
        render += content;

        // Display Contents
        AnsiUtils.clearScreen();
        AnsiUtils.printInBoxWithTitle(render, title,
                AnsiUtils.getSettingsInt(Constants.LAYOUT_PAGE_MARGIN_LEFT),
                AnsiUtils.getSettingsInt(Constants.LAYOUT_PAGE_MARGIN_TOP),
                AnsiUtils.getSettingsInt(Constants.LAYOUT_TEXT_WIDTH)
        );

        String loadingBar = "";
        while (loadingBar.length() < numChars) {
            loadingBar += AnsiUtils.getSettingsString(Constants.STRINGS_LOADING_BAR_CHAR);
        }
        loadingBar = loadingBar.substring(0, numChars);
        AnsiUtils.printInBox(
                loadingBar,
                AnsiUtils.getSettingsInt(Constants.LAYOUT_PAGE_MARGIN_LEFT)
                        + AnsiUtils.getSettingsInt(Constants.LAYOUT_LOAD_MARGIN_SIDE) + 1,
                AnsiUtils.getSettingsInt(Constants.LAYOUT_PAGE_MARGIN_TOP)
                        + AnsiUtils.getSettingsInt(Constants.LAYOUT_LOAD_MARGIN_TOP) + 2
                        + AnsiUtils.getSettingsInt(Constants.LAYOUT_TITLE_CONTENT_GAP),
                length,
                AnsiUtils.getSettingsColour(Constants.COLOUR_LOADING_FG),
                AnsiUtils.getSettingsColour(Constants.COLOUR_LOADING_BG)
        );

        AnsiUtils.moveCursor(0,
                content.split("\n").length
                + 1
                + AnsiUtils.getSettingsInt(Constants.LAYOUT_CONTENT_PROMPT_GAP));
    }

    @Override
    public String getOptionName() {
        return optionName;
    }

    @Override
    public void callPage() {
        drawScreen();
        nextPage.load(this);

        // Prompt user to continue if tasks are loaded
        if (tasksCompleted == numTasks) {
            AnsiUtils.moveCursor(0, AnsiUtils.getSettingsInt(Constants.LAYOUT_CONTENT_PROMPT_GAP));
            AnsiUtils.printInBox(AnsiUtils.getSettingsString(Constants.STRINGS_PROMPT_CONTINUE),
                    AnsiUtils.getSettingsInt(Constants.LAYOUT_PAGE_MARGIN_LEFT),
                    AnsiUtils.getSettingsInt(Constants.LAYOUT_TEXT_WIDTH)
            );
            InputUtils.waitForEnter();
            resetProgress();
            nextPage.callPage();
        }
    }
}
