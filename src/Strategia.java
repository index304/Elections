package wybory;

import java.util.Random;

// Implementacja abstrakcyjnej klasy dla wszystkich Strategii.
public abstract class Strategia {
    public Strategia() {}
    
    // Funkcja odpowiedzialna za wykonanie strategii.
    public abstract Para wykonaj(Kandydat[][] kandydaci, int[][] wektor,
                                 int[] suma, int[] ilosc_wyborcow, int budzet);

    // Losujemy liczbę z danego zakresu.
    public int losuj(int zakres) {
        Random random = new Random();
        int liczba = random.nextInt(zakres);
        return liczba;
    }
}
