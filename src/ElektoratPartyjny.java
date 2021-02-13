package wybory;

import java.util.Random;

// Implementacja wyborcy głosującego na członka danej partii.
public class ElektoratPartyjny extends Wyborca {
    private String nazwa_partii;

    ElektoratPartyjny (String imie, String nazwisko, int nr_okregu, int typ,
                       boolean jedna_partia, String nazwa_partii, int nr_partii) {
        super(imie, nazwisko, nr_okregu, typ, jedna_partia, nazwa_partii,
              nr_partii);
        this.nazwa_partii = nazwa_partii;
    }

    // Głosowanie na losowego członka danej partii.
    public Kandydat glosuj(Kandydat[] kandydaci) {
        return kandydaci[losuj(kandydaci.length)];
    }
}
