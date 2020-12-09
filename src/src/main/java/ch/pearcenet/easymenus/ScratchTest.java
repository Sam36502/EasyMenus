package ch.pearcenet.easymenus;

import ch.pearcenet.easymenus.pages.MenuPage;
import ch.pearcenet.easymenus.pages.Page;
import ch.pearcenet.easymenus.pages.TextPage;
import ch.pearcenet.easymenus.util.AnsiUtils;

public class ScratchTest {

    /**
     *  ## TEST CLASS ##
     *
     *  For testing features roughly during development;
     *  this class MUST be removed before release
     */

    public static void main(String[] args) {

        AnsiUtils.installConsole();
        InputUtils.openScanner();

        Page mainMenu = new MenuPage("Test System",
                new TextPage("Menu Intro", "Hello!\nThanks for using EasyMenus!\n\nThis is a simple text page with some info. Select the blog to read more text pages."),
                new MenuPage("My Blog",
                        new TextPage("A day at the Zoo", "08/12/2020",
                                "Today I wandered aimlessly around an enclosure of prisoned creatures. It was quite boring.\n\nThat's all."),
                        new TextPage("Eggs", "09/12/2020",
                                "This morning I was overcome with the insatiable desire to make eggs for breakfast.\nMiraculously, I was able to make some eggs that did not looks absolutely hideous.")

                )
        );

        mainMenu.callPage();

        AnsiUtils.clearScreen();
        AnsiUtils.printInBox("Thanks for using EasyMenus!", 2, 1, "Thanks for using EasyMenus!".length());
        AnsiUtils.uninstallConsole();
        InputUtils.closeScanner();

    }

}
