package wybory;

import java.util.ArrayList;

// Strategia zachłanna, wybierająca takie działania, które w jak największym
// stopniu zwiększą sumę sum ważonych swoich kandydatów.
public class Zachlanna extends Strategia {
    public Zachlanna() {
        super();
    }

    public Para wykonaj(Kandydat[][] kandydaci, int[][] wektor,
                        int[] suma, int[] ilosc_wyborcow, int budzet) {
        // Tablica Par zawierająca parę (nr_okregu, nr_wektora), dla której
        // zmiana wag jest najkorzystniejsza.
        ArrayList<Para> wartosci = new ArrayList<Para>();
        wartosci.add(new Para(-1, -1));

        int suma_max = Integer.MIN_VALUE;
    
        // Przechodzę po okręgach.
        for (int i = 0; i < ilosc_wyborcow.length; i++) {
            if (ilosc_wyborcow[i] > 0) {
                // Przechodzę po wektorach zmian.
                for (int j = 0; j < wektor.length; j++) {
                    if (budzet >= ilosc_wyborcow[i] * suma[j]) {
                        int suma_wazona = 0;
                        // Przechodzę po kandydatach.
                        for (int k = 0; k < kandydaci[i].length; k++) {
                            for (int s = 0; s < wektor[j].length; s++) {
                                // Aktualizujemy teraźniejszą sumę ważoną.
                                suma_wazona += wektor[j][s] *
                                               kandydaci[i][k].getNr_cecha(s);
                            }
                        }

                        // Porównywanie otrzymanej sumy ważonej.
                        if (suma_wazona > suma_max) {
                            wartosci.clear();
                            wartosci.add(new Para(i, j));
                            suma_max = suma_wazona;
                        } else if (suma_wazona == suma_max) {
                            wartosci.add(new Para(i, j));
                        }
                    }
                }
            }
        }

        return wartosci.get(losuj(wartosci.size()));
    }
}
