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

    public static Colour parseColour(final String name) {
        String upperName = name.toUpperCase();
        switch (upperName) {
            case "BLACK": return new Colour(Ansi.Color.BLACK, false);
            case "BLUE": return new Colour(Ansi.Color.BLUE, false);
            case "CYAN": return new Colour(Ansi.Color.CYAN, false);
            case "GREEN": return new Colour(Ansi.Color.GREEN, false);
            case "MAGENTA": return new Colour(Ansi.Color.MAGENTA, false);
            case "RED": return new Colour(Ansi.Color.RED, false);
            case "WHITE": return new Colour(Ansi.Color.WHITE, false);
            case "YELLOW": return new Colour(Ansi.Color.YELLOW, false);

            case "BRIGHT_BLACK": return new Colour(Ansi.Color.BLACK, true);
            case "BRIGHT_BLUE": return new Colour(Ansi.Color.BLUE, true);
            case "BRIGHT_CYAN": return new Colour(Ansi.Color.CYAN, true);
            case "BRIGHT_GREEN": return new Colour(Ansi.Color.GREEN, true);
            case "BRIGHT_MAGENTA": return new Colour(Ansi.Color.MAGENTA, true);
            case "BRIGHT_RED": return new Colour(Ansi.Color.RED, true);
            case "BRIGHT_WHITE": return new Colour(Ansi.Color.WHITE, true);
            case "BRIGHT_YELLOW": return new Colour(Ansi.Color.YELLOW, true);

            default:
                AnsiUtils.popupBox(
                        "Attempted to parse a colour, but invalid colour name given:\n" +
                            " Name: '" + name + "'",
                        "! ERROR !",
                        AnsiUtils.getSettingsColour(Constants.COLOUR_ERROR_FG),
                        AnsiUtils.getSettingsColour(Constants.COLOUR_ERROR_BG),
                        AnsiUtils.getSettingsColour(Constants.COLOUR_ERROR_FG),
                        AnsiUtils.getSettingsColour(Constants.COLOUR_ERROR_BG)
                );
                System.exit(1);
                return null;
        }
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
