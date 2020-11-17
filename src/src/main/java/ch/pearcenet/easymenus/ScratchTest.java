package ch.pearcenet.easymenus;

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

        AnsiUtils.printInBoxWithTitle(
                "'Twas brillig and the slithy toves did gyre and gimble in the wabe. All mimsy were the borogoves, and the mome raths outgrabe.",
                "Jabberwocky", 10, 5, 30);

        AnsiUtils.setCursorPos(20, 0);

        AnsiUtils.uninstallConsole();

    }

}
