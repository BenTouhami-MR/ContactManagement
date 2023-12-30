package org.example.bll;

import org.example.bo.Contact;
import org.example.bo.Group;
import org.example.data.ContactDao;
import org.example.data.DataBaseException;
import org.example.data.GroupDao;

import java.util.ArrayList;
import java.util.List;

public class ContactManager {
    private GroupDao  groupDao =new GroupDao();
    private ContactDao  contactDao =new ContactDao();


    //TODO: cette partir est consacrée pour la gestion des contacts

    /**
     * cette methode permet du cree un contact
     * @param contact
     * @throws DataBaseException
     */
    public void createContact(Contact contact) throws DataBaseException {
        //chercher le contact par son numero pour savoir s'il existt

        Contact contactTest=contactDao.chercherContactParNumero(contact.gettelephone1());
        contactDao.createContact(contact);

    }


    /**
     * cette methode permet du supprimer un contact par le numero du telephone
     *
     * @param contact@throws DataBaseException
     * @throws BuiesnessLogicException
     */
    public void supprimerUnContact(Contact contact) throws DataBaseException,BuiesnessLogicException{

        if (contact==null){
            throw new BuiesnessLogicException("cette contact n'exist pas");
        }
        contactDao.supprimerUnContact(contact);
    }

    /**
     * cette methode permet retourner la list des contacts
     * @return
     * @throws DataBaseException
     * @throws BuiesnessLogicException
     */

    public ArrayList<Contact> getListOfContact() throws DataBaseException,BuiesnessLogicException{
        if (contactDao.getListContacts().isEmpty())
            throw new BuiesnessLogicException("il y a aucun contact pour le moment");
        return contactDao.getListContacts();
    }

    /**
     * cette methode permet du modifer un contact
      * @param contact
     * @throws DataBaseException
     */
    public void modifierUnContact(Contact contact) throws DataBaseException{
                contactDao.modifierContact(contact);

    }

    /**
     * cette methode permet de retourner la list des contact qu'ont le même nom
     * @param name
     * @return
     * @throws DataBaseException
     */
    public List<Contact> chercherUnContactParNom(String name) throws DataBaseException,BuiesnessLogicException{
        List<Contact> targetContacs=contactDao.chercherContactParNom(name);

        //si la list est vide alors en declanche une erreur nous indique l'inexistance du cette contact
        if (targetContacs.isEmpty())
            throw new BuiesnessLogicException("il n'exsite aucun contact avec ce nom");
        return targetContacs;

    }
    public  Contact chercherContactParNumero(String numero) throws DataBaseException,BuiesnessLogicException{
        Contact contact = contactDao.chercherContactParNumero(numero);
        if (contact==null)
            throw new BuiesnessLogicException("aucun contact avec ce numero");
        return contact;


    }








    //TODO: cette partie est consacrée pour la gestion des groupes
    public  void creeUnGroup(Group group) throws DataBaseException ,BuiesnessLogicException{
        Group group1=groupDao.chercherGroupParNom(group.getNomGroup());
        if (group1!=null){
            throw new BuiesnessLogicException("ce groupe exist déja");
        }

        groupDao.creeUnGroup(group);



    }
    public  void creeUnGroup(Group group, List<Contact> contactAAjouter) throws DataBaseException ,BuiesnessLogicException{
        Group group1=groupDao.chercherGroupParNom(group.getNomGroup());
        if (group1!=null){
            throw new BuiesnessLogicException("ce groupe exist déja");
        }
        groupDao.creeUnGroup(group,contactAAjouter);

    }
    public void ajouterAuGroup (Group group ,List<Contact>contacts) throws DataBaseException{
            groupDao.ajouterAuGroup(group,contacts);
    }
    public Group chercherGroupParNom(String nomGroup) throws DataBaseException,BuiesnessLogicException{
        Group  group=groupDao.chercherGroupParNom(nomGroup);
        if(group==null)
            throw new BuiesnessLogicException("aucun group trouver");
        return group;
    }

    //supprimer un groupe

    public void supprimerUnGroup(Group group) throws DataBaseException,BuiesnessLogicException{

        groupDao.supprimerUnGroup(group);

    }

    public  List<Integer> getIdContactes(Group group) throws DataBaseException{
        return groupDao.getIdContactes(group);
    }
}