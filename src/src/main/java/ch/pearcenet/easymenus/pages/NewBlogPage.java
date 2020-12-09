package ch.pearcenet.easymenus.pages;

import ch.pearcenet.easymenus.ScratchTest;
import ch.pearcenet.easymenus.input.Input;
import ch.pearcenet.easymenus.input.StringInput;

public class NewBlogPage extends InputPage implements Page {

    public NewBlogPage() {
        super("Create New Blog Entry", "New Entry",
                new StringInput("Title"),
                new StringInput("Content"));
    }

    @Override
    public void callPage() {
        super.callPage();

        String title = (String) super.getInputs().get(0).getAnswer();
        String content = (String) super.getInputs().get(1).getAnswer();

        ScratchTest.blog.addOption(new TextPage(title, content));
    }
}
