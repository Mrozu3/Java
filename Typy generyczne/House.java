/*Kamil Mroz

Klasa reprezentujaca mape zawierajaca klucz -> wartosc. Uzylem HashMap, ktora pozwala na bez dodatkowych bibliotek zaimplementowac wszystkie potrzebne metody:
- dodawanie elementów (dodanie elementu o użytym już wcześniej kluczu podmienia ten stary) za pomoca put
- dostęp do wartości przez klucz za pomoca get
- sprawdzanie czy istnieje element o podanym kluczu za pomoca containsKey

Klase House rozszerza nasza mapa przechowujaca urzedzenia domowe string z kluczami int.
Dodana metoda acceptVisitor przyjmujaca obiekt o interfejsie Visitor. Usuwa wszystkie elementy ktore Visitor kaze usunac
Interfejs Visitor ma metodę shouldRemove przyjmującą pojedynczy klucz z mapy i dzialajacy wg. podanych kryteriow Visitora
klasa Thief implementujaca Vistor z naszej mapy znikaja przedmioty o wartosci wiekszej niz 1000 zl
 */

//Importujemy potrzebna nam kolekcje
import java.util.HashMap;

//Klasa reprezentujaca mape
class Mapa<K,V> {

    //Tworzymy HashMap przechowujaca nasze wartosci klucz - K - key i wartosc - V - variable
    public HashMap<K,V> hashmap = new HashMap<>();

    //funkcja dodajaca nam klucz i wartosc do HashMap
    public void add(K klucz, V wartosc) {
        //put wstawia do mapy wartosc przypisana do klucza k
        hashmap.put(klucz, wartosc);
    }

    //dostęp do wartości przez klucz
    public void get(K klucz) {
        //get zwraca wartosc przypisana do klucza lub null jesli do takiego klucza nie jest przypisana żadna wartość
        if( hashmap.get(klucz) != null ) {  //Jesli nie jest NULL
            System.out.println("Wartosc przypisana do klucza: " + hashmap.get(klucz)); //Wyswietlamy element
        } else {
            System.out.println("Nie istnieje podany klucz"); //Podajemy komunikat jesli nie istnieje

        }
    }

    //sprawdzanie, czy isnieje element o podanym kluczu
    public void exist(K klucz) {
        //containsKey zwraca nam true lub false
        if (hashmap.containsKey(klucz)) {
            System.out.println("Klucz istnieje"); // Jesli klucz istnieje, zwracamy odpowiedni komunikat
        } else {
            System.out.println("Klucz nie istnieje"); //Jesli klucz nie istnieje, zwracamy odpowiedni komunikat
        }
    }

    //Zebysmy mogli wyswietlac mape
    public void show() {
            System.out.println(hashmap); //Najszybszy sposob na wyswietlenie, tylko pogladowo
    }

    //Metoda acceptVisitor przyjmujaca obiekt o interfejsie Visitor
    public void acceptVisitor(Visitor<K> klucz) {
        hashmap.keySet().removeIf(klucz::shouldRemove); //usuwamy klucze wg. kryteriow, dziala na zasadzie filtra uzyty na naszej mapie na zbiorze kluczy
    }

} // Koniec Mapy


//interfejs Visitor
interface Visitor<K> {
        //Metoda true/false shouldRemove przyjmujaca pojedynczy klucz z mapy
        boolean shouldRemove(K klucz);
}

//Dom dziedziczy utworzona przez nas mape
public class House extends Mapa<Integer,String> {

    //Klasa zlodziej implementujaca Visitor
    static class Thief implements Visitor<Integer>
    {
        //wszystkie klucze wieksze od 1000 beda brane pod uwage
        public boolean shouldRemove(Integer klucz){ return (klucz > 1000); }
    }

    //Glowna funkcja
    public static void main(String[] args) {

        //Powolujemy instancje klasy House na nasza mape
        House maplist = new House();

        //Wypelniam liste
        maplist.add(200,"Zwykly Toster");
        maplist.add(400,"Drozszy toster");
        maplist.add(800,"Przeplacony toster");
        maplist.add(1200,"Zloty toster");
        maplist.add(2400,"Skradziony toster");

        System.out.print("Chcemy zobaczyc nasze tostery....\n");

        //Wyswietlamy liste
        maplist.show();

        System.out.print("Przyszedl zlodziej....\n");

        //Powolujemy instancje klasy Thief, reprezantacja thief
        Thief thief = new Thief();
        maplist.acceptVisitor(thief);
        //Wyswietlamy liste po przyjscu zlodzieja
        maplist.show();

        //Chcemy toster o kluczu 2400 i 400
        System.out.print("Chcemy uzyc jednego z naszych tosterow....\n");
        maplist.get(2400);
        maplist.get(400);
        //Sprawdzamy czy istnieje element o kluczu 1200
        System.out.print("Sprawdzamy czy nasz toster jeszcze nalezy do nas....\n");
        maplist.exist(2400);

        System.out.print("Kupmy nowy toster!\n");
        maplist.add(400,"Drozszy toster 2.0");
        //Wyswietlamy liste
        maplist.show();
    }
}

