/* Mroz Kamil

Program laduje plik o podanej w argumencie nazwie. Wyswietla najczesciej wystepujace wyrazy i nastepujace statystyki z pliku:
1) liczba niebiałych znaków  ( pomijamy tabulatory, spacje, enetry etc. )
2) liczba wyrazów   ( wyraz jest taki sam niezaleznie od wielkosci liter, lub dodanych przy nim znakow interpunkcyjnych )
3) liczba zdań (Przyjeta definicja: Zdanie jest uznawane gdy jest zakonczone znakiem . ? ! lub :. Przejscie do kolejnej linii niekoniecznie moze byc koncem zdania, wiec nie jest liczone

*/

import java.io.*;
import java.util.*;

class TheMostFrequent {

    // Tworzymy HashMape z iloscia powtarzajacych sie wyrazow
    public static HashMap<String, Integer> FrequentMap = new HashMap<>();

    //Liczymy wyrazy
    static void FrequentWord(String[] WordArray) {

        // Iterujemy po tablicy
        for (int i = 0; i < WordArray.length; i++) {

            if (WordArray[i].isBlank()) {
                return;
            }
            {
                if (FrequentMap.containsKey(WordArray[i])) {    // Jesli slowo istnieje juz w tablicy
                    FrequentMap.put(WordArray[i], FrequentMap.get(WordArray[i]) + 1); //zwiekszamy jego klucz z kazdym nastepnym pojawnieniem
                }
                // Jesli nie istnieje, dodajemy do mapy
                else {
                    FrequentMap.put(WordArray[i], 1);
                }
            }
        }
    }
}

public class Statistics extends TheMostFrequent {

    public static void main(String[] args) throws IOException
    {
        //Potrzebne zmienne
        String StringOfFile;   //Zmienna typu string do przechowywania zawartosci pliku
        String[] WordArray; //Tablica w ktorej bedziemy dzielic nasz plik na wyrazy
        String EndOfSentence = "?!.:"; //Znaki konczace zdanie

        //Liczniki
        int CharacterCounter = 0;   //Zmienna do zliczania znakow niebialych
        int WordCounter = 0;    // Zmienna do zliczania wyrazow
        int SentenceCounter = 0; //Zmienna do zliczania zdan - Zdanie uznajemy gdy jest ono zakonczone "?" "!" lub "."

        //plik bedzie strumieniem buforowanym
        BufferedReader plik = null;

        try {

            //Odczytujemy nazwe pliku uzytkownika
            String filename = args[0];

            //Strumien znakowy opatriujemy w strumien buforowany
            plik = new BufferedReader(new FileReader(filename));

            //Zapisujemy zawartosc pliku linia po lini w String dopoki nie dojdziemy do konca pliku
            while ((StringOfFile = plik.readLine()) != null) {


                //Czyscimi wartosc ze wszystkich znakow ktore nie wplywaja na ilosc wyrazow, oraz ujednolicamy wszystkie wyrazy
                String ClearString = StringOfFile.toLowerCase().replaceAll("[()?:!.,;{}]+","");
                //Dzielimy zapisany tekst bialymi znakami na kolejne komorki tablicy
                WordArray = ClearString.split("\\s+");
                //Iterujemy po elementach
                for (String s : WordArray) {
                    if (s.isBlank()) {    //Szukajac pustych komorek ( typu enter lub kilkutorny tab, gdyz \\s+ nie zawsze zalicza je do bialych znakow)
                        WordCounter = WordCounter - 1;    //Jesli znajdziemy, to odejmujemy od sumy wyrazow, tak aby wynik byl prawdziwy
                    }
                }
                WordCounter += WordArray.length; //Licznik wyrazow zlicza elementy z tablicy bez bialych znakow ( nie liczac entera lub kilka tab po sobie )

                //Wywolujemy zliczanie wyrazow
                FrequentWord(WordArray);

                //Iterujemy po po ciagu znakow znajdujac konce zdan
                for (int i = 0; i < StringOfFile.length(); i++) {
                    if (EndOfSentence.indexOf(StringOfFile.charAt(i)) != -1) { // Za kazdym razem, gdy znajdziemy w naszej tablicy jeden ze znakow konczacych zdanie
                        SentenceCounter++;  //Zwieszamy ilosc zdan
                    }
                }

                //Zatepujemy wszystkie puste komorki ( bialoznakowe ) ""
                String Chars = StringOfFile.replaceAll("\\s+","");
                //Iterujemy po ciagu znakow zliczajac elementy
                for(int i = 0; i < Chars.length(); i++) {
                    //Zliczamy znaki ( jesli nie sa ' ', ale ich juz nie mamy
                    if(Chars.charAt(i) != ' ')
                        CharacterCounter++;     //Zwieksz ilosc znakow
                }
            }

            System.out.print("Dziesiec najczesciej wystepujacych slow:\n");
            //Java 8 daje nam mozliwosc latwego posortowania mapy za pomoca entrySet i stream
            FrequentMap.entrySet().stream()
                    .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue()))  //sortujemy elementy
                    .limit(10)//Limit wyswietlania 10
                    .forEach(k -> System.out.println("Slowo \" " + k.getKey() + " \" wystepuje " + k.getValue() + " razy ")); //wyswietl kazdy element


            //Wyswietlamy liczbe znakow
            System.out.print("\n Liczba nie bialych znakow: "+CharacterCounter+"\n");
            //Wyswietlamy liczbe wyrazow
            System.out.print("\n Liczba Wyrazow: "+WordCounter+"\n");
            //Wyswietlamy liczbe slow
            System.out.print("\n Liczba Zdan: "+SentenceCounter+"\n");


        //Przechwyc
        } catch ( IOException error) {
            error.printStackTrace();
        } finally { //tak czy siak, zamknij plik
            if ( plik != null) { plik.close(); }
    }

    }

}

