/* Mróz Kamil

Książka telefoniczna w SQL z dodawaniem, usuwaniem i edycją kontaktów przez interfejs graficzny
Wyświetlanie bazy danych tekstowe w terminalu.

 */

//Potrzebne Importy
import javax.swing.*;
import java.awt.event.*;
import java.sql.DriverManager;
import java.sql.SQLException;


//Klasa rozszerzona o JFrame
public class AddressBook extends JFrame {
    static JFrame frame;        //Panel Główny
    int Index = 0;              //Indeksy

    public AddressBook() {
        frame = new JFrame("Książka Adresowa"); //Tytuł okna
        frame.setSize(500, 600);         //Rozmiar panelu
        frame.setResizable(false);                   //Możliwość rozszerzania
        frame.setLayout(null);                       //Wzorzec
        frame.setVisible(true);                     //Widzialność
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);   //Operacja zamykania

        Controls(); //Menu książki telefonicznej
    }

    private void Controls() {

        //Elementy Menu
        //Dodaj
        JLabel edykietadodajKontakt = new JLabel();
        edykietadodajKontakt.setText("Dodaj lub edytuj kontakt");
        edykietadodajKontakt.setLocation(160, 20);
        edykietadodajKontakt.setSize(300, 25);

        //Imie
        JLabel etykietaImie = new JLabel();
        etykietaImie.setText("Imię: ");
        etykietaImie.setLocation(93, 50);
        etykietaImie.setSize(100, 25);

        JTextField Imie = new JTextField();
        Imie.setLocation(150, 50);
        Imie.setSize(200, 25);

        //Nazwisko
        JLabel etykietaNazwisko = new JLabel();
        etykietaNazwisko.setText("Nazwisko: ");
        etykietaNazwisko.setLocation(60, 100);
        etykietaNazwisko.setSize(100, 25);

        JTextField Nazwisko = new JTextField();
        Nazwisko.setLocation(150, 100);
        Nazwisko.setSize(200, 25);

        //Telefon
        JLabel etykietaTelefon = new JLabel();
        etykietaTelefon.setText("Telefon: ");
        etykietaTelefon.setLocation(72, 150);
        etykietaTelefon.setSize(100, 25);

        JTextField Telefon = new JTextField();
        Telefon.setLocation(150, 150);
        Telefon.setSize(200, 25);

        //Działanie przycisku Dodaj
        JButton add = new JButton("Dodaj");
        add.setLocation(150, 200);
        add.setSize(200, 50);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (Imie.getText().equals("") || Nazwisko.getText().equals("") || Telefon.getText().equals("")) {        //Jeśli któreś okno jest puste
                    JOptionPane.showMessageDialog(null, "Wszystkie dane muszą być uzupełnione");        //Daj odpowiedni komunikat
                } else {

                    //Łączymy z bazą danych
                    try (var connection = DriverManager.getConnection("jdbc:hsqldb:file:addressbook;shutdown=true", "SA", "");
                         var statement = connection.createStatement()) {

                        // Dodawanie do tabeli rekordów z danymi
                        statement.executeUpdate("INSERT INTO addressbook(id, imie, nazwisko, telefon) VALUES " +
                                "("+Index+", '"+Imie.getText()+"', '"+Nazwisko.getText()+"', '"+Telefon.getText()+"')");

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    Index = Index + 1;

                }
            }
        });


        //Usuń
        JLabel usunKontakt = new JLabel();
        usunKontakt.setText("Wpisz ID kontaktu który chcesz usunąć lub edytować");
        usunKontakt.setLocation(75, 300);
        usunKontakt.setSize(400, 25);

        JTextField Usun = new JTextField();
        Usun.setLocation(150, 330);
        Usun.setSize(200, 25);

        //Działanie przycisku usuń
        JButton Delete = new JButton("Usuń");
        Delete.setLocation(25, 375);
        Delete.setSize(200, 50);
        Delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (Integer.parseInt(Usun.getText()) > Index) {        //Jeśli wpisany indeks nie istnieje
                    JOptionPane.showMessageDialog(null, "Nie ma takiego indeksu"); //Wyświetl odpowiedni komunikat
                } else {

                    //Łączymy z bazą danych
                    try (var connection = DriverManager.getConnection("jdbc:hsqldb:file:addressbook;shutdown=true", "SA", "");
                         var statement = connection.createStatement()) {

                        //parsujemy do temp
                        int temp = (Integer.parseInt(Usun.getText()));
                        // Usuwanie rekordów o danym ID
                        statement.executeUpdate("DELETE FROM addressbook WHERE id = "+temp+"");

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


                }
            }
        });

        //Działanie przycisku Edytuj
        JButton edit = new JButton("Edytuj");
        edit.setLocation(275, 375);
        edit.setSize(200, 50);
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (Imie.getText().equals("") || Nazwisko.getText().equals("") || Telefon.getText().equals("") || Usun.getText().equals("")) {        //Jeśli któreś okno jest puste
                    JOptionPane.showMessageDialog(null, "Wszystkie dane muszą być uzupełnione aby edytować kontakt");        //Daj odpowiedni komunikat
                } else {

                    //Łączymy z bazą danych
                    try (var connection = DriverManager.getConnection("jdbc:hsqldb:file:addressbook;shutdown=true", "SA", "");
                         var statement = connection.createStatement()) {

                        // Aktualizacja rekordów
                        int temp = (Integer.parseInt(Usun.getText()));
                        statement.executeUpdate("UPDATE addressbook SET imie = '"+Imie.getText()+"', nazwisko = '"+Nazwisko.getText()+"', telefon  = '"+Telefon.getText()+"' WHERE id = "+temp+"");

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


                }
            }
        });

        //Działanie przycisku Wyświetl
        JButton Wyswietl = new JButton("Wyswietl");
        Wyswietl.setLocation(150, 450);
        Wyswietl.setSize(200, 50);
        Wyswietl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                //Łączymy z bazą danych
                try (var connection = DriverManager.getConnection("jdbc:hsqldb:file:addressbook;shutdown=true", "SA", "");
                     var statement = connection.createStatement()) {

                    //przypisujemy pobrane rekordy z bazy danych zapytaniem
                    var bazadanych = statement.executeQuery("SELECT * FROM addressbook");  // Wczytywanie wszystkich
                    System.out.println("ID | Imie | Nazwisko | Telefon");
                    //Wypisujemy je dopóki istnieje kolejny
                    while (bazadanych.next()) {
                        System.out.print(bazadanych.getInt("id") + " | " + bazadanych.getString("imie") + " | " + bazadanych.getString("nazwisko") + " | " + bazadanych.getString("telefon") + "\n");

                    }
                    bazadanych.close(); //zamykamy gdy wypisaliśmy wszystkie

                } catch (SQLException e) {
                    e.printStackTrace();
                }

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
        frame.add(edit);

        frame.add(usunKontakt);
        frame.add(Usun);
        frame.add(Delete);

        frame.add(Wyswietl);
    }

    //Main
    public static void main(String[] args) throws ClassNotFoundException {
        //Ładujemy odpowiedni sterownik
        Class.forName("org.hsqldb.jdbc.JDBCDriver");

        //Łącznie z bazą danych
        try (var connection = DriverManager.getConnection("jdbc:hsqldb:file:addressbook;shutdown=true", "SA", "");
             var statement = connection.createStatement()) {

            //Usuwanie tabeli jeśli istnieje
            statement.execute("DROP TABLE addressbook IF EXISTS");

            // Utworzenie tabeli
            statement.execute("CREATE TABLE IF NOT EXISTS addressbook (id INT, imie VARCHAR(32), nazwisko VARCHAR(32), telefon VARCHAR(32)) ");

        } catch (SQLException error) {  //W razie errora
            error.printStackTrace();
        }

        //Odpalamy książkę telefoniczną
        SwingUtilities.invokeLater(AddressBook::new);
    }
}
