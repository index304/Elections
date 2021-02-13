package wybory;

import java.util.Random;

// Klasa zawierająca Pary postaci (int, int) lub pary postaci (double, int).
public class Para implements Comparable<Para> {
    private int first;
    private int second;
    private double value;
    private int losowa;

    // Konstruktor wykorzystywany głównie do tworzenia par postaci
    // (nr_okręgu, nr_wektora).
    public Para(int first, int second) {
        this.first = first;
        this.second = second;
    }

    // Ten konstruktor wykorzystywany jest sortowania danych w metodach.
    public Para(double value, int second) {
        this.second = second;
        this.value = value;

        Random random = new Random();
        losowa = random.nextInt();
    }

    public int getFirst() {
        return this.first;
    }

    public int getSecond() {
        return this.second;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public double getValue() {
        return this.value;
    }

    public int getLosowa() {
        return losowa;
    }

    // Przyjmuję, że zwraca true dla wszystkich par postaci (-1, -1), co ma
    // oznaczać niezdefiniowaną parę.
    boolean empty() {
        return (this.first == -1 && this.second == -1);
    }

    // Losowanie par dla metod, najpierw odbywa się względem pierwszej
    // współrzędnej, a następnie względem wylosowanej liczby.
    public int compareTo(Para para) {
        if (this.value > para.getValue()) {
            return -1;
        }
        else if (this.value == para.getValue()) {
            if (this.losowa == para.getLosowa()) {
                if (this.second > para.getSecond())
                    return 1;
                else
                    return -1;
            }
            else if (this.losowa > para.getLosowa()) {
                return 1;
            }
            else {
                return -1;
            }
        }
        else {
            return 1;
        }
    }
}
