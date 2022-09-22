/* Mróz Kamil

Zastosowanie algorytmów grafowych do generacji labiryntów.
Wykorzystany przeze mnie algorytm z nawrotami to lekko zmodyfikowany algorytm DFS.

 */


import java.util.*;

public class Labirynt {
    private final int x;             //po szerokości labiryntu
    private final int y;              //po wysokości labiryntu
    private final int[][] labirynt;     //Tablica dwuwymiarowa labiryntu

    //Kierunki
    private final Kierunek gora;
    private final Kierunek dol;
    private final Kierunek prawo;
    private final Kierunek lewo;


    //Konstruktor labiryntu wartośc
    public Labirynt(int szer, int wys, int xtart, int ytart) {
        this.x = szer;       //Szerokość labiryntu
        this.y = wys;         //Wysokość labiryntu
        labirynt = new int[this.x][this.y];        //Tablica dwuwymiarowa o "wielkości" labiryntu

        //kierunki
        gora = new Kierunek(-1, 0, 1);      // Idąc do góry zmniejszamy index
        dol = new Kierunek(1, 0, 2);        // Idąc w dól zwiększamy index
        prawo = new Kierunek(0, 1, 4);      // Idąc w prawo zwiększamy index
        lewo = new Kierunek(0, -1, 8);      // Idąc w lewo zmniejszamy index

        generuj(xtart, ytart);  //Generowanie labiryntu w podanych punktach startowych
    }

    //Klasa definująca obrany kierunek generowania labiryntu
    private static class Kierunek{
        int gora;         //Góra, ruch pionowy
        int prawo;        //Prawo, ruch poziomy
        int wartosc;      //Wartość będąca potęgą liczby 2, żeby można było jednoznacznie określić gdzie idziemy

        //Konstruktor definująca obrany kierunek generowania labiryntu
        public Kierunek(int gora, int prawo, int wartosc) {
            this.gora = gora;             //Góra, ruch pionowy
            this.prawo = prawo;           //Prawo, ruch poziomy
            this.wartosc = wartosc;       //Wartość będąca potęgą liczby 2, żeby można było jednoznacznie określić gdzie idziemy
        }
    }

    //Generujemy rekurencyjnie labirynt
    private void generuj(int obecnyX, int obecnyY) {
        Kierunek[] kierunki = {gora,dol,prawo,lewo};
        Collections.shuffle(Arrays.asList(kierunki));   //Przetasowujemy kierunki dla losowej kolejności

        for (Kierunek kierunek : kierunki) {            // "for each"  z kierunkami generowania
            int nowyX = obecnyX + kierunek.prawo;       // Tworzymy nowo obrany kierunek po szerokości
            int nowyY = obecnyY + kierunek.gora;        // Tworzymy nowo obrany kierunek po długości
            if (kontrolka(nowyX, x) && kontrolka(nowyY, y) && (labirynt[nowyX][nowyY] == 0)) {      //Sprawdzamy czy nowy punkt mieści się w rozmiarze i czy nie był już odwiedzony
                labirynt[obecnyX][obecnyY] += kierunek.wartosc; //Punkt w labiryncie dodaje wartość w którą się poruszy
                if ( kierunek == gora ) {
                    labirynt[nowyX][nowyY] += dol.wartosc;      //Idąc w górę, nowy punkt może iść w dół
                }
                if ( kierunek == dol ) {
                    labirynt[nowyX][nowyY] += gora.wartosc;     //Idąc w dół, nowy punkt może iść w górę
                }
                if ( kierunek == lewo ) {
                    labirynt[nowyX][nowyY] += prawo.wartosc;    //Idąc w lewo, nowy punkt możem iść w prawo
                }
                if ( kierunek == prawo ) {
                    labirynt[nowyX][nowyY] += lewo.wartosc;     //Idąc w prawo, nowy punkt możem iść w lewo
                }
                generuj(nowyX, nowyY);  //Wywołujemy ponownie dla kolejnego punktu
            }
        }
    }

    //Sprawdzamy czy nowy kierunek nie wychodzi poza zadany rozmiar labiryntu
    private static boolean kontrolka(int xy, int max) {
        return (xy >= 0) && (xy < max);
    }

    /*

    //Wyświetla tablicę wylosowanych kierunków poruszania się
    public void tablica() {
        for (int i = 0; i < y; i ++) {
            for (int j = 0; j < x; j++) {
                System.out.print( labirynt[j][i] + " ");
            }
            System.out.println();
        }
    }

     */

    //Rysowanie w konsoli
    public void rysuj() {

        for (int i = 0; i < y; i++) {

            // Górna ściana labiryntu
            for (int j = 0; j < x; j++) {
                if ( labirynt[j][i]%2 != 1 ) {      //Jeśli nie mozna isc w gore, rysujemy gorna sciane
                    System.out.print("▒═══");
                } else {  System.out.print("▒   "); }
            }
            System.out.println("▒");

            // Lewa ściana labiryntu
            for (int j = 0; j < x; j++) {
                if ( labirynt[j][i] < 8 ) {     //Jeśli nie można iść na lewo, rysujemy ścianę
                    System.out.print("║   ");
                } else {  System.out.print("    "); }
            }
            System.out.println("║");
        }

        // Dolna ściana labiryntu
        for (int i = 0; i < x; i++) {
            System.out.print("▒═══");
        }
        System.out.println("▒");
    }

    //Main
    public static void main(String[] args) {

        //rozmiar labiryntu
        int szer = 0;
        int wys = 0;

        //początek labirytu
        int xtart = 0;
        int ytart = 0;

        //bez podanego rozmiaru
        if ( args.length == 0 ) {
            szer = (int) ((Math.random() * (50 - 0)) + 0);          //max - min + min
            wys = (int) ((Math.random() * (50 - 0)) + 0);           //max - min + min

            System.out.print("\nWygenerowano labirynt z losowym rozmiarem " + szer + "x" + wys + " i domyślnym początkiem w punkcie (0,0) \nAby wygenerować labirynt o innych wartościach, podaj następujące argumenty: ");
            System.out.print("\n    'szerokość labiryntu' 'wysokość labirynut'  'punkt startowy x'   'punkt startowy y' \nPamiętaj! Wszystkie wartości muszą być większe od 0, a punkty startowe muszą być conajmniej jeden mniejsze niż wymiary labiryntu! \n\n");

        } else if ( args.length == 1) {         //kwadratowy
            szer = Math.abs(Integer.parseInt(args[0]));
            wys = Math.abs(Integer.parseInt(args[0]));

            System.out.print("Wygenerowano labirynt o wielkości " + szer +"x" + wys + " i domyślnym początkiem w punkcie (0,0) \n\n");

        } else if ( args.length == 2) {         //prostokątny
            szer = Math.abs(Integer.parseInt(args[0]));
            wys = Math.abs(Integer.parseInt(args[1]));

            System.out.print("Wygenerowano labirynt o wielkości " + szer +"x" + wys + " i domyślnym początkiem w punkcie (0,0) \n\n");

        } else if ( args.length == 3) {         //Ze startem na x x
            szer = Math.abs(Integer.parseInt(args[0]));
            wys = Math.abs(Integer.parseInt(args[1]));
            xtart = Math.abs(Integer.parseInt(args[2]));
            ytart = Math.abs(Integer.parseInt(args[2]));
            if ( xtart >= szer || ytart >= wys) {         //W przypadku nieprawidłowych wartości
                System.out.print("Nieprawidłowa wartość argumentów");
                System.exit(0);         //Wyjdz
            }

            System.out.print("Wygenerowano labirynt o wielkości " + szer +"x" + wys + " w punkcie startowym (" + xtart + "," + ytart + ")\n\n");


        } else if ( args.length == 4) {        //Ze startem na x y
            szer = Math.abs(Integer.parseInt(args[0]));
            wys = Math.abs(Integer.parseInt(args[1]));
            xtart = Math.abs(Integer.parseInt(args[2]));
            ytart = Math.abs(Integer.parseInt(args[3]));
            if ( xtart >= szer || ytart >= wys) {         //W przypadku nieprawidłowych wartości
                System.out.print("Nieprawidłowa wartość argumentów");
                System.out.print("\n Podaj argumenty:   'szerokość labiryntu' 'wysokość labirynut'  'punkt startowy x'   'punkt startowy y' \nPamiętaj! Wszystkie wartości muszą być większe od 0, a punkty startowe muszą być conajmniej jeden mniejsze niż wymiary labiryntu! \n\n");
                System.exit(0);      //Wyjdz
            }

            System.out.print("Wygenerowano labirynt o wielkości " + szer +"x" + wys + " w punkcie startowym (" + xtart + "," + ytart + ")\n\n");

        } else {                                     //zła ilość argumentów
            System.out.print("Nieprawidłowa ilość argumentów");
            System.out.print("\n Podaj argumenty:   'szerokość labiryntu' 'wysokość labirynut'  'punkt startowy x'   'punkt startowy y' \nPamiętaj! Wszystkie wartości muszą być większe od 0, a punkty startowe muszą być conajmniej jeden mniejsze niż wymiary labiryntu! \n\n");
            System.exit(0);              //Wyjdz
        }

        Labirynt labirynt = new Labirynt(szer, wys, xtart, ytart);     //Generuj
        labirynt.rysuj();                         //Wyświetl
        //labirynt.tablica();
    }
}

