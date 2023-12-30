import org.apache.log4j.Logger;
import org.example.data.DataBaseException;
import org.example.data.DbPropertiesLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class Test {
      Logger logger =Logger.getLogger(getClass());
    public static void main(String[] args ) throws DataBaseException {
        /**
         String login;
         String password;
         String url;
         String driver;

         Connection connexion;


        //creation de la classe DataBaseException pour savoir le type et la source de l'erreur qui se déclenche


            try{

                //importation des données neccessaire pour se connecter à la base des donneés
                Properties properties= DbPropertiesLoader.loadPoperties("config.properties");
                url=properties.getProperty("db.url");
                login=properties.getProperty("db.login");
                password=properties.getProperty("db.password");
                driver=properties.getProperty("db.driver");

                //assurer le pilote
                Class.forName(driver);


                connexion= DriverManager.getConnection(url,login,password);

                PreparedStatement stm=connexion.prepareStatement("CREATE TABLE BONJOUR (id int );");
                stm.executeUpdate();

                System.out.println("la table est crée avec succées");



            }catch(Exception ex){


                throw new DataBaseException(ex);
            }
*/
            Set<Integer> mySet=new HashSet<>();
            mySet.add(4);
            mySet.add(20);
            mySet.add(11);
            mySet.add(4);
            mySet.add(7);

        System.out.println(mySet.stream().toArray()[2]);
        }
    }

