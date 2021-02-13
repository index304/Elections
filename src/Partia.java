package wybory;

// Implementacja klasy Partia, zawierająca kandydatów danej partii, z podziałem
// na okręgi.
public class Partia {
    private Kandydat[][] kandydaci;
    private String nazwa;
    private int[] mandaty;
    private int suma_mandatow;
    private int budzet;
    private int liczba_glosow;
    private Strategia strategia;

    public Partia(String nazwa, Kandydat[][] kandydaci, Strategia strategia,
                  int budzet) {
        this.nazwa = nazwa;
        this.kandydaci = kandydaci;
        this.strategia = strategia;
        this.budzet = budzet;
        this.liczba_glosow = 0;

        int liczba_okregow = kandydaci.length;
        
        suma_mandatow = 0;
        mandaty = new int[liczba_okregow];
        for (int i = 0; i < liczba_okregow; i++) {
            mandaty[i] = 0;
        }
    }

    public Kandydat[] getKandydaci(int nr_okregu) {
        return kandydaci[nr_okregu];
    }

    // Funkcja wykonująca strategię jaką posiada dana partia.
    public Para wykonaj_strategie(Kandydat[][] kandydaci, int[][] wektor,
                                  int[] suma, int[] ilosc_wyborcow, int budzet) {
        return strategia.wykonaj(kandydaci, wektor, suma, ilosc_wyborcow, budzet);
    }

    // Wydajemy pieniądze na realizację danej jakiejś strategii.
    public void wydaj(int ile) {
        this.budzet -= ile;
    }

    public int getBudzet() {
        return this.budzet;
    }

    public void dodaj_mandaty(int nr_okregu, int value) {
        mandaty[nr_okregu] += value;
    }

    // Zwraca sumę mandatów dla danej partii.
    public int getSuma_mandatow() {
        licz_suma();
        return this.suma_mandatow;
    }

    // Liczy sumę wszystkich mandatów partii.
    private void licz_suma() {
        int liczba_okregow = kandydaci.length;
        for (int i = 0; i < liczba_okregow; i++) {
            suma_mandatow += mandaty[i];
        }
    }

    // Zeruje przyznane mandaty.
    public void zeruj_mandaty() {
        int liczba_okregow = kandydaci.length;
        for (int i = 0; i < liczba_okregow; i++) {
            mandaty[i] = 0;
        }
        suma_mandatow = 0;
    }

    public String getNazwa() {
        return nazwa;
    }

}