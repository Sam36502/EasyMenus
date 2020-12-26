package ch.pearcenet.easymenus.pages;

import ch.pearcenet.easymenus.util.InputUtils;
import ch.pearcenet.easymenus.util.AnsiUtils;
import ch.pearcenet.easymenus.util.Constants;

/**
 * Text Page
 * A Page that simply displays some content text and
 * allows the option to return to the previous page.
 */
public class TextPage implements LoadedPage {

    private String title;

    private String optionName;

    private String content;

    public TextPage(String title, String content) {
        this(title, title, content);
    }

    public TextPage(String title, String optionName, String content) {
        this.title = title;
        this.optionName = optionName;
        this.content = content;
    }

    public TextPage setTitle(String title) {
        this.title = title;
        return this;
    }

    public TextPage setContent(String content) {
        this.content = content;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }



    @Override
    public String getOptionName() {
        return optionName;
    }

    @Override
    public void callPage() {
        // Display Contents
        AnsiUtils.clearScreen();
        AnsiUtils.printInBoxWithTitle(content, title,
                AnsiUtils.getSettingsInt(Constants.LAYOUT_PAGE_MARGIN_LEFT),
                AnsiUtils.getSettingsInt(Constants.LAYOUT_PAGE_MARGIN_TOP),
                AnsiUtils.getSettingsInt(Constants.LAYOUT_TEXT_WIDTH)
        );

        AnsiUtils.moveCursor(0, AnsiUtils.getSettingsInt(Constants.LAYOUT_CONTENT_PROMPT_GAP));
        AnsiUtils.printInBox(AnsiUtils.getSettingsString(Constants.STRINGS_PROMPT_BACK),
                AnsiUtils.getSettingsInt(Constants.LAYOUT_PAGE_MARGIN_LEFT),
                AnsiUtils.getSettingsInt(Constants.LAYOUT_TEXT_WIDTH)
        );
        InputUtils.waitForEnter();
    }

    @Override
    public void load(LoadingPage loadingPage) {
        loadingPage.completeAll();
    }
}
