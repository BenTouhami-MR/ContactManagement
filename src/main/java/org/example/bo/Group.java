package org.example.bo;

public class Group {

    private int ID;
    private String nomGroup;

    public Group(int Id, String nomGroup) {
        this.ID = Id;
        this.nomGroup = nomGroup;
    }

    public Group(String nomGroup) {
        this.nomGroup = nomGroup;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNomGroup() {
        return nomGroup;
    }

    public void setNomGroup(String nomGroup) {
        this.nomGroup = nomGroup;
    }


}
