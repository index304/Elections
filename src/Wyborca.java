package wybory;

import java.util.Random;

// Implementacją klasy abstrakcyjnej dla wyborców.
public abstract class Wyborca {
    private String imie;
    private String nazwisko;
    private int nr_okregu;
    private int typ;
    private boolean jedna_partia;
    private String nazwa_partii;
    private int nr_partii;

    public Wyborca(String imie, String nazwisko, int nr_okregu, int typ,
                   boolean jedna_partia, String nazwa_partii, int nr_partii) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.nr_okregu = nr_okregu;
        this.typ = typ;
        this.jedna_partia = jedna_partia;
        this.nazwa_partii = nazwa_partii;
        this.nr_partii = nr_partii;
    }

    abstract Kandydat glosuj(Kandydat[] kandydaci);

    public int getTyp() {
        return typ;
    }

    public boolean getJedna_partia() {
        return jedna_partia;
    }

    // Zwraca losową liczbę z danego zakresu.
    public int losuj(int zakres) {
        Random random = new Random();
        int liczba = random.nextInt(zakres);
        return liczba;
    }

    public int getNr_partii() {
        return nr_partii;
    }

    public boolean isJedna_partia() {
        return jedna_partia;
    }

    public String toString() {
        String wyraz = imie + " " + nazwisko;
        return wyraz;
    }
}