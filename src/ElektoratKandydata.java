package wybory;

// Implementacja wyborców głosujących na konkretnego kandydata.
public class ElektoratKandydata extends Wyborca {
    private int nr_kandydata;

    ElektoratKandydata(String imie, String nazwisko, int nr_okregu, int typ,
                       boolean jedna_partia, String nazwa_partii, int nr_partii,
                       int nr_kandydata) {
        super(imie, nazwisko, nr_okregu, typ, jedna_partia, nazwa_partii,
                nr_partii);
        this.nr_kandydata = nr_kandydata;
    }

    // Głosujemy na konkretnego kandydata.
    public Kandydat glosuj(Kandydat[] kandydaci) {
        for (int i = 0; i < kandydaci.length; i++) {
            if (kandydaci[i].getPozycja_na_liscie() == nr_kandydata) {
                return kandydaci[i];
            }
        }
        return null;
    }
}
