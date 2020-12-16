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
                Constants.DEFAULT_PAGE_MARGIN_LEFT,
                Constants.DEFAULT_PAGE_MARGIN_TOP,
                Constants.TEXT_PAGE_BOX_WIDTH
        );

        // Prompt user to return
        int height = content.split("\\n").length + content.length() / Constants.TEXT_PAGE_BOX_WIDTH + 3;
        AnsiUtils.printInBox(Constants.TEXT_PAGE_RETURN_TXT,
                Constants.DEFAULT_PAGE_MARGIN_LEFT,
                height + Constants.DEFAULT_PAGE_MARGIN_TOP,
                Constants.TEXT_PAGE_RETURN_TXT.length()
        );
        InputUtils.waitForEnter();
    }

    @Override
    public void load(LoadingPage loadingPage) {
        loadingPage.completeAll();
    }
}
