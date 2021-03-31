package ch.pearcenet.easymenus.pages;

import ch.pearcenet.easymenus.util.AnsiUtils;
import ch.pearcenet.easymenus.util.Colour;
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

    @Override
    public void setNext(LoadedPage nextPage) { this.nextPage = nextPage; }

    /**
     * Takes a string and formats it
     * by replacing markup tags with formatted text
     * @param content The markup text
     * @return Formatted string
     */
    private String markupFormat(final String content) {
        String formatted = content;

        try {
            formatted = formatted.replaceAll("\n", " ");
            formatted = formatted.replaceAll("<br>", "\n");
            formatted = formatted.replaceAll("<t>", "\t");
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

            // Colour Tags
            formatted = formatted.replaceAll("<col>|</col>", "" + Ansi.ansi()
                    .fg(AnsiUtils.getSettingsColour(Constants.COLOUR_TEXT_FG).getAnsiColour())
                    .bg(AnsiUtils.getSettingsColour(Constants.COLOUR_TEXT_BG).getAnsiColour())
            );
            int index = formatted.indexOf("<col");
            while (index != -1) {
                String fgString = AnsiUtils.getSettingsString(Constants.COLOUR_TEXT_FG);
                String bgString = AnsiUtils.getSettingsString(Constants.COLOUR_TEXT_BG);

                int endIndex = formatted.indexOf(">", index);
                String tagArgs = formatted.substring(index, endIndex);
                int fgStIndex = tagArgs.indexOf("fg=");
                if (fgStIndex != -1) {
                    int semiIndex = tagArgs.indexOf(";", fgStIndex);
                    fgString = tagArgs.substring(fgStIndex + "fg=".length(), semiIndex);
                }
                int bgStIndex = formatted.substring(index, endIndex).indexOf("bg=");
                if (bgStIndex != -1) {
                    int semiIndex = tagArgs.indexOf(";", bgStIndex);
                    bgString = tagArgs.substring(bgStIndex + "fg=".length(), semiIndex);
                }

                Colour fg = Colour.parseColour(fgString);
                Colour bg = Colour.parseColour(bgString);

                formatted = formatted.substring(0, index)
                        + Ansi.ansi().fg(fg.getAnsiColour()).bg(bg.getAnsiColour())
                        + formatted.substring(endIndex + 1);

                index = formatted.indexOf("<col", index);
            }

            // Making headings
            index = formatted.indexOf("<h1>");
            while (index != -1) {
                int hIndex = index + "<h1>".length();
                int hEndindex = formatted.indexOf("</h1>", index);
                int endIndex = hEndindex + "</h1>".length();

                // Create Heading Border
                char[] brdr = AnsiUtils.getSettingsString(Constants.STYLE_BORDER_CHARSTR).toCharArray();
                String heading = "" + brdr[0];
                for (int i = 0; i < (hEndindex - hIndex) + 2; i++) {
                    heading += brdr[1];
                }
                heading += brdr[2] + "\n" + brdr[7] + " "
                        + formatted.substring(hIndex, hEndindex)
                        + " " + brdr[3] + "\n" + brdr[6];
                for (int i = 0; i < (hEndindex - hIndex) + 2; i++) {
                    heading += brdr[5];
                }
                heading += brdr[4] + "\n";

                formatted = formatted.substring(0, index)
                        + heading
                        + formatted.substring(endIndex);

                index = formatted.indexOf("<h1>", index);
            }

            // Escape codes; must be done last
            formatted = formatted.replaceAll("<lt>", "<");
            formatted = formatted.replaceAll("<gt>", ">");
        } catch (Exception e) { // TODO: Yes, I know this is bad practice, but the whole thing needs to be replaced with
                                // TODO: an actual ANTLR parser at some point, so I don't mind solving syntax errors like this.
            AnsiUtils.popupBox(
                    "Syntax Error in page Markup.",
                    "ERROR",
                    Colour.BLACK, Colour.BRIGHT_RED,
                    Colour.BRIGHT_RED, Colour.BLACK
            );
        }

        return formatted;
    }
}
