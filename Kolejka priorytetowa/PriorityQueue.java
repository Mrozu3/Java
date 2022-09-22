/* Kamil Mroz

Implementacja kolejki priorytetowej bez java.util.PriorytyQueue, z dwoma operacjami:
Wstawianie nowego elementu do kolejki void add() - zlozonosc O(1)
Zwracanie i usuwanie z kolejki element o najwyższym priorytecie. Jeśli w kolejce znajduje się kilka obiektów o najwyższym priorytecie, zwracany jest ten z nich, który został tam wstawiony najwcześniej Integer get() - zlozonosc O(n);

Dla zobrazowania dzialania, program pozwala dolozyc jeden element do kolejki o wybranym priorytecie za pomoca podania dwoch argumentow:
javac PriorityQueue.java
java PriorityQueue 5 5

 */


//Ladujemy klase tablicy
import java.util.ArrayList;

//Obiekt z dwoma parametrami, ktory posluzy nam do przechowywania w liscie dwoch parametrow w jednej komorce
class Pair {
    Integer value; //Wartosc
    int priority;  //Priorytet

    public Pair( Integer value, int priority) {
        this.value = value;  //Wartosc
        this.priority = priority;  //Priorytet
    }

}

public class PriorityQueue {

    //Tworzymy liste tablicowa widziana dla wszystkich metod, przechowujaca stworzony przez nas obiekt przechowujacy pary
    public static ArrayList<Pair> KolejkaPriorytetowa = new ArrayList<>();

    //Funckja dodajaca element typu object przechowujacy pary do tablicy sluzacej nam za kolejke
    void add(Pair element){
        KolejkaPriorytetowa.add(element);
    }

    //Integer get() - zwracam i usuwam z kolejki element o najwyzszym priorytecie lub ten ktory zostal wstawiony najwczesniej
    Integer get() {
        int findtop = 0;   //Dla najwyzszego priorytetu
        int index = 0;      //index komorki do usuniecia
        Pair top = KolejkaPriorytetowa.get(0); //do obiektu przechowujacego pare przypisujemy z kolejki index 0
        //Petla "foreach" do znalezienia najwiekszego elementu
        for (Pair i : KolejkaPriorytetowa) {
            //Jesli priorytet jest wyzszy - podmien go, jesli rowny - pomijamy poniewaz jest najwczesniej wstawionym
            if (findtop < i.priority) {
                findtop = i.priority;
                //Odczytujemy index na ktorym jest nasz element
                index = KolejkaPriorytetowa.indexOf(i);
                //Przypisujemy numer indexu do kolejnosci elementu w obiekcie
                top = i;
            }
        }
        //Usuwamy znaleziony indeks z tablicy
        KolejkaPriorytetowa.remove(index);
        //Wyswietlamy komunikat
        System.out.print("\nUsuwam z kolejki element o wartosci " + top.value + " o najwyszym priorytecie " + top.priority + " wstawionym najwczesniej\n");

        //Funkcja zwraca element o najwyzszym priorytecie
        return top.value;
    }

    void show() {
        //Wyswietlam kolejke
        System.out.println("         Wartosc:   Priorytet:");
        int j = -1; //Zmienna indeksu
        //Petla "foreach" do wyswietlenia wszystkich elementow
        for (Pair i : KolejkaPriorytetowa) {
            j++;
            System.out.print( "Indeks " + j + "|     " + i.value + "          " + i.priority + "   | \n");
        }
    }

    //Glowna funkcja
    public static void main(String [] args) {

    //Jesli podane zostana 2 argumenty
    if( args.length == 2) {

        Integer value = Integer.parseInt(args[0]);        // Przypisuje Wartosc
        int priority = Integer.parseInt(args[1]);       // Przypisuje Priorytet

        //Powolujemy instancje klasy PriorityQueue na nasz obiekt
            PriorityQueue Obiekt = new PriorityQueue();

        //Wypelniam kolejke danymi
        Pair Element1 = new Pair(1, 10);
        Obiekt.add(Element1);
        Pair Element2 = new Pair(2, 10);
        Obiekt.add(Element2);
        Pair Element3 = new Pair(3, 20);
        Obiekt.add(Element3);
        Pair Element4 = new Pair(4, 20);
        Obiekt.add(Element4);

        //Dodaje element podany w argumentach
        Pair Element5 = new Pair(value, priority);
        Obiekt.add(Element5);

        //Wyswietlam kolejke z dodanym przez nas elementem - stan poczatkowy
        System.out.println("Kolejka po dodaniu elementu: ");
        Obiekt.show();
        //Usuwam element z najwyzszym priorytetem do ostatniego indeksu i wyswietlam kolejke
        for( int i = -1; i <= KolejkaPriorytetowa.size() ; i++ ) {
            int a = Obiekt.get();   // Przypisujemy zwrocona wartosci
            System.out.print("Zwracam z kolejki priorytetowej wartosc elementu z najwyzszym priorytetem: " + a + " \n \n");
            //Wyswietlamy kolejke
            Obiekt.show();
        }

    //Jesli nie podane zostaly zadne argumenty
    } else { System.out.println("Podaj wartosc i priorytet elementu ktory chcesz dodac do kolejki"); }

    }

}






//https://stackoverflow.com/questions/13738441/how-to-initialize-a-two-column-arraylist/13738517