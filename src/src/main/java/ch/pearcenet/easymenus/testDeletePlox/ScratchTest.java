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
        AnsiUtils.loadDefaultStyle();
        //AnsiUtils.loadStyleFile(".\\test_style.txt");

        MenuPage blog = new MenuPage(
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
                                "I'd say today went well, but I've got work tomorrow...")
        );
        blog.getOptions().add(new InputPage(
                "New Blog Entry",
                new NewBlogPage(blog),
                new StringInput("Entry Title"),
                new StringInput("Entry Text")
        ));

        Page mainMenu = new MenuPage("Test System",
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
                blog,
                new PrimeFinderInputPage(),
                new PopupPage(),
                new TextPage(
                        "Jabberwocky - by Lewis Carroll",
                        "Jabberwocky",
                        "'Twas brillig and the slithy toves\n" +
                                "did gyre and gimble in the wabe.\n" +
                                "All mimsy were the borogoves,\n" +
                                "and the mome raths outgrabe.",
                        new TextPage(
                                "Jabberwocky - by Lewis Carroll",
                                "\"Beware the Jabberwock my boy!\n" +
                                        "The jaws that bite; the claws that catch!\n" +
                                        "Beware the jub-jub bird and\n" +
                                        "shun the frumious Bandersnatch\"",
                                new TextPage(
                                        "Jabberwocky - by Lewis Carroll",
                                        "He took his vorpal sword in hand.\n" +
                                                "Long time the manxsome foe he sought.\n" +
                                                "So rested he by the tum-tum tree,\n" +
                                                "and stood a while in thought.",
                                        new TextPage(
                                                "Jabberwocky - by Lewis Carroll",
                                                "and as in uffish thought he stood,\n" +
                                                        "the Jabberwock with eyes of flame,\n" +
                                                        "came whiffling through the tulgey wood\n" +
                                                        "and burbled as it came.",
                                                new TextPage(
                                                        "Jabberwocky - by Lewis Carroll",
                                                        "One two, one two, and through and through!\n" +
                                                                "The vorpal blade went snicker-snack.\n" +
                                                                "He left it dead, and with its head,\n" +
                                                                "he went gallumphing back.",
                                                        new TextPage(
                                                                "Jabberwocky - by Lewis Carroll",
                                                                "\"And hast thou slain the Jabberwock?\n" +
                                                                        "O come to my arms my beamish boy!\n" +
                                                                        "O frabjous day, calooh calay\",\n" +
                                                                        "He chortled in his joy",
                                                                new TextPage(
                                                                        "Jabberwocky - by Lewis Carroll",
                                                                        "'Twas Brillig and the slithy toves\n" +
                                                                                "did gyre and gimble in the wabe.\n" +
                                                                                "All mimsy were the borogoves,\n" +
                                                                                "and the mome raths outgrabe."
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                ),
                new MarkupTextPage(
                        "Markup Test Page",
                        "<h1>This is a markup page!</h1><br>" +
                                "<br>" +
                                "If all went well, this page should look much cooler than the\n" +
                                "others, because some elements are formatted:<br>" +
                                "<br>" +
                                "<t><mark>This text has been highlighted using the <lt>mark<gt> tag!</mark><br>" +
                                "<br>" +
                                "<t><strong>This text has been emphasized with the <lt>strong<gt> tag</strong><br>" +
                                "<br>" +
                                "<t><col fg=black; bg=cyan;>Whee the <lt>col<gt> tag is so cool!</col><br><br>"
                )
        );
        mainMenu.callPage();

        AnsiUtils.clearScreen();
        AnsiUtils.printInBox("Thanks for using EasyMenus!", 2, 1, "Thanks for using EasyMenus!".length());
        AnsiUtils.uninstallConsole();
        InputUtils.closeScanner();

    }

}
