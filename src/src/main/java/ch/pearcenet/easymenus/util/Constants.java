package ch.pearcenet.easymenus.util;

import java.util.Properties;

/**
 * Default Constants
 */
public class Constants {

    // Non-Settings Constants:
    public static final int ERROR_MSG_DISPLAY_TIME = 3 * 1000; // 3 seconds
    public static final int MAX_ERR_MSG_LENGTH = 10;
    public static final int DEFAULT_MAX_INPUT_LEN = 5000;
    public static final String EASY_MENUS_VERSION = "1.0 - Alpha";

    // Style Settings Key Constants
    public static final String STYLE_BORDER_CHARSTR = "style.border_charstr";
    public static final String STYLE_LINE_BRK_CHARS = "style.line_break_chars";

    public static final String LAYOUT_TEXT_WIDTH = "layout.text.width";
    public static final String LAYOUT_TITLE_CONTENT_GAP = "layout.title_content_gap";
    public static final String LAYOUT_CONTENT_PROMPT_GAP = "layout.content_prompt_gap";
    public static final String LAYOUT_LOAD_MARGIN_SIDE = "layout.load.margin_side";
    public static final String LAYOUT_LOAD_MARGIN_TOP = "layout.load.margin_top";
    public static final String LAYOUT_PAGE_MARGIN_LEFT = "layout.page.margin_left";
    public static final String LAYOUT_PAGE_MARGIN_TOP = "layout.page.margin_top";
    public static final String LAYOUT_MENU_TITLE_MAX_WIDTH = "layout.menu.title.max_width";
    public static final String LAYOUT_INPUT_MARGIN_LEFT = "layout.input.margin_left";
    public static final String LAYOUT_POPUP_X = "layout.popup.x";           // Note: It will stack overflow,
    public static final String LAYOUT_POPUP_Y = "layout.popup.y";           // if any of these three, or
    public static final String LAYOUT_POPUP_WIDTH = "layout.popup.width";   // 'strings.prompt.continue' is missing.

    public static final String COLOUR_TEXT_FG = "colour.text_fg";
    public static final String COLOUR_TEXT_BG = "colour.text_bg";
    public static final String COLOUR_DECO_FG = "colour.deco_fg";
    public static final String COLOUR_DECO_BG = "colour.deco_bg";
    public static final String COLOUR_ERROR_FG = "colour.error_fg";
    public static final String COLOUR_ERROR_BG = "colour.error_bg";
    public static final String COLOUR_PROMPT_FG = "colour.prompt_fg";
    public static final String COLOUR_PROMPT_BG = "colour.prompt_bg";
    public static final String COLOUR_LOADING_FG = "colour.loading_fg";
    public static final String COLOUR_LOADING_BG = "colour.loading_bg";


    public static final String STRINGS_LOADING_DEF_MSG = "strings.loading.def_msg";
    public static final String STRINGS_LOADING_BAR_CHAR = "strings.loading.bar_char";
    public static final String STRINGS_PROMPT_CONTINUE = "strings.prompt.continue";
    public static final String STRINGS_PROMPT_BACK = "strings.prompt.back";
    public static final String STRINGS_DEF_EXIT_MSG = "strings.def_exit_msg";

}
