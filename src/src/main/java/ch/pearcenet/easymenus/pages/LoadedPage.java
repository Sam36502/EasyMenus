package ch.pearcenet.easymenus.pages;

/**
 * Loaded Page Interface
 * implement this to make your code callable
 * by menu options and loadable with a
 * loading screen.
 */
public interface LoadedPage extends Page {

    public void load(LoadingPage loadingPage);

}
