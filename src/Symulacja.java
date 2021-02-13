package wybory;

// Klasa odpowiedzialna za przeprowadzenie symulacji wyborów.
public class Symulacja {
    private Okreg[] okregi;
    private Partia[] partie;
    private Kandydat[][] kandydaci;
    private int liczba_okregow;
    private int liczba_partii;
    private int[][] wektor;
    private int[] suma;
    private int[] ilosc_wyborcow;

    // Liczymy sumę wartości bezwzględnych dla wektorów zmian.
    private void make_suma() {
        suma = new int[wektor.length];
        for (int i = 0; i < wektor.length; i++) {
            int licz_suma = 0;
            for (int j = 0; j < wektor[i].length; j++) {
                licz_suma += Math.abs(wektor[i][j]);
            }
            suma[i] = licz_suma;
        }
    }

    public Symulacja(Okreg[] okregi, Kandydat[][] kandydaci, Partia[] partie,
                     int liczba_okregow, int liczba_partii, int[][] wektor,
                     int[] ilosc_wyborcow) {
        this.okregi = okregi;
        this.kandydaci = kandydaci;
        this.partie = partie;
        this.liczba_okregow = liczba_okregow;
        this.liczba_partii = liczba_partii;
        this.wektor = wektor;
        this.ilosc_wyborcow = ilosc_wyborcow;
        make_suma();
    }
    
    // Funkcja startująca kolejno: strategie partii, głosowanie, przydzielanie
    // mandatów oraz rozdawanie mandatów i wypisywanie na wyjście.
    public void start() {
        rozpocznij_dzialania();
        rozpocznij_glosowanie();
        przydziel_mandaty(1);
        wypisz(1);
        przydziel_mandaty(2);
        wypisz(2);
        przydziel_mandaty(3);
        wypisz(3);
    }

    // Wykonuje wszystkie działania zgodnie ze strategiami partii.
    public void rozpocznij_dzialania() {
        // MIN - ilość wyborców w najmniejszym okręgu.
        // MIN2 - najmniejszą suma wartości bezwzględnych wektora zmian.
        int MIN = Integer.MAX_VALUE;
        int MIN2 = Integer.MAX_VALUE;

        for (int i = 0; i < liczba_okregow; i++) {
            if (ilosc_wyborcow[i] < MIN && ilosc_wyborcow[i] != 0) {
                MIN = ilosc_wyborcow[i];
            }
        }

        for (int i = 0; i < suma.length; i++) {
            if (suma[i] < MIN2) {
                MIN2 = MIN;
            }
        }

        // Iteruje po wszystkich partiach.
        for (int i = 0; i < liczba_partii; i++) {
            // Dopóki budżęt na to pozwala wykonuje wszystkie działania danej partii.
            while (partie[i].getBudzet() >= MIN * MIN2) {
                Para para = partie[i].wykonaj_strategie(kandydaci, wektor, suma,
                            ilosc_wyborcow, partie[i].getBudzet());
               
                if (!para.empty()) {
                    // Zmieniamy wagi wyborców w danym okręgu.
                    okregi[para.getFirst()].zmien_wagi(wektor[para.getSecond()]);
                    // Płacimy za wykonaną strategię.
                    partie[i].wydaj(ilosc_wyborcow[para.getFirst()] *
                                    suma[para.getSecond()]);
                }
            }
        }
    }

    // Funkcja odpowiedzialna za głosowanie wyborców.
    public void rozpocznij_glosowanie() {
        for (int i = 0; i < liczba_okregow; i++) {
            // Pobieramy dane wyborców.
            Wyborca[] wyborcy = okregi[i].getWyborcy();

            // Sprawdzamy czy czasem okręg nie został już rozpatrzony(połączony okręg).
            if (wyborcy.length > 0) {
                for (int j = 0; j < wyborcy.length; j++) {
                    // Kandydat, na którego zagłosował dany wyborca.
                    Kandydat kandydat;

                    if (wyborcy[j].isJedna_partia()) {
                        kandydat = wyborcy[j].glosuj(partie[
                                   wyborcy[j].getNr_partii()].getKandydaci(i));
                    }
                    else {
                        kandydat = wyborcy[i].glosuj(kandydaci[i]);
                    }

                    // Zapisujemy oddany głos.
                    okregi[i].oddaj_glos(j, kandydat.getNr_kandydata());
                }
            }
        }
    }

    // Funkcja odpowdzialna za przydzielanie mandatów zgodnie z daną metodą.
    public void przydziel_mandaty(int typ_metody) {
        Metoda metoda;
        if (typ_metody == 1)
            metoda = new DHondta();
        else if (typ_metody == 2)
            metoda = new SainteLague();
        else
            metoda = new HareaNiemeyera();

        for (int i = 0; i < liczba_okregow; i++) {
            if (okregi[i].getLiczba_wyborcow() > 0) {
                // Pobieranie tablicy zawierającej rozmieszczenie przyznanych
                // mandatów.
                int[] mandaty = metoda.rozdaj_mandaty(okregi[i], liczba_partii);

                // Dodawanie mandatów partiom.
                for (int j = 0; j < liczba_partii; j++) {
                    partie[j].dodaj_mandaty(i, mandaty[j]);
                }
                
                // Zapisywanie ilości przyznanych mandatów w kążdym z okręgów.
                okregi[i].set_mandaty(mandaty);
            }
        }
    }

    // Wypisujemy wszystkie dane na wyjście
    public void wypisz(int typ_metody) {
        if (typ_metody == 1)
            System.out.println("Metoda D'Hondta");
        else if (typ_metody == 2)
            System.out.println("Metoda SainteLaque");
        else if (typ_metody == 3)
            System.out.println("Metoda Hare'a-Niemeyera");
        
        for (int i = 0; i < liczba_okregow; i++) {
            // Wypisywanie wszystkich danych dla konkretnego okręgu.
            if (ilosc_wyborcow[i] > 0) {
                System.out.println("Nr. okregu: " + (i + 1));
                Wyborca[] wyborcy = okregi[i].getWyborcy();

                // Wypisywanie wyboróc i ich kandydatów.
                for (int j = 0; j < ilosc_wyborcow[i]; j++) {
                    System.out.println(wyborcy[j] + " " +
                            kandydaci[i][okregi[i].getGlos_osoba(j)]);
                }
                
                // Wypisywanie kandydatów wraz z liczbą uzyskanych głosów.
                Kandydat[] kandydaci = okregi[i].getKandydaci();
                for (int j = 0; j < kandydaci.length; j++) {
                    System.out.println(kandydaci[j] + " " +
                               kandydaci[j].getNazwa_partii() + " " +
                               (kandydaci[j].getPozycja_na_liscie() + 1) +
                               " " + okregi[i].getGlosy_kandydat(j));
                }
                // Wypisywanie rozdanych mandatów w danym okręgu.
                for (int j = 0; j < liczba_partii; j++) {
                    System.out.print("(" + partie[j].getNazwa() + "," +
                                     okregi[i].getMandaty(j) + ") ");
                }
                System.out.println();
            }
        }
        // Wypisywanie Podsumowania rozdanych mandatów dla partii.
        for (int i = 0; i < liczba_partii; i++) {
            System.out.print("(" + partie[i].getNazwa() + "," +
                             partie[i].getSuma_mandatow() + ") ");
        }
        System.out.print("\n");
        // Zerowanie liczby mandatów przyznanych partiom.
        for (int i = 0; i < liczba_partii; i++)
            partie[i].zeruj_mandaty();
        // Zerowanie liczby mandatów przyznanych w danym okregu.
        for (int i = 0; i < liczba_okregow; i++)
            okregi[i].zeruj_mandaty();
    }

}