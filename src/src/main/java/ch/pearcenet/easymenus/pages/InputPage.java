package ch.pearcenet.easymenus.pages;

import ch.pearcenet.easymenus.input.Input;
import ch.pearcenet.easymenus.util.AnsiUtils;
import ch.pearcenet.easymenus.util.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class InputPage implements LoadedPage {

    private Scanner input = new Scanner(System.in);

    private ArrayList<Input> inputs;

    private String title;

    private String optionName;

    public InputPage(String title, Input... inputs) {
        this.title = title;
        this.optionName = title;
        this.inputs = new ArrayList<>(Arrays.asList(inputs));
    }

    public InputPage(String title, String optionName, Input... inputs) {
        this.title = title;
        this.optionName = optionName;
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
                    Constants.DEFAULT_PAGE_MARGIN_LEFT,
                    Constants.DEFAULT_PAGE_MARGIN_TOP,
                    title.length());

            for (Input field : inputs) {
                AnsiUtils.printWithMargins(
                        "\n" + AnsiUtils.renderWithUnderline(field.getName()) + "\n",
                        Constants.DEFAULT_PAGE_MARGIN_LEFT
                );
                System.out.println("  > " + field.getAnswer());
            }

            // Confirm Inputs
            AnsiUtils.printWithMargins("\nIs this correct? (y/n)\n", Constants.DEFAULT_PAGE_MARGIN_LEFT);
            System.out.print("  > ");
            String in = input.nextLine();
            if ("y".equals(in) || "yes".equals(in)) {
                confirmed = true;
            }
        }
    }

    //TODO: Remove?
    public ArrayList<Input> getInputs() {
        return inputs;
    }

    @Override
    public void load(LoadingPage loadingPage) {
        loadingPage.completeAll();
    }
}
