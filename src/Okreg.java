package wybory;

// Implementacja klasy będącej Okręgiem wyborczym.
public class Okreg {
    private Kandydat[] kandydaci;
    private Wyborca[] wyborcy;
    private int liczba_wyborcow;
    private int liczba_partii;
    private int[] liczba_glosow;
    private int[] glos_osoba;
    private int[] partie_glosy;
    private int[] mandaty;

    public Okreg(Kandydat[] kandydaci, Wyborca[] wyborcy, int liczba_wyborcow,
                  int liczba_partii) {
        this.wyborcy = wyborcy;
        this.kandydaci = kandydaci;
        this.liczba_wyborcow = liczba_wyborcow;
        this.liczba_partii = liczba_partii;

        liczba_glosow = new int[kandydaci.length];
        partie_glosy = new int[liczba_partii];
        for (int i = 0; i < kandydaci.length; i++) {
            liczba_glosow[i] = 0;
        }

        for (int i = 0; i < liczba_partii; i++) {
            partie_glosy[i] = 0;
        }

        glos_osoba = new int[wyborcy.length];
        for (int i = 0; i < wyborcy.length; i++)
            glos_osoba[i] = 0;

        mandaty = new int[liczba_partii];
        for (int i = 0; i < liczba_partii; i++) {
            mandaty[i] = 0;
        }
    }

    // Zmieniam wagi wszystkich wyborców, zgodnie z przyjętą strategią.
    public void zmien_wagi(int[] wektor) {
        for (int i = 0; i < liczba_wyborcow; i++) {
            if (wyborcy[i] instanceof Wszeschstronni) {
                Wszeschstronni zmien = (Wszeschstronni)wyborcy[i];
                zmien.zmien_wagi(wektor);
                wyborcy[i] = zmien;

            }
        }
    }

    public Kandydat[] getKandydaci() {
        return kandydaci;
    }

    public Wyborca[] getWyborcy() {
        return wyborcy;
    }
    
    // Oddaje głos na kandydata o danym numerze.
    public void oddaj_glos(int nr_wyborcy, int nr) {
        this.liczba_glosow[nr]++;
        this.glos_osoba[nr_wyborcy] = nr;
        this.partie_glosy[kandydaci[nr].getNr_partii()]++;
    }

    // Zapisujące wyborcy nr. kandydata, na którego głosował.
    public int getGlos_osoba(int nr_wyborcy) {
        return glos_osoba[nr_wyborcy];
    }
    
    // Zwraca ilość głosów oddanych na partię o danym numerze.
    public int getGlosy(int nr) {
        return this.partie_glosy[nr];
    }

    // Zwraca ilość głosów oddanych na danego kandydata.
    public int getGlosy_kandydat(int nr) {
        return this.liczba_glosow[nr];
    }

    // Zwraca nr. partii, do której należy kandydat o danym numerze.
    public int getNr_partii(int nr) {
        return kandydaci[nr].getNr_partii();
    }

    // Zwraca liczbę wyborców w danym okręgu.
    public int getLiczba_wyborcow() {
        return liczba_wyborcow;
    }

    // Ustawia liczbę mandatów.
    public void set_mandaty(int[] mandaty) {
        this.mandaty = mandaty;
    }

    // Zwraca liczbę mandatów partii o danym numerze.
    public int getMandaty(int nr) {
        return this.mandaty[nr];
    }

    // Zeruje tablicę zawierającą przyznane mandaty.
    public void zeruj_mandaty() {
        for (int i = 0; i < liczba_partii; i++) {
            mandaty[i] = 0;
        }
    }
}
