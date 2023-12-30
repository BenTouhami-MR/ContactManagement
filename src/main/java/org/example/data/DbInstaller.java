package org.example.data;
import org.apache.log4j.Logger;

import java.sql.*;
import java.lang.module.Configuration;

public class DbInstaller {
    private static Logger logger = Logger.getLogger(DbInstaller.class);
    private  static Connection connection;
    private  static Statement stm;

    public DbInstaller() throws DbInstellerException {
        try {
            connection = DbConnection.getInstance();

            //creation des reqêtes nécessaire pour la creation de tables

            String requete1 = """
                            
                            CREATE TABLE CONTACT 
                                (Id INTEGER PRIMARY KEY AUTO_INCREMENT,
                                Nom VARCHAR(20),
                                Prenom VARCHAR(30),
                                Adress VARCHAR(90),
                                Telephone1 VARCHAR(30) unique,
                                Telephone2 VARCHAR(30) unique,
                                Emailpersonnel VARCHAR(90),
                                Emailprofessionnel VARCHAR(90),
                                Genre VARCHAR(50)
                            );
            """;
                        
            String requete2=""" 
                                CREATE TABLE GROUPE 
                                (
                                ID INTEGER PRIMARY KEY AUTO_INCREMENT,
                                NomGroup VARCHAR(30) UNIQUE
                            );
                            """;

            String requete3="""
                                CREATE TABLE GROUPOFCONTACT 
                                (IDCONTACT INTEGER references CONTACT(id),
                                IDGROUP INTEGER references Groupe (id)
                            );
                        """;


                stm = connection.createStatement();
                stm.executeUpdate(requete1);
                stm.executeUpdate(requete2);
                stm.executeUpdate(requete3);
            } catch (Exception ex) {
                // Enregistrer cette erreur dans un fichier log
                logger.error(ex);

                // Déclencher cette erreur qui sera traitée par DbInstallerException
                throw new DbInstellerException(ex);
            }
        }

    public static boolean isTableInstalled() throws  DataBaseException{
                boolean isCreated=true;
            try {
                connection = DbConnection.getInstance();
                stm = connection.createStatement();

                // si la table n'exist pas une errerur  de SQLSyntaxErrorException se déclencher
                ResultSet rs = stm.executeQuery("SELECT * FROM GROUPOFCONTACT;");
                rs.close();

            }catch(SQLSyntaxErrorException ex){
                isCreated = false;


            }catch(SQLException ex){
                logger.error(ex);
                throw new DbInstellerException(ex);
            }finally {
                return isCreated;
        }

    }

}


