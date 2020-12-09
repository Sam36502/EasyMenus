package ch.pearcenet.easymenus;

import ch.pearcenet.easymenus.input.StringInput;
import ch.pearcenet.easymenus.pages.*;
import ch.pearcenet.easymenus.util.AnsiUtils;

import java.util.ArrayList;

public class ScratchTest {

    public static MenuPage blog;

    /**
     *  ## TEST CLASS ##
     *
     *  For testing features roughly during development;
     *  this class MUST be removed before release
     */
    public static void main(String[] args) {

        AnsiUtils.installConsole();
        InputUtils.openScanner();

        blog = new MenuPage("My Blog",
                new TextPage("A day at the Zoo", "08/12/2020",
                        "Today I wandered aimlessly around an enclosure of prisoned creatures. It was quite boring.\n\nThat's all."),
                new TextPage("Eggs", "09/12/2020",
                        "This morning I was overcome with the insatiable desire to make eggs for breakfast.\nMiraculously, I was able to make some eggs that did not looks absolutely hideous."),
                new NewBlogPage()
        );

        Page mainMenu = new MenuPage("Test System",
                new TextPage("Menu Intro", "Hello!\nThanks for using EasyMenus!\n\nThis is a simple text page with some info. Select the blog to read more text pages."),
                blog,
                new InputPage("Contact Form",
                        new StringInput("Name:", 20),
                        new StringInput("Email:", 100, "[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,3}", "john.smith@website.tld"),
                        new StringInput("Message:")
                )
        );

        mainMenu.callPage();

        AnsiUtils.clearScreen();
        AnsiUtils.printInBox("Thanks for using EasyMenus!", 2, 1, "Thanks for using EasyMenus!".length());
        AnsiUtils.uninstallConsole();
        InputUtils.closeScanner();

    }

}
