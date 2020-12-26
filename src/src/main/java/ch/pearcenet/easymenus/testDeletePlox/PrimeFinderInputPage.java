package ch.pearcenet.easymenus.testDeletePlox;

import ch.pearcenet.easymenus.input.IntInput;
import ch.pearcenet.easymenus.pages.InputPage;
import ch.pearcenet.easymenus.pages.LoadedPage;
import ch.pearcenet.easymenus.pages.LoadingPage;

public class PrimeFinderInputPage extends InputPage {

    private IntInput maxPrimeIn;

    public PrimeFinderInputPage() {
        super("Prime Computer");
        maxPrimeIn = new IntInput(
                "Maximum Prime to Compute:",
                3,
                100000
        );
        super.getInputs().add(maxPrimeIn);
    }

    @Override
    public void callPage() {
        super.callPage();

        int estNumPrimes = (int) (maxPrimeIn.getAnswer() / Math.log(maxPrimeIn.getAnswer()));

        LoadedPage next = new PrimeFinderPage(maxPrimeIn.getAnswer());
        LoadingPage load = new LoadingPage(
                "Computing Primes:",
                "Computing...\n" +
                        "\n" +
                        "Fun fact: Primes are kinda weird.\n" +
                        "That's it. They just don't really make sense.",
                next,
                estNumPrimes
        );
        load.callPage();

    }
}
