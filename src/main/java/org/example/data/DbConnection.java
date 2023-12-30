package org.example.data;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import com.mysql.jdbc.Driver;

public class DbConnection {

        Logger logger =Logger.getLogger(getClass());
        private String login;
        private String password;
        private String url;
        private String driver;
        private boolean isinstalled=false;

        private static Connection connexion;



        //creation de la classe DataBaseException pour savoir le type et la source de l'erreur qui se déclenche
        public DbConnection() throws DataBaseException{

                try{

                        //importation des données neccessaire pour se connecter à la base des donneés
                        Properties properties=DbPropertiesLoader.loadPoperties("config.properties");
                        url=properties.getProperty("db.url");
                        login=properties.getProperty("db.login");
                        password=properties.getProperty("db.password");
                        driver=properties.getProperty("db.driver");



                        //assurer le pilote
                        Class.forName(driver);


                        connexion= DriverManager.getConnection(url,login,password);




                }catch(Exception ex){

                        logger.error(ex);
                        throw new DataBaseException(ex);
                }


        }

        public static Connection getInstance() throws DataBaseException{
                        if (connexion==null){
                                new DbConnection();
                        }
                        return connexion;
                }


}
