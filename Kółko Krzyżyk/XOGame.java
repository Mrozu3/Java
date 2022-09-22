/* Mroz Kamil

    Gra Kolko Krzyzyk z interfejsem graficznym dla 2 graczy z mozliwoscia ustawienia dowolnego wymiaru planszy ( NxN ) oraz dowolnym warunkiem wygranej.
    Aplikacja prosi w okienku graficznym uzytkownika o wprowadzenie rozmiaru planszy i koniecznego warunku wygranej przed rozpoczeciem , nastepnie generuje plansze o podanych rozmiarach.

    W kodzie zostala zaimplementowana mozliwosc zrobienia plaszny NxM, jednak ze wzgledu na warunki wygranej i estetyki, pozostawilem mozliwosc generowania tylko kwadratowej planszy.
 */

//Potrzebne importy
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class GameStart extends JFrame {
    //Zmienne do ustawien
    private JTextField SizeX;       //Tekstowe do ilosci Wierszy
    private JTextField SizeY;       //Tekstowe do ilosci Kolumn
    private JTextField KingSize;    //Tekstowe potrzebne do wygrania
    private int BoardSizeX;         //Int do rozmiaru wierszy
    private int BoardSizeY;         //Int do rozmiaru kolumn
    private int KingSizeWin;        //Int do ilosci potrzebnej do wygranej

    //Zmienne do planszy gry
    private Container ramka;         //Tu wszystko przechowujemy
    private JButton[][] plansza;    //Przyciski ktore sa nasza plansza
    private String gracz;           //Gracz "x" lub "o"
    private boolean wygrany;        //Gracz ktory wygral


    //Pierwsze okno gry z ustawieniami
    public GameStart() {
        getContentPane();                 //Glowna ramka, tu wszystko przechowujemy
        setTitle("Podaj warunki gry w Kolko Krzyzyk");  //Tytul okna
        setResizable(true);                             //Mozliwosc zmiany rozmiaru
        setSize(500, 50);                  //Domyslny rozmiar
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);     //Zamykamy po zamknieciu ramki
        setVisible(true);                               //Widzialny

        Ustawienia();                     //Odpalamy panel "menu" w oknie
    }

    private void Ustawienia() {

        //Okienko ustawien
        JMenuBar settingsBar = new JMenuBar();   //Nowy Panel

        //Pole tekstowe do podania rozmiaru planszy
        SizeX = new JTextField("Rozmiar Planszy");
        SizeX.setForeground(Color.black.brighter());
        SizeX.setBackground(Color.white);

        //Pole tekstowe do podania ile kolumn - nieuzywane obecnie
        SizeY = new JTextField("Liczba kolumn");
        SizeY.setForeground(Color.black.brighter());
        SizeY.setBackground(Color.white);

        //Pole tekstowe do podania  warunku wygranej
        KingSize = new JTextField("Liczba warunku wygranej");
        KingSize.setForeground(Color.black.brighter());
        KingSize.setBackground(Color.white);

        //Przycisk do odpalenia gry
        //Przycisk Play
        JMenuItem play = new JMenuItem("Graj");
        play.setBorder(BorderFactory.createLineBorder(Color.black));
        //Po kliknieciu przycisku
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        BoardSizeX = Integer.parseInt(SizeX.getText());             //Zamieniamy String pobrany z okna tekstowego na Integer
                        //BoardSizeY = Integer.parseInt(SizeY.getText());           //Gdybysmy chcieli NxM odkomentujemy to i przycisk add(SizeY)
                        BoardSizeY = BoardSizeX;                                    //Podmieniamy zmienne by byly sobie rowne
                        KingSizeWin = Integer.parseInt(KingSize.getText());         //Zamieniamy String pobrany z okna tekstowego na Integer
                        //Pare warunkow dla danych by gra miala sens
                        if(BoardSizeX < 0 || BoardSizeY < 0 || KingSizeWin < 0 || BoardSizeX < KingSizeWin || BoardSizeY < KingSizeWin ) {
                            JOptionPane.showMessageDialog(null, "Wpisz poprawne liczby wieksze od 0 \n Wartosc potrzebna do wygrania nie moze byc wieksza od ilosci kolumn i wierszy"); //Wyswietlamy wiadomosc jesli cos jest zle
                        } else {
                            XO(); //Odpalamy okno z gra
                        }
                    }
                });
            }
        });

        settingsBar.add(SizeX);     //Dodajemy pole tekstowe
        //settingsBar.add(SizeY);   //Dodajemy pole tekstowe
        settingsBar.add(KingSize);  //Dodajemy pole tekstowe
        settingsBar.add(play);      //Dodajemy przycsik
        setJMenuBar(settingsBar);   //i gotowe menu do panelu
    }

    //Okno gry
    public void XO() {

        ramka = getContentPane();                                        //Glowna ramka
        ramka.setLayout((new GridLayout(BoardSizeX, BoardSizeY)));       //Sposob wyswietlania
        setTitle("Kolko Krzyzyk");                                      //Tytul ramki/okna
        setResizable(true);                                             //Mozliwosc zmiany rozmiaru
        setSize(500, 500);                                  //Domyslny wyglad
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);                      //Zamykamy po zamknieciu ramki
        setVisible(true);                                                //Widoczne

        gracz = "x";                                                     //Zaczynamy od "x"
        plansza = new JButton[BoardSizeX][BoardSizeY];                   //Generujemy plansze przyciskow
        wygrany = false;                                                 //Nie mam na poczatku wygranego
        Plansza();                                              //Wyswietlamy plansze gry w oknie
        Menu();                                            //Wyswietlamy opcje
    }



    private void Menu() {
        //Panel gry
        JMenuBar menuBar = new JMenuBar();           //Tworzymy pasek menu

        //Przycisk Nowa gra
        //Przycisk nowa gra
        JMenuItem nowaGra = new JMenuItem("Nowa Gra");
        nowaGra.setBorder(BorderFactory.createLineBorder(Color.black));
        //Po kliknieciu przycisku
        nowaGra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Reset();    //resetujemy gre
            }
        });

        //Przycisk opuszczania gry
        //Przycisk wyjdz
        JMenuItem wyjdz = new JMenuItem("Opusc Gre");
        wyjdz.setBorder(BorderFactory.createLineBorder(Color.black));
        //Po kliknieciu przycisku
        wyjdz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0); //Zamykamy gre
            }
        });

        menuBar.add(nowaGra); //dodajemy przycisk nowaGra do panelu
        menuBar.add(wyjdz); //dodajemy przycisk wyjdz do panelu
        setJMenuBar(menuBar); //dodajemy panel do ramki/okna/panelu glownego
    }

    //Reset gry
    private void Reset() {
        //Przywracamy wszystko do ustawien fabrycznych
        gracz = "x";
        wygrany = false;
        //Wypelniamy cala tablice pustymi polami przyciskow
        for (int i = 0; i < BoardSizeX; i++) {
            for (int j = 0; j < BoardSizeY; j++) {
                plansza[i][j].setText("");
            }
        }
    }

    //Generujemy plansze
    private void Plansza() {
        //Generujemy plansze wg. podanych kryteriow
        for (int i = 0; i < BoardSizeX; i++) {
            for (int j = 0; j < BoardSizeY; j++) {
                JButton pole = new JButton();            //Wypelniajac ja przyciskami
                pole.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 10));   //O czcionce, rozmiarze
                plansza[i][j] = pole; //kazdy przycisk to pole
                //Po kliknieciu w pole
                pole.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        //Jesli jest pusta i nie ma wygranego
                        if (((JButton) actionEvent.getSource()).getText().equals("") && !wygrany) {
                            //Dostaje wartosc "x" lub "o" w zaleznosci kogo tura
                            pole.setText(gracz);
                            //Sprawdza czy jest wygrany
                            Wygrana();
                            //Zmienia gracza na drugiego
                            DrugiGracz();
                        }
                    }
                });
                //Dodaj nowe pole do ramki
                ramka.add(pole);

            }
        }

    }

    //Zmienia gracza
    private void DrugiGracz() {
        //Jesli "o" to zmieniamy na "x", pozwala wymieniac gracza
        if (gracz.equals("o")) {
            gracz = "x";
        } else {            //inaczej na "o"
            gracz = "o";
        }

    }

    //Kiedy padnie wygrana
    private void Wygrana() {

        //Zmienne pomocnicze
        int counterX = 1;
        int counterO = 1;

        //Szukamy wygranej poziomo
        for (int i = 0; i < BoardSizeX; i++) { // Wiersze
            for (int j = 0; j < BoardSizeY-1; j++) { // Kolumny
                if (plansza[i][j].getText().equals("x") && plansza[i][j+1].getText().equals(("x"))) {  //Jesli jest rowna x
                    counterX++;                             //Zwiekszamy licznik x do wygranej
                } else {
                    counterX = 1; }                    //Jesli nie jest pod rzad to resetujemy licznik dla x
                if (plansza[i][j].getText().equals("o") && plansza[i][j+1].getText().equals(("o"))) {  //Jesli jest rowna o
                    counterO++;                             //Zwiekszamy licznik o do wygranej
                } else {
                    counterO = 1; }                    //Jesli nie jest pod rzad to resetujemy licznik dla o
                if (counterX == KingSizeWin && !wygrany) { // Jesli osiagniemy podana wartosc oznaczajaca wygrana i nie padla juz wygrana ( zabezpieczenie przed podwojnym okienkiem )
                    JOptionPane.showMessageDialog(null, "Gracz X wygral");  //Wyswietlamy wiadomosc kto wygral
                    wygrany = true;
                }
                if (counterO == KingSizeWin && !wygrany) { // Jesli osiagniemy podana wartosc oznaczajaca wygrana i nie padla juz wygrana ( zabezpieczenie przed podwojnym okienkiem )
                    JOptionPane.showMessageDialog(null, "Gracz O wygral");  //Wyswietlamy wiadomosc kto wygral
                    wygrany = true;
                }
            }
        }

        //Szukamy wygranej pionowo
        for (int j = 0; j < BoardSizeX; j++) { // Wiersze
            for (int i = 0; i < BoardSizeY-1; i++) { // Kolumny
                if (plansza[i][j].getText().equals("x") && plansza[i+1][j].getText().equals("x")) {  //Jesli jest rowna x
                    counterX++;                             //Zwiekszamy licznik x do wygranej
                } else {
                    counterX = 1; }                    //Jesli nie jest pod rzad to resetujemy licznik dla x
                if (plansza[i][j].getText().equals("o") && plansza[i+1][j].getText().equals("o")) {  //Jesli jest rowna o
                    counterO++;                             //Zwiekszamy licznik o do wygranej
                } else {
                    counterO = 1;
                }                    //Jesli nie jest pod rzad to resetujemy licznik dla o
                if (counterX == KingSizeWin && !wygrany) { // Jesli osiagniemy podana wartosc oznaczajaca wygrana i nie padla juz wygrana ( zabezpieczenie przed podwojnym okienkiem )
                    JOptionPane.showMessageDialog(null, "Gracz X wygral");  //Wyswietlamy wiadomosc kto wygral
                    wygrany = true;
                }
                if (counterO == KingSizeWin && !wygrany) { // Jesli osiagniemy podana wartosc oznaczajaca wygrana i nie padla juz wygrana ( zabezpieczenie przed podwojnym okienkiem )
                    JOptionPane.showMessageDialog(null, "Gracz O wygral");  //Wyswietlamy wiadomosc kto wygral
                    wygrany = true;
                }
            }
        }

        //Diagonale

        //Diagonale gorne z dolu do gory
        for(int i = BoardSizeX; i > 0; i--) {
            for(int j = 0, x = i; x <= BoardSizeY-2; j++, x++) {
                if (plansza[x][j].getText().equals("x") && plansza[x+1][j+1].getText().equals("x")){  //Jesli jest rowna x
                    counterX++;                             //Zwiekszamy licznik x do wygranej
                } else {
                    counterX = 1; }                    //Jesli nie jest pod rzad to resetujemy licznik dla x
                if (plansza[x][j].getText().equals("o") && plansza[x+1][j+1].getText().equals("o")) {  //Jesli jest rowna o
                    counterO++;                             //Zwiekszamy licznik o do wygranej
                } else {
                    counterO = 1; }                    //Jesli nie jest pod rzad to resetujemy licznik dla o
                if (counterX == KingSizeWin && !wygrany) { // Jesli osiagniemy podana wartosc oznaczajaca wygrana i nie padla juz wygrana ( zabezpieczenie przed podwojnym okienkiem )
                    JOptionPane.showMessageDialog(null, "Gracz X wygral");  //Wyswietlamy wiadomosc kto wygral
                    wygrany = true;
                }
                if (counterO == KingSizeWin && !wygrany) { // Jesli osiagniemy podana wartosc oznaczajaca wygrana i nie padla juz wygrana ( zabezpieczenie przed podwojnym okienkiem )
                    JOptionPane.showMessageDialog(null, "Gracz O wygral");  //Wyswietlamy wiadomosc kto wygral
                    wygrany = true;
                }
            }
        }


        //Diagonale gorne z gory do dolu
        for(int i = 0; i <= BoardSizeX; i++) {
            for(int j = 0 , y = i; y <= BoardSizeY-2; j++, y++) {
                if (plansza[j][y].getText().equals("x") && plansza[j+1][y+1].getText().equals("x")){  //Jesli jest rowna x
                    counterX++;                             //Zwiekszamy licznik x do wygranej
                } else {
                    counterX = 1; }                    //Jesli nie jest pod rzad to resetujemy licznik dla x
                if (plansza[j][y].getText().equals("o") && plansza[j+1][y+1].getText().equals("o")) {  //Jesli jest rowna o
                    counterO++;                             //Zwiekszamy licznik o do wygranej
                } else {
                    counterO = 1; }                    //Jesli nie jest pod rzad to resetujemy licznik dla o
                if (counterX == KingSizeWin && !wygrany) { // Jesli osiagniemy podana wartosc oznaczajaca wygrana i nie padla juz wygrana ( zabezpieczenie przed podwojnym okienkiem )
                    JOptionPane.showMessageDialog(null, "Gracz X wygral");  //Wyswietlamy wiadomosc kto wygral
                    wygrany = true;
                }
                if (counterO == KingSizeWin && !wygrany) { // Jesli osiagniemy podana wartosc oznaczajaca wygrana i nie padla juz wygrana ( zabezpieczenie przed podwojnym okienkiem )
                    JOptionPane.showMessageDialog(null, "Gracz O wygral");  //Wyswietlamy wiadomosc kto wygral
                    wygrany = true;
                }
            }
        }

        //Diagonale gorne z dolu do gory
        for(int i = 0; i < BoardSizeX; i++) {
            for(int j = 0 ,x = i; x > 0 && j < BoardSizeY-1; j++, x--) {
                if (plansza[j][x].getText().equals("x") && plansza[j+1][x-1].getText().equals("x")){  //Jesli jest rowna x
                    counterX++;                             //Zwiekszamy licznik x do wygranej
                } else {
                    counterX = 1; }                    //Jesli nie jest pod rzad to resetujemy licznik dla x
                if (plansza[j][x].getText().equals("o") && plansza[j+1][x-1].getText().equals("o")) {  //Jesli jest rowna o
                    counterO++;                             //Zwiekszamy licznik o do wygranej
                } else {
                    counterO = 1; }                    //Jesli nie jest pod rzad to resetujemy licznik dla o
                if (counterX == KingSizeWin && !wygrany) { // Jesli osiagniemy podana wartosc oznaczajaca wygrana i nie padla juz wygrana ( zabezpieczenie przed podwojnym okienkiem )
                    JOptionPane.showMessageDialog(null, "Gracz X wygral");  //Wyswietlamy wiadomosc kto wygral
                    wygrany = true;
                }
                if (counterO == KingSizeWin && !wygrany) { // Jesli osiagniemy podana wartosc oznaczajaca wygrana i nie padla juz wygrana ( zabezpieczenie przed podwojnym okienkiem )
                    JOptionPane.showMessageDialog(null, "Gracz O wygral");  //Wyswietlamy wiadomosc kto wygral
                    wygrany = true;
                }
            }
        }

        //Diagonale dolne z dolu do gory
        for(int i = BoardSizeX; i >= 0; i--) {
            for(int j = BoardSizeY-1, y = i; j > 0 && y < BoardSizeY-1; j--, y++) {
                if (plansza[y][j].getText().equals("x") && plansza[y+1][j-1].getText().equals("x")){  //Jesli jest rowna x
                    counterX++;                             //Zwiekszamy licznik x do wygranej
                } else {
                    counterX = 1; }                    //Jesli nie jest pod rzad to resetujemy licznik dla x
                if (plansza[y][j].getText().equals("o") && plansza[y+1][j-1].getText().equals("o")) {  //Jesli jest rowna o
                    counterO++;                             //Zwiekszamy licznik o do wygranej
                } else {
                    counterO = 1; }                    //Jesli nie jest pod rzad to resetujemy licznik dla o
                if (counterX == KingSizeWin && !wygrany) { // Jesli osiagniemy podana wartosc oznaczajaca wygrana i nie padla juz wygrana ( zabezpieczenie przed podwojnym okienkiem )
                    JOptionPane.showMessageDialog(null, "Gracz X wygral");  //Wyswietlamy wiadomosc kto wygral
                    wygrany = true;
                }
                if (counterO == KingSizeWin && !wygrany ) { // Jesli osiagniemy podana wartosc oznaczajaca wygrana i nie padla juz wygrana ( zabezpieczenie przed podwojnym okienkiem )
                    JOptionPane.showMessageDialog(null, "Gracz O wygral");  //Wyswietlamy wiadomosc kto wygral
                    wygrany = true;
                }
            }
        }
    }
}

//Glowna klasa
public class XOGame {
    //Main
    public static void main(String [] args) {
        //Odpalamy klase gry
        SwingUtilities.invokeLater(GameStart::new);
    }
}