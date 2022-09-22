//JavaBeans
public class Kontakt {
    private String Imie;        //pole prywatne na imie
    private String Nazwisko;    //pole prywatne na nazwisko
    private String Telefon;     //pole prywatne na telefon

    public Kontakt(){}

    public Kontakt(String Imie, String Nazwisko, String Telefon) {
        this.Imie = Imie;
        this.Nazwisko = Nazwisko   ;
        this.Telefon = Telefon;
    }

    //Settery
    public void setImie(String imie) {
        Imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        Nazwisko = nazwisko;
    }

    public void setTelefon(String telefon) {
        Telefon = telefon;
    }

    //Gettery
    public String getImie() {
        return Imie;
    }

    public String getNazwisko() {
        return Nazwisko;
    }

    public String getTelefon() {
        return Telefon;
    }
}
