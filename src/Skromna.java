package wybory;

import java.util.ArrayList;

// Implementacja skromnej strategii.
public class Skromna extends Strategia {

    public Skromna() {
        super();
    }

    public Para wykonaj(Kandydat[][] kandydaci, int[][] wektor,
                        int[] suma, int[] ilosc_wyborcow, int budzet) {
        // Tablica Par zawierająca parę (nr_okregu, nr_wektora), dla której
        // wykonanie działania jest najtańsze (w przypadku kiedy istniej
        // kilka takich działań będzie losowanie).
        ArrayList<Para> wartosci = new ArrayList<Para>();
        wartosci.add(new Para(-1, -1));

        int MIN = Integer.MAX_VALUE;
        for (int i = 0; i < ilosc_wyborcow.length; i++) {
            if (ilosc_wyborcow[i] > 0) {
                for (int j = 0; j < suma.length; j++) {
                    int koszt = suma[j] * ilosc_wyborcow[i];
                    if (koszt < MIN && budzet >= koszt) {
                        wartosci.clear();
                        wartosci.add(new Para(i, j));
                        MIN = koszt;
                    } else if (koszt == MIN && budzet >= koszt) {
                        wartosci.add(new Para(i, j));
                    }
                }
            }
        }

        return wartosci.get(losuj(wartosci.size()));
    }
}
