package ch.pearcenet.easymenus;

import ch.pearcenet.easymenus.input.BoolInput;
import ch.pearcenet.easymenus.input.DoubleInput;
import ch.pearcenet.easymenus.input.IntInput;
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

        Page mainMenu = new MenuPage("Test System with an long title",
                new TextPage("Menu Intro", "Hello!\nThanks for using EasyMenus!\n\nThis is a simple text page with some info. Select the blog to read more text pages."),
                new InputPage("Contact Form",
                        new StringInput("Name:", 20),
                        new StringInput("Email:", 100, "[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,3}", "john.smith@website.tld"),
                        new BoolInput("Why are you gay?"),
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
