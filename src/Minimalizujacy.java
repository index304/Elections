package wybory;

import java.util.ArrayList;
import java.util.Random;

// Implementacja wyborcy minimalizującego daną cechę.
public class Minimalizujacy extends Wyborca {
    private int nr_cechy;

    public Minimalizujacy(String imie, String nazwisko, int nr_okregu, int typ,
                           boolean jedna_partia, String nazwa_partii, int nr_partii,
                           int nr_cechy) {
        super(imie, nazwisko, nr_okregu, typ, jedna_partia, nazwa_partii,
                nr_partii);
        this.nr_cechy = nr_cechy - 1;
    }


    public Kandydat glosuj(Kandydat[] kandydaci) {
        // Tablica do której będziemy dodawać nr kandydatów z minimalną wartością
        // danej cechy.
        ArrayList<Integer> numery = new ArrayList<Integer>();
        int minimalna = Integer.MAX_VALUE;

        for (int i = 0; i < kandydaci.length; i++) {
            if (kandydaci[i].getNr_cecha(nr_cechy) < minimalna) {
                numery.clear();
                numery.add(i);
                minimalna = kandydaci[i].getNr_cecha(nr_cechy);
            }
            else if (kandydaci[i].getNr_cecha(nr_cechy) == minimalna) {
                numery.add(i);
            }
        }

        return kandydaci[numery.get(losuj(numery.size()))];
    }
}
