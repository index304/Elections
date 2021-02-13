package wybory;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        // Rozpoczynam wczytywanie z pliku, którego nazwa jest podana jako
        // pierwszy i jedyny argument.
        Wczytywanie wczytywanie = new Wczytywanie(args[0]);
    }
}
