package org.example.data;

import org.apache.log4j.Logger;
import org.example.bo.Contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ContactDao {
    private Logger logger = Logger.getLogger(ContactDao.class);

    private Scanner scnner=new Scanner(System.in);
    private    ArrayList<Contact> contacts;

    private final static String[] listParametre={"Nom","prenom","Adress","telephone1","telephone2","Emailprofessionnel","Emailpersonnel","Genre"};


    /**
     * mehtod pour la creation d'un contact
     *
     * @param contact
     * @throws DataBaseException
     */
    public void createContact(Contact contact) throws DataBaseException {
        try {
            Connection connection = DbConnection.getInstance();
            PreparedStatement stm = connection.prepareStatement("Insert Into CONTACT (nom,prenom,Adress,telephone1,telephone2,Emailprofessionnel,Emailpersonnel,Genre) values (?,?,?,?,?,?,?,?)");


            stm.setString(1, contact.getNom().toUpperCase());
            stm.setString(2, contact.getPrenom().toUpperCase());
            stm.setString(3, contact.getAdress().toUpperCase());
            stm.setString(4, contact.gettelephone1().toUpperCase());
            stm.setString(5, contact.gettelephone2().toUpperCase());
            stm.setString(6, contact.getEmailprofessionnel().toUpperCase());
            stm.setString(7, contact.getEmailpersonnel().toUpperCase());
            stm.setString(8, contact.getGenre().toUpperCase());

            stm.executeUpdate();

        } catch (SQLException ex) {
            logger.error(ex);
            throw new DataBaseException(ex);

        }
    }

    /**
     * cette methode retourn la list des etudiants par order alphabetique
     * @return
     * @throws DataBaseException
     */
    public ArrayList<Contact> getListContacts() throws DataBaseException {
        contacts= new ArrayList<>();
        try {
            Connection connection = DbConnection.getInstance();
            PreparedStatement stm = connection.prepareStatement("Select * FROM Contact order by nom,prenom asc");
            ResultSet rs = stm.executeQuery();
            //TODO: Ajouter à la list des Contacts les COontacts selectionner de la base de données
            while (rs.next()) {
                contacts.add(new Contact(rs.getInt("id"),rs.getString("nom"), rs.getString("prenom"), rs.getString("Adress"), rs.getString("telephone1"),
                        rs.getString("telephone2"), rs.getString("Emailprofessionnel"), rs.getString("Emailpersonnel"),
                        rs.getString("Genre")));
            }
            rs.close();
            return contacts;
        } catch (SQLException ex) {
            logger.error(ex);
            throw new DataBaseException(ex);
        }
    }

    /**
     * cette methode permet de supprimer un contact
     * @param contact
     * @throws DataBaseException
     */
    public void  supprimerUnContact(Contact contact) throws DataBaseException{


                try{
                    Connection connection=DbConnection.getInstance();
                    PreparedStatement stm =connection.prepareStatement("DELETE FROM GROUPOFCONTACT  WHERE IDCONTACT=?");
                    stm.setInt(1,contact.getID());
                    stm.executeUpdate();
                    stm=connection.prepareStatement("DELETE FROM Contact WHERE ID=?");
                    stm.setInt(1,contact.getID());
                    stm.executeUpdate();


                }catch(SQLException ex){
                    logger.error(ex);
                    throw new DataBaseException(ex);
                }
            }






    /**
     * cette methode permet d'assurer la modification d'un contact
     * @param contact
     * @throws DataBaseException
     */
    public  void modifierContact(Contact contact) throws DataBaseException{
    try{
        Connection connection=DbConnection.getInstance();
        PreparedStatement stm1=connection.prepareStatement("Select * from CONTACT where id=?");
        stm1.setInt(1,contact.getID());

        ResultSet rs =stm1.executeQuery();

        PreparedStatement stm2 =connection.prepareStatement("update CONTACT set nom=?,prenom=?," +
                "Adress=?,telephone1=?,telephone2=?,Emailprofessionnel=?,Emailpersonnel=?,Genre=? WHERE ID=?");

        if(rs.next()){

        int i=1;
        String response;
        for (String str:listParametre) {
            String changement = rs.getString(str);
            System.out.println("tu veut changer le " + str + " (O/N) ? ");
            response = scnner.nextLine();
            if (response.toUpperCase().equals("O")) {
                System.out.println("entrer le nouveau " + str + ":  ");
                changement = scnner.nextLine();
                if (str.equals("telephone1") || str.equals("telephone2")) {
                    while (!changement.matches("^0[6,7]\\d{8}$") || !changement.matches("^0[6,7]\\d{8}$")) {
                        System.out.println("syntaxe invalide!!");
                        System.out.println("entrer le nouveau " + str + ":  ");
                        changement = scnner.nextLine();
                    }
                }
                if (str.equals("Emailpersonnel") || str.equals("Emailprofessionnel")) {
                    while (!changement.matches("^[A-Za-z0-9+.-]+@[A-Za-z0-9.]+[A-Za-z]{2,}$") || !changement.matches("^[A-Za-z0-9+.-]+@[A-Za-z0-9.-]+[A-Za-z]{2,}$")) {
                        System.out.println("syntaxe invalide!!");
                        System.out.println("entrer le nouveau " + str + ":  ");
                        changement = scnner.nextLine();
                    }

                }
            }


            stm2.setString(i, changement.toUpperCase());
            i++;
        }
        }

        stm2.setInt(9,contact.getID());
        stm2.executeUpdate();


        rs.close();


    }catch(SQLException ex){
        logger.error(ex);
        throw new DataBaseException(ex);
    }
    }



    /**
     * cette methode de chercher un Contact par son Nom
     * @param pnom
     * @return
     * @throws DataBaseException
     */
    public List<Contact> chercherContactParNom(String pnom) throws DataBaseException{
    try{
        Connection connection=DbConnection.getInstance();
        PreparedStatement stm =connection.prepareStatement("SELECT* FROM CONTACT WHERE NOM=?");
        stm.setString(1,pnom.toUpperCase());
        ResultSet rs= stm.executeQuery();
        contacts=new ArrayList<>();
        while(rs.next()){

            if (rs.getString("nom").equals(pnom.toUpperCase())){
                contacts.add(new Contact(rs.getInt("id"),rs.getString("nom"), rs.getString("prenom"), rs.getString("Adress"), rs.getString("telephone1"),
                        rs.getString("telephone2"), rs.getString("Emailprofessionnel"), rs.getString("Emailpersonnel"),
                        rs.getString("Genre")));

            }


                   }



        rs.close();
        return contacts;

    }catch(SQLException ex){
        logger.error(ex);
        throw new DataBaseException(ex);
    }

}

// normalement un numero est unique
public  Contact chercherContactParNumero(String pnumero) throws DataBaseException {
        Contact contact=null;
    try{
        Connection connection=DbConnection.getInstance();
        PreparedStatement stm =connection.prepareStatement("SELECT* FROM CONTACT WHERE telephone1=? or telephone2=?");
        stm.setString(1,pnumero);
        stm.setString(2,pnumero);
        ResultSet rs= stm.executeQuery();
        contacts=new ArrayList<>();
        if(rs.next()){
            contact=new Contact(rs.getInt("id"),rs.getString("nom"), rs.getString("prenom"), rs.getString("Adress"), rs.getString("telephone1"),
                    rs.getString("telephone2"), rs.getString("Emailprofessionnel"), rs.getString("Emailpersonnel"),
                    rs.getString("Genre"));


        }

        //si il n'existe pas il va retourner null sinon il va retourner la list des Contact trouver

        rs.close();
        return contact;

    }catch(SQLException ex){
        logger.error(ex);
        throw new DataBaseException(ex);
    }
}
}








