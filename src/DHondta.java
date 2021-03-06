package wybory;

import java.util.ArrayList;
import java.util.Collections;

// Implementacja Metody D'Hondta.
public class DHondta extends Metoda {
    public DHondta() {
        super();
    }

    // Funkcja rozdająca mandaty.
    public int[] rozdaj_mandaty(Okreg okreg, int liczba_partii) {
        int liczba_kandydatow =  okreg.getLiczba_wyborcow() / 10;
        int[] mandaty = new int[liczba_partii];

        ArrayList<Para> zbior= new ArrayList<Para>();
        // Wrzucamy do tablicy Pary (wartość, nr_partii), a następnie
        // sortujemy te wartości po pierwszej współrzędnej.
        for (int i = 0; i < liczba_partii; i++) {
            double glosy = okreg.getGlosy(i);
            for (int j = 0; j < liczba_kandydatow; j++) {
                zbior.add(new Para(Math.round(glosy / ((double) j + 1)), i));
            }
        }

        Collections.sort(zbior);
        // Przyznajemy mandaty w kolejności malejącej.
        for (int i = 0; i < liczba_kandydatow; i++) {
            mandaty[zbior.get(i).getSecond()]++;
        }
        return mandaty;
    }
}
