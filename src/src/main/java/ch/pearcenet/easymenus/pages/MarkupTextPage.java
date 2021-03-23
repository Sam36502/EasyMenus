package ch.pearcenet.easymenus.pages;

import ch.pearcenet.easymenus.util.AnsiUtils;
import ch.pearcenet.easymenus.util.Constants;
import ch.pearcenet.easymenus.util.InputUtils;
import org.fusesource.jansi.Ansi;

/**
 * Markup Text Page
 * A Page that displays some content text formatted with
 * basic Markup tags and allows the option to return to
 * the previous page.
 */
public class MarkupTextPage implements LoadedPage {

    private String title;

    private String optionName;

    private Page nextPage;

    private String content;

    public MarkupTextPage(String title, String content) {
        this(title, title, content);
    }

    public MarkupTextPage(String title, String optionName, String content) {
        this(title, optionName, content, null);
    }

    public MarkupTextPage(String title, String content, Page nextPage) {
        this(title, title, content, nextPage);
    }

    public MarkupTextPage(String title, String optionName, String content, Page nextPage) {
        this.title = title;
        this.optionName = optionName;
        this.content = markupFormat(content);
        this.nextPage = nextPage;
    }

    public MarkupTextPage setTitle(String title) {
        this.title = title;
        return this;
    }

    public MarkupTextPage setContent(String content) {
        this.content = markupFormat(content);
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

        String prompt = AnsiUtils.getSettingsString(Constants.STRINGS_PROMPT_BACK);
        if (nextPage != null) {
            prompt = AnsiUtils.getSettingsString(Constants.STRINGS_PROMPT_CONTINUE);
        }

        AnsiUtils.printInBox(
                prompt,
                AnsiUtils.getSettingsInt(Constants.LAYOUT_PAGE_MARGIN_LEFT),
                AnsiUtils.getSettingsInt(Constants.LAYOUT_TEXT_WIDTH)
        );
        InputUtils.waitForEnter();
        if (nextPage != null) {
            nextPage.callPage();
        }
    }

    @Override
    public void callPage(InputPage inputPage) {
        callPage();
    }

    @Override
    public void load(LoadingPage loadingPage) {
        loadingPage.completeAll();
    }

    /**
     * Takes a string and formats it
     * by replacing markup tags with formatted text
     * @param content The markup text
     * @return Formatted string
     */
    private String markupFormat(final String content) {
        String formatted = content;

        formatted = formatted.replaceAll("<br>", "\n");
        formatted = formatted.replaceAll("<mark>",
                "" + Ansi.ansi()
                        .fg(AnsiUtils.getSettingsColour(Constants.MARKUP_HIGHLIGHT_FG).getAnsiColour())
                        .bg(AnsiUtils.getSettingsColour(Constants.MARKUP_HIGHLIGHT_BG).getAnsiColour())
        );
        formatted = formatted.replaceAll("</mark>",
                "" + Ansi.ansi()
                        .fg(AnsiUtils.getSettingsColour(Constants.COLOUR_TEXT_FG).getAnsiColour())
                        .bg(AnsiUtils.getSettingsColour(Constants.COLOUR_TEXT_BG).getAnsiColour())
        );
        formatted = formatted.replaceAll("<strong>|<b>",
                "" + Ansi.ansi().bold()
        );
        formatted = formatted.replaceAll("</strong>|</b>",
                "" + Ansi.ansi().boldOff()
        );
        formatted = formatted.replaceAll("<lt>", "<");
        formatted = formatted.replaceAll("<gt>", ">");

        return formatted;
    }
}
