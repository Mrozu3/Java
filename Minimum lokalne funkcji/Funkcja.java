/* Zadanie 2 Mroz Kamil

Program do szukania minimum lokalnego funkcji w przedziale

javac Funkcja.java
java Funkcja - na przykladowym przedziale
java Funkcja -5 -3 - na podanym przedziale

*/

//Rozszerzamy klase Minimum, dziedziczac po klasie Funkcja
class Minimum extends Funkcja{

    //Podajemy konkretny punkt 
    @Override
    double getValue(double x) {

    //Dla przykladowej konkretnej funkcji
        double A = 3;   // Musi byc >0 zeby funkcja moga miec minimum lokalne      
        double B = 2;
        double C = 5;

    //Liczmy wartosc w konkretnym punkcie
        double fx = ( A * Math.pow(x,2) + B * x + C ) ;
    
    //Zwracamy 
        return fx;
    }
}

//Klasa abstrakcyjna...
public abstract class Funkcja {

    //..posiadajaca metode abstrakcyjna...
    abstract double getValue(double x);


    //..oraz zwykla metoda do szukania minimum lokalnego
    double findMinimum(double a,double b) {

        //Liczenie podstawieniem poczatku przedzialu
        double min = getValue(a);

        //Precyzja szukania
        double precision = 0.1;

        //Dopoki nie dojdziemy do konca przedzialu, szukamy najmniejszej wartosci co dana prezycje
        for(double i = a; i <= b; i = i + precision)
        {
            //Liczenie podstawieniem wartosci
            double fx = getValue(i);
            
            //Szukamy najmniejszej wartosci i podmieniamy gdy znajdziemy
            if( min > fx ) {  min = fx;  }
        }

        return min;
    }

//Glowna metoda
    public static void main(String[] args) {
        
    //Powoloujemy instancje klasy minimum
    Minimum minimum = new Minimum();

    //Jesli nie podalismy zakresu w argumentach
    if( args.length == 0 ) { 

        //Przypisanie przedzialu 
        double a = 3;      
        double b = 5;

        //Przypisanie wyniku funkcji 
        double min = minimum.findMinimum(a,b);
        //Wyswietlenie komentarza
        System.out.println("Minimum lokalne przykladowej funkcji w przedziale [ " + a + " , " + b +" ] jest rowne " + min );

    //Jesli podamy zakres w argumentach
    } else if( args.length == 2) {
        
        //Przypisanie argumentow jako przedzialu
        double a = Double.parseDouble(args[0]);
        double b = Double.parseDouble(args[1]);
           
        //Zabezpieczenie przed zlym przedzialem
            if(a >= b )
                {
                    double c = a;
                    a = b;
                    b = c;
                }

        //Przypisanie wyniku funkcji 
        double min = minimum.findMinimum(a,b);
        //Wyswietlenie komentarza
        System.out.println("Minimum lokalne przykladowej funkcji w przedziale [ " + a + " , " + b +" ] jest rowne " + min );

    //W przypadku gdy argumenty sie nie zgadzaja
    } else {

        System.out.println("Prosze podac *poczatek przedzialu a*, *koniec przedzialu b*");
    }

    }
}
