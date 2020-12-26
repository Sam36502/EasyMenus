package ch.pearcenet.easymenus.pages;

import ch.pearcenet.easymenus.input.Input;
import ch.pearcenet.easymenus.util.AnsiUtils;
import ch.pearcenet.easymenus.util.Constants;
import ch.pearcenet.easymenus.util.InputUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class InputPage implements LoadedPage {

    private Scanner input = new Scanner(System.in);

    private ArrayList<Input> inputs;

    private String title;

    private String optionName;

    private Page nextPage;

    public InputPage(String title, Input... inputs) { this(title, title, inputs); }

    public InputPage(String title, Page nextPage, Input... inputs) { this(title, title, nextPage, inputs); }

    public InputPage(String title, String optionName, Input... inputs) { this(title, title, null, inputs); }

    public InputPage(String title, String optionName, Page nextPage, Input... inputs) {
        this.title = title;
        this.optionName = optionName;
        this.nextPage = nextPage;
        this.inputs = new ArrayList<>(Arrays.asList(inputs));
    }

    @Override
    public String getOptionName() {
        return optionName;
    }

    @Override
    public void callPage() {
        boolean confirmed = false;
        while (!confirmed) {
            for (Input field : inputs) {
                field.displayPrompt(title, input);
            }
            AnsiUtils.clearScreen();
            AnsiUtils.printInBox(title,
                    AnsiUtils.getSettingsInt(Constants.LAYOUT_PAGE_MARGIN_LEFT),
                    AnsiUtils.getSettingsInt(Constants.LAYOUT_PAGE_MARGIN_TOP),
                    title.length());

            for (Input field : inputs) {
                AnsiUtils.printWithMargins(
                        "\n" + AnsiUtils.renderWithUnderline(field.getName()) + "\n",
                        AnsiUtils.getSettingsInt(Constants.LAYOUT_PAGE_MARGIN_LEFT)
                );
                System.out.println("  > " + field.getAnswer());
            }

            // Confirm Inputs
            AnsiUtils.printWithMargins(
                    "\nIs this correct?\n",
                    AnsiUtils.getSettingsInt(Constants.LAYOUT_PAGE_MARGIN_LEFT));
            confirmed = InputUtils.getBool();
        }

        if (nextPage != null) { nextPage.callPage(this); }
    }

    @Override
    public void callPage(InputPage inputPage) {
        callPage();
    }

    public ArrayList<Input> getInputs() {
        return inputs;
    }

    @Override
    public void load(LoadingPage loadingPage) {
        loadingPage.completeAll();
    }
}
