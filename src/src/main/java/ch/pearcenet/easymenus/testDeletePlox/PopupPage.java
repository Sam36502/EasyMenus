package ch.pearcenet.easymenus.testDeletePlox;

import ch.pearcenet.easymenus.pages.TextPage;
import ch.pearcenet.easymenus.util.AnsiUtils;
import ch.pearcenet.easymenus.util.Colour;

public class PopupPage extends TextPage {

    public PopupPage() {
        super(
                "Popup Test Page",
                "This page tests if the popup functionality works correctly."
        );
    }

    @Override
    public void callPage() {
        super.callPage();

        AnsiUtils.popupBox(
                "Whoah! A popup box!\n" +
                        "This is neato! Though the colours are a bit weird...",
                "Cool!",
                Colour.BRIGHT_CYAN,
                Colour.BRIGHT_BLACK,
                Colour.BLACK,
                Colour.BRIGHT_YELLOW
        );

        super.callPage();
    }
}
