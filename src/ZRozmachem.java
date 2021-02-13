package wybory;

import java.util.ArrayList;
import java.util.Random;

// Strategia preferująca jak najdroższe działania.
public class ZRozmachem extends Strategia {
    public ZRozmachem() {
        super();
    }

    public Para wykonaj(Kandydat[][] kandydaci, int[][] wektor,
                                 int[] suma, int[] ilosc_wyborcow, int budzet) {
        // Tablica Par zawierająca parę (nr_okregu, nr_wektora), dla której
        // wykonanie działania jest najdroższe.
        ArrayList<Para> wartosci = new ArrayList<Para>();
        wartosci.add(new Para(-1, -1));

        int MAX = Integer.MIN_VALUE;
        for (int i = 0; i < ilosc_wyborcow.length; i++) {
            if (ilosc_wyborcow[i] > 0) {
                for (int j = 0; j < suma.length; j++) {
                    int koszt = suma[j] * ilosc_wyborcow[i];
                    if (koszt > MAX && budzet >= koszt) {
                        wartosci.clear();
                        wartosci.add(new Para(i, j));
                        MAX = koszt;
                    } else if (koszt == MAX && budzet >= koszt) {
                        wartosci.add(new Para(i, j));
                    }
                }
            }
        }

        return wartosci.get(losuj(wartosci.size()));
    }
}
