package ch.pearcenet.easymenus.util;

import org.fusesource.jansi.Ansi;

/*

    Handles colour so that bright
    and dark colours are available

 */
public class Colour {

    private Ansi.Color ansiColour;
    private boolean isBright;

    private Colour(final Ansi.Color ansiColour, final boolean isBright) {
        this.ansiColour = ansiColour;
        this.isBright = isBright;
    }

    public Ansi.Color getAnsiColour() {
        return ansiColour;
    }

    public boolean isBright() {
        return isBright;
    }

    // Colour Constants

    public static final Colour BLACK = new Colour(Ansi.Color.BLACK, false);
    public static final Colour BLUE = new Colour(Ansi.Color.BLUE, false);
    public static final Colour CYAN = new Colour(Ansi.Color.CYAN, false);
    public static final Colour GREEN = new Colour(Ansi.Color.GREEN, false);
    public static final Colour MAGENTA = new Colour(Ansi.Color.MAGENTA, false);
    public static final Colour RED = new Colour(Ansi.Color.RED, false);
    public static final Colour WHITE = new Colour(Ansi.Color.WHITE, false);
    public static final Colour YELLOW = new Colour(Ansi.Color.YELLOW, false);

    public static final Colour BRIGHT_BLACK = new Colour(Ansi.Color.BLACK, true);
    public static final Colour BRIGHT_BLUE = new Colour(Ansi.Color.BLUE, true);
    public static final Colour BRIGHT_CYAN = new Colour(Ansi.Color.CYAN, true);
    public static final Colour BRIGHT_GREEN = new Colour(Ansi.Color.GREEN, true);
    public static final Colour BRIGHT_MAGENTA = new Colour(Ansi.Color.MAGENTA, true);
    public static final Colour BRIGHT_RED = new Colour(Ansi.Color.RED, true);
    public static final Colour BRIGHT_WHITE = new Colour(Ansi.Color.WHITE, true);
    public static final Colour BRIGHT_YELLOW = new Colour(Ansi.Color.YELLOW, true);

}
