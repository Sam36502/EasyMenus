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
        Input.openScanner();

        Menu testMenu1 = new Menu("Test Menu", "Option 1", "Option 2", "Option 3");
        testMenu1.callPage();

        AnsiUtils.setCursorPos(30, 3);
        AnsiUtils.uninstallConsole();
        Input.closeScanner();

    }

}
