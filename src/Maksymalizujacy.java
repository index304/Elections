package wybory;

import java.util.ArrayList;
import java.util.Random;

// Implementacja wyborcy maksymalizującego daną cechę.
public class Maksymalizujacy extends Wyborca {
    private int nr_cechy;
    private String nazwa_partii;
    private int nr_partii;

    public Maksymalizujacy(String imie, String nazwisko, int nr_okregu, int typ,
                          boolean jedna_partia, String nazwa_partii, int nr_partii,
                           int nr_cechy) {
        super(imie, nazwisko, nr_okregu, typ, jedna_partia, nazwa_partii,
                nr_partii);
        this.nr_cechy = nr_cechy - 1;
    }

    public Kandydat glosuj(Kandydat[] kandydaci) {
        // Tablica do której będziemy dodawać nr kandydatów z maksymalną wartością
        // danej cechy.
        ArrayList<Integer> numery = new ArrayList<Integer>();
        int maksymalna = Integer.MIN_VALUE;

        for (int i = 0; i < kandydaci.length; i++) {
            if (kandydaci[i].getNr_cecha(nr_cechy) > maksymalna) {
                numery.clear();
                numery.add(i);
                maksymalna = kandydaci[i].getNr_cecha(nr_cechy);
            }
            else if (kandydaci[i].getNr_cecha(nr_cechy) == maksymalna) {
                numery.add(i);
            }
        }

        return kandydaci[numery.get(losuj(numery.size()))];
    }
}
