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
        this(title, title, Constants.DEFAULT_LOADING_MSG, nextPage, numTasks);
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
        String loadingBar = "";
        int length = Constants.TEXT_PAGE_BOX_WIDTH - (2 * Constants.LOADING_BAR_MARGIN_SIDES) - 2;
        int numChars = (int) Math.round(((double) tasksCompleted / (double) numTasks) * (double) length);
        if (numChars == prevNumChars) {
            return;
        }
        prevNumChars = numChars;

        // Render Contents
        String render = "";
        for (int i=0; i<Constants.LOADING_BAR_MARGIN_TOP + 2; i++) {
            render += "\n";
        }
        render += content;

        // Display Contents
        AnsiUtils.clearScreen();
        AnsiUtils.printInBoxWithTitle(render, title,
                Constants.DEFAULT_PAGE_MARGIN_LEFT,
                Constants.DEFAULT_PAGE_MARGIN_TOP,
                Constants.TEXT_PAGE_BOX_WIDTH
        );

        for (int i=0; i<numChars; i++) { loadingBar += Constants.LOADING_BAR_CHARACTER; }
        AnsiUtils.printInBox(
                loadingBar,
                Constants.DEFAULT_PAGE_MARGIN_LEFT + Constants.LOADING_BAR_MARGIN_SIDES + 1,
                Constants.DEFAULT_PAGE_MARGIN_TOP + Constants.LOADING_BAR_MARGIN_TOP + 1,
                length
        );
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
            int height = Constants.LOADING_BAR_MARGIN_TOP + 3
                    + content.split("\\n").length
                    + content.length() / Constants.TEXT_PAGE_BOX_WIDTH
                    + 3;
            AnsiUtils.printInBox(Constants.LOADING_COMPLETE_MSG,
                    Constants.DEFAULT_PAGE_MARGIN_LEFT,
                    height + Constants.DEFAULT_PAGE_MARGIN_TOP,
                    Constants.LOADING_COMPLETE_MSG.length()
            );
            InputUtils.waitForEnter();
            resetProgress();
            nextPage.callPage();
        }
    }
}
