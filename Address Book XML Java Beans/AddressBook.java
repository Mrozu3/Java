/* Książka telefoniczna z interfejsem graficznym oraz importowaniem i eksportowaniem z pliku XML i do pliku XML

Program pozwalający dodawać i usuwać kontakty, sam graficzny podgląd do listy kontaktów jest niezależny od JavaBeans.

 */

//Potrzebne Importy
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.beans.*;

//Klasa rozszerzona o JFrame
public class AddressBook extends JFrame{
    static Container okno;         //Okno do przechowania listy
    static JFrame frame;        //Panel Główny
    int Index = 0;              //Indeksy

    public AddressBook() {
        frame = new JFrame("Książka Adresowa"); //Tytuł okna
        frame.setSize(500,700);         //Rozmiar panelu
        frame.setResizable(false);                   //Możliwość rozszerzania
        frame.setLayout(null);                       //Wzorzec
        frame.setVisible(true);                     //Widzialność
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);   //Operacja zamykania

        Controls(); //Menu książki telefonicznej
    }

    private void Controls() {
        @SuppressWarnings("unchecked")
        final ArrayList<Kontakt>[] kontakty = new ArrayList[]{new ArrayList<>()}; //Lista na nasze kontakty w pliku Kontakt.java będący JavaBeans, dostęp z klasy wewnętrznej dlatego musi być final (IntelliJ)

        //Tabela do kontaktów
        JPanel ramkaTabeli = new JPanel();        //Tworzenie tabelki służącej za listy na kontakty
        String[] nazwyKolumn = {"Imię","Nazwisko","Telefon"}; //Nazwy Kolumn

        DefaultTableModel Lista = new DefaultTableModel(nazwyKolumn,0); //Przechowujemy w strukturze z wektorów dynamicznie
        JTable tabelka = new JTable(Lista);   //Tworzymy tabelkę
        JScrollPane scrollPane  = new JScrollPane(tabelka); //Tworzymy suwak do tabelki

        ramkaTabeli.add(scrollPane );                //dodajemy suwak do tabelki
        ramkaTabeli.setLocation(0,0);            //Ustawiamy położenie
        ramkaTabeli.setSize(500,500);    //Ustawiamy Rozmiar

        //Elementy Menu

        //Dodaj
        JLabel edykietadodajKontakt = new JLabel();
        edykietadodajKontakt.setText("Dodaj kontakt");
        edykietadodajKontakt.setLocation(200,20);
        edykietadodajKontakt.setSize(100,25);

        //Imie
        JLabel etykietaImie = new JLabel();
        etykietaImie.setText("Imię: ");
        etykietaImie.setLocation(93,50);
        etykietaImie.setSize(100,25);

        JTextField Imie = new JTextField();
        Imie.setLocation(150,50);
        Imie.setSize(200,25);

        //Nazwisko
        JLabel etykietaNazwisko = new JLabel();
        etykietaNazwisko.setText("Nazwisko: ");
        etykietaNazwisko.setLocation(60,100);
        etykietaNazwisko.setSize(100,25);

        JTextField Nazwisko = new JTextField();
        Nazwisko.setLocation(150,100);
        Nazwisko.setSize(200,25);

        //Telefon
        JLabel etykietaTelefon = new JLabel();
        etykietaTelefon.setText("Telefon: ");
        etykietaTelefon.setLocation(72,150);
        etykietaTelefon.setSize(100,25);

        JTextField Telefon = new JTextField();
        Telefon.setLocation(150,150);
        Telefon.setSize(200,25);

        //Działanie przycisku Dodaj
        JButton add = new JButton("Dodaj");
        add.setLocation(150,200);
        add.setSize(200,50);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(Imie.getText().equals("") || Nazwisko.getText().equals("") || Telefon.getText().equals("")) {        //Jeśli któreś okno jest puste
                    JOptionPane.showMessageDialog(null, "Wszystkie dane muszą być uzupełnione");        //Daj odpowiedni komunikat
                }
                else {
                    Object[] nowykontakt = new Object[3];       //Przechowujemy obiekt zawierający 3 elementy
                    nowykontakt[0] = Imie.getText();            //Pierwszym z nich jest imie
                    nowykontakt[1] = Nazwisko.getText();        //Drugim z nich jest nazwisko
                    nowykontakt[2] = Telefon.getText();         //Trzecim z nich jest telefon

                    kontakty[0].add(new Kontakt((String)nowykontakt[0],(String)nowykontakt[1],(String)nowykontakt[2]));        //Dodajemy go do naszej listy z JavaBeans

                    Lista.addRow(nowykontakt);  //Dodajemy go do graficznego GUI Tabelki
                    Index = tabelka.getRowCount();  //Sprawdzamy i przypisujemy ilość wierszy
                }
            }
        });

        //Usuń
        JLabel usunKontakt = new JLabel();
        usunKontakt.setText("Wpisz pozycje kontaktu którą chcesz usunąć");
        usunKontakt.setLocation(100,300);
        usunKontakt.setSize(400,25);

        JTextField Usun = new JTextField();
        Usun.setLocation(150,330);
        Usun.setSize(200,25);

        //Działanie przycisku usuń
        JButton Delete = new JButton("Usuń");
        Delete.setLocation(150,360);
        Delete.setSize(200,50);
        Delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(Integer.parseInt(Usun.getText()) > Index ) {        //Jeśli wpisany indeks nie istnieje
                    JOptionPane.showMessageDialog(null, "Nie ma takiego indeksu"); //Wyświetl odpowiedni komunikat
                } else {
                    kontakty[0].remove(kontakty[0].get(Integer.parseInt(Usun.getText())-1));          //Usuwamy z listy kontakt będący na danej pozycji
                    Lista.removeRow(Integer.parseInt(Usun.getText())-1); //Usuwamy z danej pozcyji kontakt w graficznym GUI Tabelki
                    Index = Index-1;    //Jeśli usuwamy, zmniejszamy ilość indeksów
                }
            }
        });

        //Działanie przycisku utwórz XML
        JButton exportXML = new JButton("Eksportuj");
        exportXML.setLocation(25,480);
        exportXML.setSize(200,50);

        exportXML.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                        XMLEncoder export = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("kontakty.xml")));     //Otwieramy strumień dla enkodera XML do pliku
                        export.writeObject(kontakty[0]);       //Zapisujemy dane z naszej listy z JavaBeans do pliku kontakty.xml
                        export.close();                     //Zamykamy strumień enkodera do pliku
                        JOptionPane.showMessageDialog(null, "Kontakty zostały zapisane do pliku XML");  //Wyświetlamy stosowną wiadomość
                    } catch (FileNotFoundException error) {     //Przechwytujemy w razie błędu
                        error.printStackTrace();
                    }
            }
        });

        //Działanie przycisku wczytaj XML
        JButton importXML = new JButton("Importuj");
        importXML.setLocation(275,480);
        importXML.setSize(200,50);

        importXML.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                final JFileChooser wyborPliku = new JFileChooser();   //tworzymy mechanizm wybierania pliku przez użytkownika
                if(wyborPliku.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {  //metoda zwracająca wartość wartość jeśli wybrano plik, inaczej okno będzie otwarte
                    File plik = wyborPliku.getSelectedFile(); //tworzymy obiekt klasy file i wybieramy wybrany plik
                    try
                    {
                     //Usuwamy wszystkie kontkaty z tablicy GUI
                        for(int i = 1; i<= kontakty[0].size(); i++) {
                            Lista.removeRow(0);
                            Index = 0;  //Zerujemy indeksy
                        }

                        FileInputStream strumienPliku = new FileInputStream(plik.getName());          //otwieramy strumień z pliku potrzebny do XMLDecoder
                        XMLDecoder decoder = new XMLDecoder(strumienPliku);                          //Tworzymy dekoder dla otwartego pliku
                        @SuppressWarnings("unchecked")              // Krzyczy jak nie damy, chcemy żeby to było dozwolone w przypadku który chcemy wykonać
                        ArrayList<Kontakt> importowane = (ArrayList<Kontakt>)decoder.readObject();      //Czytamy dane z pliku dekodując je z pomocą XMLDecoder
                        decoder.close();                //Zamykamy XMLDecoder
                        strumienPliku.close();               //Zamykamy strumień

                        kontakty[0] = importowane;     //dostęp z klasy wewnętrznej, dlatego musi być kontakty[0] i final (IntelliJ)
                        Index = kontakty[0].size();     //Teraz ilosc indeksów jest taka jak ilość kontaktów zaimportowanych


                        Object[] importowaneKontakty = new Object[3];       //Tworzymy obiekt który wstawimy do tablicy kontaktów
                        for(int i = 0; i < kontakty[0].size(); i++) {   //Pętla po kolei spisująca kontakty
                            importowaneKontakty[0] = kontakty[0].get(i).getImie();          //Dodawanie na kolejną pozycję
                            importowaneKontakty[1] = kontakty[0].get(i).getNazwisko();      //Dodawanie na kolejną pozycję
                            importowaneKontakty[2] = kontakty[0].get(i).getTelefon();       //Dodawanie na kolejną pozycję
                            Lista.addRow(importowaneKontakty);  //Dodajemy kontakty zczytane zapisane w obiekcie do listy
                        }

                    }
                    catch(IOException error)    //Złap błędy
                    {
                        error.printStackTrace();
                    }

                    JOptionPane.showMessageDialog(null, "Dane zostały zaimportowane do listy kontaktów"); //Po udanym wyborze pliku wyrzuć komunikat
                }
            }

        });

        //Działanie przycisku Wyświetl
        JButton Wyswietl = new JButton("Wyswietl");
        Wyswietl.setLocation(150,550);
        Wyswietl.setSize(200,50);
        Wyswietl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                okno = getContentPane();                                        //Glowna ramka
                okno.setLayout(null);       //Sposob wyswietlania
                setTitle("Lista Kontaktów");                                     //Tytul ramki/okna
                setResizable(false);                                             //Mozliwosc zmiany rozmiaru
                setSize(500, 600);                                  //Domyslny wyglad
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);                      //Zamykamy po zamknieciu ramki
                setVisible(true);                                                //Widoczne


                /*
                //Przycisk do edycji danych
                JButton Edytuj = new JButton("Edytuj");
                Edytuj.setLocation(150,450);
                Edytuj.setSize(200,50);
                Edytuj.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {

                        if(tabelka.getEditingRow()==-1 && tabelka.getEditingColumn()==-1) { //Jeśli jakieś pole jest edytowane

                        } else { }  //Powinno tutaj aktualizować listę ArrayList<kontakty> z tego co edytowaliśmy w GUI
                    }
                });

                //Dodajemy do ramki elementy
                   ramka.add(Edytuj);
                   */

                //Dodajemy do nowego okna
                okno.add(ramkaTabeli);
            }
        });


        //Dodajemy wszystko do panelu głównego
        frame.add(edykietadodajKontakt);
        frame.add(etykietaImie);
        frame.add(Imie);

        frame.add(etykietaNazwisko);
        frame.add(Nazwisko);

        frame.add(etykietaTelefon);
        frame.add(Telefon);

        frame.add(add);

        frame.add(usunKontakt);
        frame.add(Usun);
        frame.add(Delete);

        frame.add(exportXML);
        frame.add(importXML);

        frame.add(Wyswietl);
    }


    //Main
    public static void main(String[] args) {
        //Odpalamy książkę telefoniczną
        SwingUtilities.invokeLater(AddressBook::new);
    }
}

