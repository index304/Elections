package wybory;

import java.util.ArrayList;
import java.util.Collections;

// Implementacja metody Hare'a Niemeyera.
public class HareaNiemeyera extends Metoda {

    public HareaNiemeyera() {
        super();
    }

    public int[] rozdaj_mandaty(Okreg okreg, int liczba_partii) {
        int liczba_kandydatow =  okreg.getLiczba_wyborcow() / 10;
        int[] mandaty = new int[liczba_partii];
    
        // Wrzucamy do tablicy Pary (wartość, nr_partii), a następnie
        // sortujemy te wartości po pierwszej współrzędnej.
        ArrayList<Para> zbior = new ArrayList<Para>();
        // Wrzucamy do tablicy Pary (wartość_po_przecinku, nr_partii), okazuje
        // się, że wartość po przecinku, to tak naprawdę liczba głosów % 10.
        ArrayList<Para> modulo = new ArrayList<Para>();
        for (int i = 0; i < liczba_partii; i++) {
            double glosy = (double)(okreg.getGlosy(i) / 10);
            int po_przecinku = okreg.getGlosy(i) % 10;
            zbior.add(new Para(glosy, i));
            modulo.add(new Para((double)po_przecinku, i));
        }

        Collections.sort(zbior);
        Collections.sort(modulo);
        
        int suma = 0;
        // Przyznajemy mandaty i liczymy sumę wartości przed przecinkiem.
        for (int i = 0; i < zbior.size(); i++) {
            mandaty[zbior.get(i).getSecond()] += (int)zbior.get(i).getValue();
        }

        for (int i = 0; i < liczba_partii; i++) {
            suma += mandaty[i];
        }

        // Rozdajemy pozostałe mandaty względem malejących wartości po przecinku.
        int pozostalo = liczba_kandydatow - suma;
        for (int i = 0; i < modulo.size(); i++) {
            if (pozostalo == 0) {
                break;
            }
            else {
                mandaty[zbior.get(i).getSecond()]++;
                pozostalo--;
            }
        }

        return mandaty;
    }
}
