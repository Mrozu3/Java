/*

serwer otwieramy podając w argumencie programu port ( np. 1234 );


 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String [] args) {
        try (var serverSock = new ServerSocket(Integer.parseInt(args[0]))) {
            System.out.println("Serwer jest tutaj by odebrać Twój plik");

            while (true) {
                Socket clientSock = serverSock.accept();
                System.out.println("Klient przybył by otworzyć plik");

                var in = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
                var out = new PrintWriter(new OutputStreamWriter(clientSock.getOutputStream()));

                String StringOfFile;   //Zmienna typu string do przechowywania zawartosci pliku
                String nazwapliku;
                //plik bedzie strumieniem buforowanym
                BufferedReader plik = null;

                while ((nazwapliku = in.readLine()) != null) {
                    if ("wyjdz".equals(nazwapliku)) {
                        System.out.println("Klient wychodzi z serwera");
                        break;
                    }
                    if ("zamknij".equals(nazwapliku)) {
                        System.out.println("Klient zamyka serwer");
                        System.exit(0);
                    }

                    else if ( !"".equals(nazwapliku) ) {

                         try {
                             //Strumien znakowy opatriujemy w strumien buforowany
                             plik = new BufferedReader(new FileReader(nazwapliku));

                             //Zapisujemy zawartosc pliku linia po lini w String dopoki nie dojdziemy do konca pliku
                             while ((StringOfFile = plik.readLine()) != null) {

                                 out.println(StringOfFile);
                                 out.flush();
                             }

                             //Przechwyc
                         } catch ( IOException error) {
                             error.printStackTrace();
                             out.println("Nie ma takiego pliku");
                             out.flush();
                             System.out.println("Nie ma takiego pliku \n");


                         } finally { //tak czy siak, zamknij plik
                             if ( plik != null) { plik.close(); }
                         }
                    }

                    System.out.println("Dany plik to: " + nazwapliku);
                    out.println("\nPodana nazwa pliku to: " + nazwapliku);
                    out.flush();
                }

                System.out.println("Połączenie przerwane");
                clientSock.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
