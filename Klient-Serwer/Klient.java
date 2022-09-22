/*

klienta otwieramy podając w argumencie programu adres (np. 127.0.0.1 ) i port ( np. 1234 );


 */

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

public class Klient {
    public static PrintWriter serverSender;
    public static JTextField nazwaPliku;
    public static JButton otworzPlik;
    public static JTextArea pokazPlik;

    public static void receiveMessages(Socket socket) {
        try {
            var serverReceiver = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String serverLine;
            while ((serverLine = serverReceiver.readLine()) != null) {
                System.out.println(serverLine);
                if("Nie ma takiego pliku".equals(serverLine)) {
                    JOptionPane.showMessageDialog(null, "Nie ma takiego pliku \n");        //Daj odpowiedni komunikat
                }
                final String finalServerLine = serverLine;
                SwingUtilities.invokeLater(() -> pokazPlik.append("\n" + finalServerLine + " "));
            }

            System.out.println("Serwer zamknął połączenie");
            SwingUtilities.invokeLater(() -> {
                nazwaPliku.setEditable(false);
                otworzPlik.setEnabled(false);
                pokazPlik.append("Serwer zamknął połączenie.\n");
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void GUI() {
        var ramka = new JFrame("File Client");
        var okno = new JPanel();
        var layout = new BoxLayout(okno, BoxLayout.PAGE_AXIS);
        okno.setLayout(layout);

        nazwaPliku = new JTextField();
        nazwaPliku.setPreferredSize(new Dimension(300, 50));
        okno.add(nazwaPliku);

        otworzPlik = new JButton("Open");
        otworzPlik.setAlignmentX(Component.CENTER_ALIGNMENT);
        otworzPlik.setPreferredSize(new Dimension(100, 50));
        otworzPlik.addActionListener((actionEvent) -> {
            serverSender.println(nazwaPliku.getText());
            serverSender.flush();
            pokazPlik.selectAll();
            pokazPlik.replaceSelection("");

        });
        okno.add(otworzPlik);

        pokazPlik = new JTextArea();
        pokazPlik.setEditable(false);
        pokazPlik.setLineWrap(true);
        var scrollPane = new JScrollPane(pokazPlik);
        scrollPane.setPreferredSize(new Dimension(300, 300));
        okno.add(scrollPane);

        ramka.add(okno);
        ramka.pack();
        ramka.setLocationRelativeTo(null);
        ramka.setVisible(true);
        ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String [] args) throws InvocationTargetException, InterruptedException {
        try (Socket socket = new Socket(args[0], Integer.parseInt(args[1]))) {
            System.out.println("Nawiązano połączenie z serwerem");
            serverSender = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            SwingUtilities.invokeAndWait(Klient::GUI);
            receiveMessages(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
