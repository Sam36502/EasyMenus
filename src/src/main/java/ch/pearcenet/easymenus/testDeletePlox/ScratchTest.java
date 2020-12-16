package ch.pearcenet.easymenus.testDeletePlox;

import ch.pearcenet.easymenus.util.InputUtils;
import ch.pearcenet.easymenus.input.BoolInput;
import ch.pearcenet.easymenus.input.StringInput;
import ch.pearcenet.easymenus.pages.*;
import ch.pearcenet.easymenus.util.AnsiUtils;

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

        Page mainMenu = new MenuPage("Test System with a long title",
                "None",
                "Exit",
                new TextPage(
                        "Menu Intro",
                        "Hello!\n" +
                                "Thanks for using EasyMenus!\n" +
                                "\n" +
                                "This is a simple text page with some info. Select the blog to read more text pages. " +
                                "Here are multiple spaces:    What happens to those?"
                ),
                new InputPage("Contact Form",
                        new StringInput("Name:", 20),
                        new StringInput("Email:", 100, "[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,3}", "john.smith@website.tld"),
                        new BoolInput("Why are you gay?"),
                        new StringInput("Message:")
                ),
                new TextPage(
                        "Long String tests",
                        "This is to test newlines:\n" +
                                "Here's a very long sentence that will hopefully be broken on the spaces. " +
                                "If you want to set which characters the program will break on, it's the " +
                                "Constant 'LINE_BREAK_CHARACTERS'.\n" +
                                "\n" +
                                "Now-a-test-of-breaking-on-hyphens.-I'm-still-not-sure-how-long-this-needs-to-be-ughhh.\n" +
                                "\n" +
                                "Nowthisisunbreakablebecausetherearenospacesorhyphensinit.Whatwillthealgorithmdo?Iwonder...\n"
                ),
                new MenuPage(
                        "Secret Blog",
                        "<WIP BUGGY DON'T SELECT>",
                        new TextPage(
                                "Day 1",
                                "Today, I didn't do very much."
                        ),
                        new TextPage(
                                "Day 2 - Dev time!",
                                "Day 2",
                                "After school today, I decided to do some programming and managed to fix a " +
                                        "particularly annoying bug while simultaneously cleaning up some code that had " +
                                        "been bothering me.\n" +
                                        "\n" +
                                        "I'd say today went well, but I've got work tomorrow..."
                        )
                ),
                new PrimeFinderInputPage()
        );
        mainMenu.callPage();

        AnsiUtils.clearScreen();
        AnsiUtils.printInBox("Thanks for using EasyMenus!", 2, 1, "Thanks for using EasyMenus!".length());
        AnsiUtils.uninstallConsole();
        InputUtils.closeScanner();

    }

}
