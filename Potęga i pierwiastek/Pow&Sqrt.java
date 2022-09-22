/*

Mroz Kamil
Program do obliczania n-tej potegi liczby v oraz n-tego pierwiastka liczby v bez uzycia bibloteki Math ( z wylaczeniem Math.abs )
javac Zadanie1.java
java Zadanie1 - bez argumentow wyswietli przykladowe rozwiazania
java Zadanie1 5 5 - z dwoma argumentami wyswietli ich rozwiazanie
*/

public class Zadanie1 {

//Precyzja
public static final double precision = 1.0e-5;

//Metoda poteg
public static double nPow(double v, int n) {
	if( n < 0 ) {    //Sprawdzenie poprawnosci argumentow
		System.out.println("Potega musi byc wieksza od 0 badz rowna 0");
		System.exit(1);
	} 

	//Potega rekurencyjnie
	if( n == 0 ) { v=1; }
	else { v=v*nPow(v,n-1); }

	return v; //Zwracam wynik

}

//Metoda pierwiastkow
public static double nRoot(double v, int n) {
	if( v < 0 || n <= 0 ) {    //Sprawdzenie poprawnosci arugmentow
		System.out.println("Podana liczba musi byc wieksza badz rowna 0, a pierwiastek wiekszy od 0");
		System.exit(1);
	}
		//Poczatkowe przyblizenie
		double wynik = v;
		//Potegowanie
		double temp = nPow(wynik,(n-1));
		
		//Ogranicznik do dokladnosci
		while(Math.abs(v - temp * wynik) >= precision)
		{
			//Wzor na kolejne przyblizenia wynikajacy z metody Newtona-Raphsona
			wynik = 1/(1.0*n) *((n-1)*wynik + (v/temp));
			temp = nPow(wynik,(n-1));
		}
		
		return wynik; //Zwracam wynik
}

//Glowna metoda
public static void main(String[] args) {
	//Jesli nie podano argumentow wyswietl przykladowe rozwiazania
	if(args.length == 0 ) {  
		
		System.out.println( "0 do potegi 0 wynosi: " + nPow(0,0));
		System.out.println( "1 do potegi 5 wynosi: " + nPow(1,5));
		System.out.println( "5.215  do potegi 0 wynosi: " + nPow(5.215,0));
		System.out.println( "8.912  do potegi 1 wynosi: " + nPow(8.912,1));
		System.out.println( "-6 do potegi 3 wynosi: " + nPow(-6,3));
		System.out.println( "8.512 do potegi 6 wynosi: " + nPow(8.512,6));
	    System.out.println( "Pierwiastek 1 stopnia z 5 wynosi: " + nRoot(5,1));
		System.out.println( "Pierwiastek 5 stopnia z 1 wynosi: " + nRoot(1,5));
		System.out.println( "Pierwiastek 2 stopnia z 5 wynosi: " + nRoot(5,2));
		System.out.println( "Pierwiastek 6 stopnia z 2.124 wynosi: " + nRoot(2.124,6));
		System.out.println( "Pierwiastek 2 stopnia z 4 wynosi: " + nRoot(4,2));
		System.out.println( "Pierwiastek 7 stopnia z 3.613 wynosi: " + nRoot(3.613,7));
		//Niepoprawne argumenty natychmiastowo koncza dzialanie programu i nie wyswietlaja dalszych instrukcji :<
		//System.out.println( "0  do potegi -5 wynosi: " + nPow(0,-5));
		//System.out.println( "-2 pierwiastek z 0 wynosi: " + nRoot(-2,0));
		
	}

	//Jesli podano 2 argumenty
	else if(args.length == 2) {
	
	Double v = Double.parseDouble(args[0]);	    // Przypisanie liczby
	Integer n = Integer.parseInt(args[1]);      //Przypisanie stopnia
	
	//Wypisanie wynikow funkcji potegujacej i pierwiastkujacej
	System.out.println( v + " do potegi " + n + " wynosi: " + nPow(v,n));
	System.out.println( n + " pierwiastek z "+ v +" wynosi: " + nRoot(v,n));
	 }

	//Jesli liczba argumentow sie nie zgadza
	else {
		System.out.println("Prosze podac dwa argumenty: *liczba v* oraz *liczba n* ");
		System.exit(1);
	} 
   }
}
