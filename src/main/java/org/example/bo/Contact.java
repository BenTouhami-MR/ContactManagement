package org.example.bo;

public class Contact {
    private int ID;
    private String Nom;
    private String prenom;

    private String Adress;
    private String telephone1;
    private String telephone2;

    private String Emailprofessionnel;
    private String Emailpersonnel;

    private String Genre;

    public Contact(int ID, String nom, String prenom, String adress, String telephone1, String telephone2, String emailprofessionnel, String emailpersonnel, String genre) {
        this.ID = ID;
        this.Nom = nom;
        this.prenom = prenom;
        this.Adress = adress;
        this.telephone1 = telephone1;
        this.telephone2 = telephone2;
        Emailprofessionnel = emailprofessionnel;
        Emailpersonnel = emailpersonnel;
        Genre = genre;
    }

    public Contact(String nom, String prenom, String adress, String telephone1, String telephone2, String emailprofessionnel, String emailpersonnel, String genre) {
        Nom = nom;
        this.prenom = prenom;
        Adress = adress;
        this.telephone1 = telephone1;
        this.telephone2 = telephone2;
        Emailprofessionnel = emailprofessionnel;
        Emailpersonnel = emailpersonnel;
        Genre = genre;
    }
    public Contact(){

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }

    public String gettelephone1() {
        return telephone1;
    }

    public void settelephone1(String telephone1) {
        this.telephone1 = telephone1;
    }

    public String gettelephone2() {
        return telephone2;
    }

    public void settelephone2(String telephone2) {
        this.telephone2 = telephone2;
    }

    public String getEmailprofessionnel() {
        return Emailprofessionnel;
    }

    public void setEmailprofessionnel(String emailprofessionnel) {
        Emailprofessionnel = emailprofessionnel;
    }

    public String getEmailpersonnel() {
        return Emailpersonnel;
    }

    public void setEmailpersonnel(String emailpersonnel) {
        Emailpersonnel = emailpersonnel;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    @Override
    public String toString(){
        return "(nom= "+getNom()+", prenom= "+getPrenom()+", Adress=" +getAdress()+", telephone1= "+gettelephone1()+", telephone2= "+gettelephone2()
                +", Emailprofessionnel= "+getEmailprofessionnel()+", Emailpersonnel= "+getEmailpersonnel()+", Genre= "+getGenre()+")";
    }
}