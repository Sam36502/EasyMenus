package ch.pearcenet.easymenus.testDeletePlox;

import ch.pearcenet.easymenus.pages.*;

public class NewBlogPage implements Page {

    private MenuPage blog;

    public NewBlogPage(MenuPage blog) {
        this.blog = blog;
    }

    @Override
    public String getOptionName() {
        return "New Entry";
    }

    @Override
    public void callPage() { }

    @Override
    public void setNext(LoadedPage nextPage) { }

    @Override
    public void callPage(InputPage inputPage) {
        String title = (String) inputPage.getInputs().get(0).getAnswer();
        String text = (String) inputPage.getInputs().get(1).getAnswer();

        blog.getOptions().add(new TextPage(title, text));
    }

}
