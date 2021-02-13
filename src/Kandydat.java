package wybory;

import java.util.Random;

// Implementacja klasy abstrakcyjnej dla wszystkich kandydatów.
public class Kandydat {
    private String imie;
    private String nazwisko;
    private String nazwa_partii;
    private int pozycja_na_liscie;
    private int nr_partii;
    private int[] cechy;
    private int nr_okregu;
    private int nr_kandydata;

    public Kandydat(String imie, String nazwisko, int nr_okregu,
                    String nazwa_partii, int nr_partii, int pozycja_na_liscie,
                    int[] cechy, int nr_kandydata) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.nr_okregu = nr_okregu;
        this.nazwa_partii = nazwa_partii;
        this.nr_partii = nr_partii;
        this.pozycja_na_liscie = pozycja_na_liscie;
        this.cechy = cechy;
        this.nr_kandydata = nr_kandydata;
    }

    public int getNr_cecha(int nr) {
        return cechy[nr];
    }

    public int getPozycja_na_liscie() {
        return pozycja_na_liscie;
    }

    public int getNr_partii() {
        return nr_partii;
    }

    public int getNr_kandydata() {
        return nr_kandydata;
    }

    public String getNazwa_partii() {
        return nazwa_partii;
    }

    public String toString() {
        String wyraz = imie + " " + nazwisko;
        return wyraz;
    }
}
