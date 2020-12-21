package ch.pearcenet.easymenus.testDeletePlox;

import ch.pearcenet.easymenus.pages.LoadingPage;
import ch.pearcenet.easymenus.pages.TextPage;

public class PrimeFinderPage extends TextPage {

    private int maxPrime;

    public PrimeFinderPage(int maxPrime) {
        super(
                "Primes Found:",
                "1, 2, 3"
        );
        this.maxPrime = maxPrime;
    }

    private boolean isPrime(int p) {
        for (int i=2; i<p; i++) {
            if (p % i == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void load(LoadingPage loadingPage) {
        for (int i=3; i<maxPrime; i++) {
            if (isPrime(i)) {
                setContent(getContent() + ", " + i);
                loadingPage.taskDone();
            }
        }
        loadingPage.completeAll();
    }
}
