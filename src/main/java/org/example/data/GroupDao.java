package org.example.data;

import org.apache.log4j.Logger;
import org.example.bo.Contact;
import org.example.bo.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GroupDao {

    private Logger logger = Logger.getLogger(GroupDao.class);

    private Scanner scnner=new Scanner(System.in);

    /***
     * cette methode permet de cree un group sans l'ajout d'un contact
     * @param group
     * @throws DataBaseException
     */

    public  void creeUnGroup(Group group) throws DataBaseException{



        try {
            Connection connection = DbConnection.getInstance();

                PreparedStatement stm=connection.prepareStatement("INSERT  into  GROUPE (Nomgroup) values (?); ");
                stm.setString(1, group.getNomGroup().toUpperCase());
                stm.executeUpdate();


        } catch (SQLException ex) {
            logger.error(ex);
            throw new DataBaseException(ex);

        }
    }

    /**
     * cette methode permet d'ajouter un Group on lui ajoute des contacts directement apres sa creation
     * @param group
     * @throws DataBaseException
     */
    public  void creeUnGroup(Group group, List<Contact> contactAAjouter) throws DataBaseException {

        //TODO: verifier si cette utilisateur exist déja
        Group testgroup=chercherGroupParNom(group.getNomGroup());
        if (testgroup!=null) {
            System.out.println("cette utilisateur exist déja");
            return;
        }

        // On ajouter les contatcts dans la list en utilisant chercherContactParNumero()
        try {
            Connection connection = DbConnection.getInstance();

            //TODO: cree le nouveau group;
            creeUnGroup(group);

            //TODO:insertion des contact dans le nouveau group et selectionner son id
            PreparedStatement stm = connection.prepareStatement("SELECT LAST_INSERT_ID() AS last_id;");
            ResultSet rs = stm.executeQuery();
            rs.next();
            int current_id = rs.getInt("LAST_id");

            //TODO:
            for (Contact c : contactAAjouter) {
                stm = connection.prepareStatement("INSERT INTO GROUPOFCONTACT VALUES(?,?)");
                stm.setInt(1, c.getID());
                stm.setInt(2, current_id);
                stm.executeUpdate();
            }

            rs.close();
        } catch (SQLException ex) {
            logger.error(ex);
            throw new DataBaseException(ex);
        }
    }

    /**
     * cette methode permet de chercher un Group à partir de son nom
     * @param nomGroup
     * @return
     * @throws DataBaseException
     */

    public Group chercherGroupParNom(String nomGroup) throws DataBaseException {
        Group group=null;

        try {
            Connection connection = DbConnection.getInstance();
            PreparedStatement stm=connection.prepareStatement("Select *  from GROUPE where NomGroup=?");
            stm.setString(1,nomGroup.toUpperCase());
            ResultSet rs=stm.executeQuery();


            //retourner null s'il n'a pas trouvé aucun Group
            if(rs.next())
                group=new Group(rs.getInt("ID"),rs.getString("nomGroup"));
            rs.close();
            return group;
            //

        } catch (SQLException ex) {
            logger.error(ex);
            throw new DataBaseException(ex);

        }




    }

    public void ajouterAuGroup(Group group, List<Contact> contactsAAjouter) throws DataBaseException {

        // On ajouter les contatcts dans la list en utilisant chercherContactParNumero()
        try {
            Connection connection = DbConnection.getInstance();

            //TODO: ajouter les contactes a un groupes
            for (Contact c : contactsAAjouter) {
                PreparedStatement stm = connection.prepareStatement("INSERT INTO GROUPOFCONTACT VALUES(?,?)");
                stm.setInt(1, c.getID());
                stm.setInt(2,group.getID());
                stm.executeUpdate();
            }


        } catch (SQLException ex) {
            logger.error(ex);
            throw new DataBaseException(ex);
        }    }
        /**
         * cette methode permet de supprimer un group
         * @param group
         * @throws DataBaseException
         */
    public void supprimerUnGroup(Group group) throws DataBaseException {

        try {
            Connection connection = DbConnection.getInstance();

            String sql1 = """
                        DELETE FROM GROUPOFCONTACT WHERE IDGROUP=?;
                        """;
            String sql2= """
                      DELETE FROM GROUPE WHERE ID=?;
                    """
                        ;

            PreparedStatement stm = connection.prepareStatement(sql1);
            stm.setInt(1,group.getID());
            stm.executeUpdate();

            stm = connection.prepareStatement(sql2);
            stm.setInt(1,group.getID());


            stm.executeUpdate();





        } catch (SQLException ex) {
            logger.error(ex);
            throw new DataBaseException(ex);

        }
    }

    public List<Integer> getIdContactes(Group group) throws DataBaseException{
        List<Integer> listIdOfContact=new ArrayList<>();
            try{
                Connection cnx=DbConnection.getInstance();
                PreparedStatement stm =cnx.prepareStatement("SELECT * From GROUPOFCONTACT WHERE IDGROUP=?");
                stm.setInt(1,group.getID());
                ResultSet rs=stm.executeQuery();
                while(rs.next()){
                    listIdOfContact.add(rs.getInt("IDCONTACT"));
                }
                rs.close();
                return listIdOfContact;
            }catch(SQLException ex){
                logger.error(ex);
                throw new DataBaseException(ex);
            }
    }

    }









