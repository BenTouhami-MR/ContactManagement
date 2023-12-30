package org.example.him;

import org.apache.log4j.Logger;
import org.example.bll.BuiesnessLogicException;
import org.example.bll.ContactManager;
import org.example.bo.Contact;
import org.example.bo.Group;
import org.example.data.DataBaseException;
import org.example.data.DbInstaller;
import org.example.data.DbInstellerException;
import org.example.data.GroupDao;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {


    static Logger logger=Logger.getLogger(Main.class);
     static Scanner scanner1 =new Scanner(System.in);
     static ContactManager contactManager= new ContactManager();

    public  static void menu(){

        String content="1- Cree un contact\n2- Chercher un contact par nom\n3- Chercher un contact par numero\n4- Modifier un contact\n5- Supprimer un contact" +
                "\n6- Afficher la list des contacts par ordre alphabétique\n7 -Cree un group vide\n8- Cree un group contient des contacts\n9- ajouter des contactes à un group\n10- Cherecher un group par son nom\n11- Supprimer un group\n0- sortir" ;
        System.out.println(content);
    }


    //TODO: la paritie du gestion du contact
    public static  void creeUncontact() throws DataBaseException,BuiesnessLogicException{
        Contact contact=new Contact();


        System.out.println("enter le nom du contact: ");
        contact.setNom(scanner1.nextLine());
        System.out.println("entrer le prenom du contact: ");
        contact.setPrenom(scanner1.nextLine());
        System.out.println("entrer l'adresse du contact: ");
        contact.setAdress(scanner1.nextLine());
        do{
            System.out.println("entrer le telephone1 du contact:");
            String telephone1 = scanner1.nextLine();
            System.out.println("entrer le telephone2 du contact: ");
            String telephone2=scanner1.nextLine();
            if(!telephone1.matches("^0[6,7]\\d{8}$") ||!telephone2.matches("^0[6,7]\\d{8}$")) {
                System.out.println("syntaxe invalide d'un ou des deux telephones!!");
                continue;
            }
            contact.settelephone1(telephone1);
            contact.settelephone2(telephone2);
            break;

        }while(true);

        do{
            System.out.println("entrer l'email professionnel du contact:");
            String Emailprofessionnel = scanner1.nextLine();
            System.out.println("entrer l'email personnel du contact: ");
            String Emailpersonnel=scanner1.nextLine();
            if(!Emailpersonnel.matches("^[A-Za-z0-9+.-]+@[A-Za-z0-9.]+[A-Za-z]{2,}$") ||!Emailprofessionnel.matches("^[A-Za-z0-9+.-]+@[A-Za-z0-9.-]+[A-Za-z]{2,}$")) {
                System.out.println("syntaxe invalide d'un ou des deux email!!");
                continue;
            }
            contact.setEmailpersonnel(Emailpersonnel);
            contact.setEmailprofessionnel(Emailprofessionnel);
            break;

            }while(true);

        System.out.println("entrer le genre du contact: ");
        contact.setGenre(scanner1.nextLine());
        contactManager.createContact(contact);
        System.out.println("le contact a été cré avec succées");


    }

    public static  void chercherUnContact() throws DataBaseException,BuiesnessLogicException{
        System.out.println("entrer le nom du contact que vous voulez chercher:  ");
        List<Contact> contacts =contactManager.chercherUnContactParNom(scanner1.nextLine());

    }

    /**
     * cette methode permet de chercher un contact par nom
     * @throws DataBaseException
     * @throws BuiesnessLogicException
     */
    public static void checherUnContactParNom() throws DataBaseException,BuiesnessLogicException {
        System.out.println("entrer le nom du contact que vous voulez chercher:  ");
        List<Contact> listDesContactTrouver=contactManager.chercherUnContactParNom(scanner1.nextLine());
        System.out.println("les contacts trouver sont:  ");
        for (Contact c :listDesContactTrouver){
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
            System.out.println(c);

        }
        System.out.println("\n");

    }

    /**
     * cette methode permet de chercher par un le numero de telephone
     * @throws DataBaseException
     * @throws BuiesnessLogicException
     */
    public static void chercherUnContactParNumero() throws DataBaseException,BuiesnessLogicException{
    System.out.println("saisir le numero du contact:   ");
    Contact c =contactManager.chercherContactParNumero(scanner1.nextLine());
    System.out.println("le contact trouver est: ");
    System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
    System.out.println(c);
    System.out.println("\n");
}

    /**
     * cette methode permet de modifier un contact
     * @throws DataBaseException
     * @throws BuiesnessLogicException
     */
    public static  void modifierUnContact()  throws DataBaseException,BuiesnessLogicException{
        Contact contactToModify=selectionnerUnContact();
        contactManager.modifierUnContact(contactToModify);
        System.out.println("le contact a été Modifié  avec succées");


    }

    /**
     * cette methode permet de supprimer un contact
     * @throws DataBaseException
     * @throws BuiesnessLogicException
     */
    public static void supprimerUnContact() throws DataBaseException,BuiesnessLogicException{
        Contact contactTODelete =selectionnerUnContact();
        contactManager.supprimerUnContact(contactTODelete);
        System.out.println("le contact a été supprimé avec succées");

    }
    public static List<Contact> listDesContacts() throws DataBaseException,BuiesnessLogicException{
        List<Contact>  listcontacts=contactManager.getListOfContact();
        System.out.println("les contacts disponibles sont :  ");
        int numContact=1;
        for (Contact c: listcontacts){
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------");

            System.out.println(numContact+"- "+c);
            numContact++;
        }
        System.out.println("\n");
        return listcontacts;
    }


    //TODO: la paritie du gestion du group

    /**
     * cette methode permet du cree un group vide
     * @throws DataBaseException
     * @throws BuiesnessLogicException
     */
    public static void creeUnGroupVide() throws DataBaseException, BuiesnessLogicException{

        System.out.println("enter le nom du group:");
        Group group =new Group(scanner1.nextLine());

        contactManager.creeUnGroup(group);
        System.out.println("le group a été cré avec succées");


    }

    /**
     * cette methode permet du cree un contact avec la list des des contactes
     * @throws DataBaseException
     * @throws BuiesnessLogicException
     */
    public static void creeUnGroupAvecContacts() throws DataBaseException,BuiesnessLogicException{
        System.out.println("saisir le nom du nouveau group: ");
        String nomGroup=scanner1.nextLine();
        //pour arrêter le processus du choix selon le nombre des contactes disponibles
        int i=1;

        //contient les listes des contactes
        List<Contact> listContates=listDesContacts();
        //contient la list des contactes à ajouter
        List<Contact> contactsaAjoute=new ArrayList<>();
        //contient la numerotation des contactes pour savoir si il a était déja choisit ou non
        List<Integer> numeroContact=new ArrayList<>();


       String choix;
       // j'ai fait outer pour specifier la boucle while exacte que je doit manipuler
        outer:while(i<=listContates.size()) {

            System.out.println("choisir le contact numero " + i + " à ajouter ou taper 'fin' pour terminer:   ");
            choix=scanner1.nextLine();

            //sortir si l'utilisateur a taper 'fin'
            if (choix.toUpperCase().equals("FIN"))
                break;
            while(Integer.parseInt(choix)<=0 ||Integer.parseInt(choix)>listContates.size()) {
                System.out.println("s'il vous plaît choisir un numero de contact parmit celle afficher:   ");
                choix=scanner1.nextLine();
                //il fait break juste dans cette boucle interne
                if (choix.toUpperCase().equals("FIN"))
                    break outer;

            }


            //gestion de cas ou l'utilisateur a ajouter un contact 2 fois
            if(numeroContact.contains(Integer.parseInt(choix))){
                System.out.println("ce contact est déja ajouté");
                continue;
            }
            numeroContact.add (Integer.parseInt(choix));

            contactsaAjoute.add(listContates.get(Integer.parseInt(choix)-1));


            i++;

        }
        if (numeroContact.isEmpty()){
            System.out.println("aucun contact choisi");
            return;
        }
        contactManager.creeUnGroup(new Group(nomGroup),contactsaAjoute);

    }
    public static void ajouterAugroup() throws DataBaseException,BuiesnessLogicException{
        System.out.println("saisir le nom du group: ");
        Group group=contactManager.chercherGroupParNom(scanner1.nextLine());
        //pour arrêter le processus du choix selon le nombre des contactes disponibles


        int i=1;

        //contient les listes des contactes
        List<Contact> listContates=listDesContacts();
        //contient la list des contactes à ajouter
        List<Contact> contactsaAjoute=new ArrayList<>();
        //contient la numerotation des contactes pour savoir si il a était déja choisit ou non
        List<Integer> numeroContact=new ArrayList<>();


        String choix;
        // j'ai fait outer pour specifier la boucle while exacte que je doit manipuler
        outer:while(i<=listContates.size()) {

            System.out.println("choisir le contact numero " + i + " à ajouter ou taper 'fin' pour terminer:   ");
            choix=scanner1.nextLine();

            //sortir si l'utilisateur a taper 'fin'
            if (choix.toUpperCase().equals("FIN"))
                break;
            while(Integer.parseInt(choix)<=0 ||Integer.parseInt(choix)>listContates.size()) {
                System.out.println("s'il vous plaît choisir un numero de contact parmi celle afficher:   ");
                choix=scanner1.nextLine();
                //il fait break juste dans cette boucle interne
                if (choix.toUpperCase().equals("FIN"))
                    break outer;

            }

            //cette condition nous permet de savoir si le contact exist déja dans ce group
            if(isContactExistInGroup(group,listContates.get(Integer.parseInt(choix)-1)))
            {
                System.out.println("exist");
                System.out.println("ce contact exist déja dans ce group");
                continue;
            }


            //gestion de cas ou l'utilisateur a ajouter un contact 2 fois
            if(numeroContact.contains(Integer.parseInt(choix))){
                System.out.println("ce contact est déja ajouté");
                continue;
            }
            numeroContact.add (Integer.parseInt(choix));

            contactsaAjoute.add(listContates.get(Integer.parseInt(choix)-1));


            i++;

        }
        if (numeroContact.isEmpty()){
            System.out.println("aucun contact choisi");
            return;
        }
        contactManager.ajouterAuGroup(group,contactsaAjoute);


    }



    /**
     * cette methode permet de chercher un group par son nom
     * @throws DataBaseException
     * @throws BuiesnessLogicException
     */
    public static void chercherUnGroupParNom() throws DataBaseException,BuiesnessLogicException{
        System.out.println("sasir le nom du group:  ");
        Group group=contactManager.chercherGroupParNom(scanner1.nextLine());
        System.out.println("le group trouver:   "+group.getNomGroup());
    }

    /**
     * cette methode permet de supprimer un group
     * @throws DataBaseException
     * @throws BuiesnessLogicException
     */
    public static void supprimerUnGroup() throws DataBaseException,BuiesnessLogicException{
        System.out.println("saisir le nom du group: ");
        String nomDuGroup=scanner1.nextLine();
        Group group=contactManager.chercherGroupParNom(nomDuGroup);
        contactManager.supprimerUnGroup(group);

    }
    public static void main(String[] args)   {
        System.out.println("hello");
        try {
            if (!DbInstaller.isTableInstalled()) {
                new DbInstaller();
                logger.info("la creation des tables a été avec succées");
            }

        } catch (DataBaseException | DbInstellerException ex) {
            System.err.println(ex.getMessage());
        }

        int choix;

        while (true) {

            menu();

            System.out.println("saisir un choix:   ");
            choix = scanner1.nextInt();
            scanner1.nextLine();// eviter le probleme de sauter par nextInt

            switch (choix) {
                case 1 -> {
                    try {
                        creeUncontact();
                    } catch (DataBaseException ex) {
                        System.err.println(ex.getMessage());

                    } catch (BuiesnessLogicException ex) {
                        System.err.println(ex.getMessage());

                    }
                }
                case 2 -> {
                    try {
                        checherUnContactParNom();
                    } catch (DataBaseException ex) {
                        System.err.println(ex.getMessage());

                    } catch (BuiesnessLogicException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
                case 3 -> {

                    try {
                        chercherUnContactParNumero();
                    } catch (DataBaseException ex) {
                        System.err.println(ex.getMessage());

                    } catch (BuiesnessLogicException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
                case 4 -> {
                    try {
                        modifierUnContact();
                    } catch (DataBaseException ex) {
                        System.err.println(ex.getMessage());
                    } catch (BuiesnessLogicException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
                case 5 -> {
                    try {
                        supprimerUnContact();
                    } catch (DataBaseException ex) {
                        System.err.println(ex.getMessage());
                    } catch (BuiesnessLogicException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
                case 6 -> {
                    try {
                        listDesContacts();
                    } catch (DataBaseException | BuiesnessLogicException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
                case 7 -> {


                    try {
                        creeUnGroupVide();
                        System.out.println("le group à été vré avec succées");

                    } catch (DataBaseException | BuiesnessLogicException ex) {
                        System.err.println(ex.getMessage());
                    }


                }case 8 -> {

                    try {
                        creeUnGroupAvecContacts();
                        System.out.println("le group à été vré avec succées");
                    } catch (DataBaseException ex) {
                        System.err.println(ex.getMessage());
                    } catch (BuiesnessLogicException ex) {
                        System.err.println(ex.getMessage());
                    }
                }case 9-> {

                        try {
                            ajouterAugroup();
                            System.out.println("les contactes sont ajoutés avec succée");
                        } catch (DataBaseException ex) {
                            System.err.println(ex.getMessage());
                        } catch (BuiesnessLogicException ex) {
                            System.err.println(ex.getMessage());
                        }
                }case 10->{
                    try {
                        chercherUnGroupParNom();
                    } catch (DataBaseException ex) {
                        System.err.println(ex.getMessage());
                    } catch (BuiesnessLogicException ex) {
                        System.err.println(ex.getMessage());
                    }
                }case 11 ->{
                    try {
                        supprimerUnGroup();
                        System.out.println("le group a été supprimé avec succées");
                    } catch (DataBaseException ex) {
                        System.err.println(ex.getMessage());
                    } catch (BuiesnessLogicException ex) {
                        System.err.println(ex.getMessage());
                    }
                }case 0 ->{

                    System.exit(0);
                }default->{
                    System.err.println("ce choix n'existe pas veuillez choirsir un autre");
                }


            }


        }
    }

    /**
     * cette methode permet de selectionner un contactes parmi pour le traiter(modifier,supprimer)
     * @return
     * @throws DataBaseException
     * @throws BuiesnessLogicException
     */
    public static Contact selectionnerUnContact() throws DataBaseException,BuiesnessLogicException{
        System.out.println("saisir le nom du contact:");
        List<Contact>listContacts=contactManager.chercherUnContactParNom(scanner1.nextLine());
        int numContact=1;
        System.out.println("voici la list des contactes trouver choisir un contact: ");
        for (Contact c:listContacts){
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
            System.out.println(numContact+"- "+c);
            numContact++;
        }
        System.out.println("\n");

        System.out.println("s'il vous plaît choisir un numero de contact parmit celle afficher:   ");
        numContact=scanner1.nextInt();
        scanner1.nextLine();
        while(numContact<=0 ||numContact>listContacts.size()) {
            System.out.println("s'il vous plaît choisir un numero de contact parmit celle afficher:   ");
            numContact=scanner1.nextInt();
            scanner1.nextLine();

        }

        if (listContacts.isEmpty())
            return null;
        return listContacts.get(numContact-1);

    }
    public static  boolean isContactExistInGroup(Group group,Contact c) throws DataBaseException{
        List<Integer> listDesContactInGroup=contactManager.getIdContactes(group);
        for(int id :listDesContactInGroup){

            if(c.getID()==id)

                return true;
        }
        return false;

    }

}
