package wybory;

import java.util.ArrayList;
import java.util.Random;

// Implementacją wyborcy patrzącego na wszystkie cechy kandydatów.
public class Wszeschstronni extends  Wyborca {
    private int[] waga;
    private String nazwa_partii;
    private int nr_partii;

    public Wszeschstronni(String imie, String nazwisko, int nr_okregu, int typ,
                          boolean jedna_partia, int[] waga, String nazwa_partii,
                          int nr_partii) {
        super(imie, nazwisko, nr_okregu, typ, jedna_partia, nazwa_partii,
              nr_partii);
        this.waga = waga;
        this.nazwa_partii = nazwa_partii;
        this.nr_partii = nr_partii;
    }

    public Kandydat glosuj(Kandydat[] kandydaci) {
        int max = Integer.MIN_VALUE;
        int ilosc_cech = waga.length;

        // Tablica zawięrająca numery wyborców o największej sumie cech.
        ArrayList<Integer> numery = new ArrayList<Integer>();
        
        // Znajdujemy kandydata o jak największej sumie ważonej cech.
        for (int i = 0; i < kandydaci.length; i++) {
            int suma = 0;
            for (int j = 0; j < ilosc_cech; j++) {
                suma += waga[j] * kandydaci[i].getNr_cecha(j);
            }

            if (suma > max) {
                numery.clear();
                numery.add(i);
                max = suma;
            }
            else if (suma == max) {
                numery.add(i);
                max = suma;
            }
        }

        return kandydaci[losuj(numery.size())];
    }

    // Funkcja odpowiedzialna za zmienianie wag cech wyborców.
    public void zmien_wagi(int[] wektor) {
        for (int i = 0; i < wektor.length; i++) {
            waga[i] += wektor[i];
            waga[i] = Math.min(waga[i], 100);
            waga[i] = Math.max(waga[i], -100);
        }
    }

}
