package wybory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Wczytywanie {
    private int liczba_okregow;
    private int liczba_partii;
    private int liczba_dzialan;
    private int liczba_cech;

    private int liczba_scalonych_okregow;

    private String[] nazwy_partii;
    private String[] typ_partii;
    private int[] budzet;
    private int[] liczba_wyborcow;
    private int[] suma_wyborcow;

    private Kandydat[][] kandydaci;
    private Kandydat[][][] kandydaci_partie;
    private Wyborca[][] wyborcy;
    private boolean[] scalony;
    private boolean[] glowny;

    // Tablica przechowywująca wektory zmian.
    private int[][] wektory;

    // Mapa par (nazwa_partii, nr_partii).
    HashMap<String, Integer> partie;
    Scanner sc;

    public Wczytywanie(String nazwa_pliku) {
        try {
            File obj = new File(nazwa_pliku);
            sc = new Scanner(obj);
            wczytaj();
        }
        catch (FileNotFoundException e) {
            System.out.println("Nie znaleziono pliku.");
        }
    }

    // Scalanie konkretnych okręgów.
    private void scalanie(String wyraz) {
        int koniec = 0;
        while(wyraz.charAt(koniec + 1) != ',') {
            koniec++;
        }

        int nr = Integer.parseInt(wyraz.substring(1, koniec + 1));
        scalony[nr] = true;
        glowny[nr - 1] = true;
    }

    // Wczytywanie początkowych wartości.
    private void wczytaj_poczatek() {
        liczba_okregow = sc.nextInt();
        liczba_partii = sc.nextInt();
        liczba_dzialan = sc.nextInt();
        liczba_cech = sc.nextInt();
    
        liczba_scalonych_okregow = sc.nextInt();
    
        scalony = new boolean[liczba_okregow];
        glowny = new boolean[liczba_okregow];
        for (int i = 0; i < liczba_okregow; i++) {
            scalony[i] = false;
            glowny[i] = false;
        }
    
        for (int i = 0; i < liczba_scalonych_okregow; i++) {
            scalanie(sc.next());
        }
    
        nazwy_partii = new String[liczba_partii];
        partie = new HashMap<String, Integer>();
        for (int i = 0; i < liczba_partii; i++) {
            nazwy_partii[i] = sc.next();
            partie.put(nazwy_partii[i], i);
        }
    
        budzet = new int[liczba_partii];
        for(int i = 0; i < liczba_partii; i++) {
            budzet[i] = sc.nextInt();
        }
    
        typ_partii = new String[liczba_partii];
        for (int i = 0; i < liczba_partii; i++) {
            typ_partii[i] = sc.next();
        }
    }
    
    // Wczytywanie danych potrzebnych do inicjalizacji tablic wyborców i
    // tablic kandydatów.
    private void wczytaj_tablice() {
        liczba_wyborcow = new int[liczba_okregow];
        suma_wyborcow = new int[liczba_okregow];
        for (int i = 0; i < liczba_okregow; i++) {
            liczba_wyborcow[i] = sc.nextInt();
            suma_wyborcow[i] = liczba_wyborcow[i];
        
            if (scalony[i]) {
                suma_wyborcow[i - 1] += liczba_wyborcow[i];
                suma_wyborcow[i] = 0;
            }
        }
    
    
        kandydaci = new Kandydat[liczba_okregow][];
        kandydaci_partie = new Kandydat[liczba_partii][liczba_okregow][];
        for (int i = 0; i < liczba_okregow; i++) {
            kandydaci[i] = new Kandydat[(suma_wyborcow[i] / 10) * liczba_partii];
        }
    
        for (int i = 0; i < liczba_partii; i++) {
            for (int j = 0; j < liczba_okregow; j++) {
                kandydaci_partie[i][j] = new Kandydat[suma_wyborcow[j] / 10];
            }
        }
    }
    
    private void wczytaj_kandydata(String imie, String nazwisko, int nr_okregu,
                                   String nazwa_partii, int pozycja_na_liscie,
                                   int[] cechy, int licznik, int i, int j,
                                   int k) {
        
        if (scalony[nr_okregu - 1] == true) {
            // Dodaję nowego kandydata.
            Kandydat nowy = new Kandydat(imie, nazwisko, nr_okregu - 2,
                                         nazwa_partii,
                                         (Integer)partie.get(nazwa_partii),        // Nr. partii
                                         pozycja_na_liscie - 1 +                   // Pozycja na liście scalonego okręgu.
                                             liczba_wyborcow[nr_okregu - 2] / 10,
                                         cechy,
                                         (liczba_wyborcow[nr_okregu - 2] / 10) *   // Nr. wyborcy w okręgu.
                                             liczba_partii + licznik);
            
            kandydaci[nr_okregu - 2][(liczba_wyborcow[nr_okregu - 2] / 10) *       // Dodawanie kandydata do okręgu.
                                     liczba_partii + licznik] = nowy;
            kandydaci_partie[j][nr_okregu - 2]
                               [(liczba_wyborcow[nr_okregu - 2] / 10) + k] = nowy; // Dodawanie kandydata do partii.
        }
        else {
            Kandydat nowy = new Kandydat(imie, nazwisko, nr_okregu - 1,
                                         nazwa_partii,
                                         (Integer)partie.get(nazwa_partii),
                                         pozycja_na_liscie - 1, cechy, licznik);
            kandydaci[nr_okregu - 1][licznik] = nowy;                              // Dodawanie kandydata do okręgu.
            kandydaci_partie[j][nr_okregu - 1][k] = nowy;                          // Dodawanie kandydta do partii.
        }
    }
    
    // Wczytywanie kandydatów.
    private void wczytaj_kandydatow() {
        for (int i = 0; i < liczba_okregow; i++) {
            int licznik = 0;
            for (int j = 0; j < liczba_partii; j++) {
                for (int k = 0; k < liczba_wyborcow[i] / 10; k++) {
                    String imie, nazwisko, nazwa_partii;
                    int nr_okregu, pozycja_na_liscie;
                    int[] cechy = new int[liczba_cech];
                    imie = sc.next();
                    nazwisko = sc.next();
                    nr_okregu = sc.nextInt();
                    nazwa_partii = sc.next();
                    pozycja_na_liscie = sc.nextInt();
                
                    for (int s = 0; s < liczba_cech; s++) {
                        cechy[s] = sc.nextInt();
                    }
                
                    wczytaj_kandydata(imie, nazwisko, nr_okregu, nazwa_partii,
                                      pozycja_na_liscie, cechy, licznik, i, j, k);
                    licznik++;
                }
            }
        }
    }
    
    // Funkcja pomocnicza wczytująca konkretnego wyborcę. Ze względu na
    // dość schematyczną budowę nie została podzielona na mniejsze kawałki.
    private void wczytaj_wyborce(int i, int j, String imie, String nazwisko,
                                 int nr_okregu, int typ_wyborcy, int dodaj) {
        Wyborca nowy = null;
        String nazwa_partii;
        int nr_kandydata, nr_cechy;
        
        if (typ_wyborcy == 1) { // Elektorat Partyjny.
            nazwa_partii = sc.next();
            nowy = new ElektoratPartyjny(imie, nazwisko, nr_okregu - 1, typ_wyborcy,
                true, nazwa_partii, (Integer)partie.get(nazwa_partii));
        }
        else if (typ_wyborcy == 2) { // Elektorat Kandydata.
            nazwa_partii = sc.next();
            nr_kandydata = sc.nextInt();
            nr_kandydata = dodaj + nr_kandydata - 1;
            nowy = new ElektoratKandydata(imie, nazwisko, nr_okregu - 1, typ_wyborcy,
                true, nazwa_partii, partie.get(nazwa_partii), nr_kandydata);
        }
        else if (typ_wyborcy == 3) { // Minimalizujący.
            nr_cechy = sc.nextInt();
            nowy = new Minimalizujacy(imie, nazwisko, nr_okregu - 1, typ_wyborcy,
                false, null, -1, nr_cechy);
        }
        else if (typ_wyborcy == 4) { // Maksymalizujący.
            nr_cechy = sc.nextInt();
            nowy = new Maksymalizujacy(imie, nazwisko, nr_okregu - 1, typ_wyborcy,
                false, null, -1, nr_cechy);
        }
        else if (typ_wyborcy == 5) { // Wszechstronny.
            int[] wagi = new int[liczba_cech];
        
            for (int k = 0; k < liczba_cech; k++) {
                wagi[k] = sc.nextInt();
            }
        
            nowy = new Wszeschstronni(imie, nazwisko, nr_okregu - 1, typ_wyborcy,
                false, wagi, null, -1);
        }
        else if (typ_wyborcy == 6) { // Minimalizujacy z danej partii.
            nr_cechy = sc.nextInt();
            nazwa_partii = sc.next();
        
            nowy = new Minimalizujacy(imie, nazwisko, nr_okregu - 1, typ_wyborcy,
                true, nazwa_partii, (Integer)partie.get(nazwa_partii), nr_cechy);
        }
        else if (typ_wyborcy == 7) { // Maksymalizujący z danej partii.
            nr_cechy = sc.nextInt();
            nazwa_partii = sc.next();
        
            nowy = new Maksymalizujacy(imie, nazwisko, nr_okregu - 1, typ_wyborcy,
                true, nazwa_partii, (Integer)partie.get(nazwa_partii), nr_cechy);
        }
        else { // Wszechstronny z danej partii.
            int[] wagi = new int[liczba_cech];
            for (int k = 0; k < liczba_cech; k++) {
                wagi[k] = sc.nextInt();
            }
        
            nazwa_partii = sc.next();
        
            nowy = new Wszeschstronni(imie, nazwisko, nr_okregu - 1, typ_wyborcy,
                true, wagi, nazwa_partii, (Integer)partie.get(nazwa_partii));
        }
        if (scalony[i]) {
            wyborcy[i - 1][liczba_wyborcow[i - 1] + j] = nowy;
        }
        else {
            wyborcy[i][j] = nowy;
        }
    }
    
    // Funkcja wczytująca wyborców.
    private void wczytaj_wyborcow() {
        wyborcy = new Wyborca[liczba_okregow][];
        for (int i = 0; i < liczba_okregow; i++) {
            wyborcy[i] = new Wyborca[suma_wyborcow[i]];
        }
    
        for (int i = 0; i < liczba_okregow; i++) {
            for (int j = 0; j < liczba_wyborcow[i]; j++) {
                String imie, nazwisko;
                int nr_okregu, typ_wyborcy, dodaj = 0;
            
                imie = sc.next();
                nazwisko = sc.next();
                nr_okregu = sc.nextInt();
                typ_wyborcy = sc.nextInt();
                
                if (scalony[i] == true) {
                    nr_okregu--;
                    dodaj = (liczba_wyborcow[nr_okregu - 1] / 10);
                }
                wczytaj_wyborce(i, j, imie, nazwisko, nr_okregu, typ_wyborcy,
                                dodaj);
            }
        }
    }
    
    // Wczytywanie danych z pliku.
    private void wczytaj() {
        
        wczytaj_poczatek();
        wczytaj_tablice();
        wczytaj_kandydatow();
        wczytaj_wyborcow();
        
        wektory = new int[liczba_dzialan][liczba_cech];
        for (int i = 0; i < liczba_dzialan; i++) {
            for (int j = 0; j < liczba_cech; j++) {
                wektory[i][j] = sc.nextInt();
            }
        }

        uruchom_symulacje();
    }
    
    // Uruchamianie symulacji wyborów.
    private void uruchom_symulacje() {
        Okreg[] okregi = new Okreg[liczba_okregow];
        for (int i = 0; i < liczba_okregow; i++) {
            okregi[i] = new Okreg(kandydaci[i], wyborcy[i], suma_wyborcow[i],
                    liczba_partii);
        }

        Partia[] partie = new Partia[liczba_partii];
        for (int i = 0; i < liczba_partii; i++) {
            Strategia strategia;
            if (typ_partii[i].compareTo("R") == 0) {
                strategia = new ZRozmachem();
            }
            else if (typ_partii[i].compareTo("Z") == 0) {
                strategia = new Zachlanna();
            }
            else if (typ_partii[i].compareTo("S") == 0) {
                strategia = new Skromna();
            }
            else {
                strategia = new Wlasna();
            }

            partie[i] = new Partia(nazwy_partii[i], kandydaci_partie[i],
                    strategia, budzet[i]);
        }

        Symulacja symulacja = new Symulacja(okregi, kandydaci, partie,
                liczba_okregow, liczba_partii, wektory, suma_wyborcow);

        symulacja.start();
        sc.close();
    }
}
