package wybory;

// Klasa abstrakcyjna dla wszystkich metod.
public abstract class Metoda {
    public Metoda() {}

    // Abstrakcyjna funkcja rozdająca mandaty.
    public abstract int[] rozdaj_mandaty(Okreg okreg, int liczba_partii);
}
