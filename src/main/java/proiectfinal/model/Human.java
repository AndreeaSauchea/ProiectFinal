package proiectfinal.model;

import java.util.Date;

public class Human {

    private String name;
    private String forename;
    private String cnp;
    private String street;
    private int streetNumber;
    private Date birthday;
    private String typeID;
    private String seriesID;
    private int numberID;


    public Human(String name, String forename, String cnp){
        this.name = name;
        this.forename = forename;
        this.cnp = cnp;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public String getSeriesID() {
        return seriesID;
    }

    public void setSeriesID(String seriesID) {
        this.seriesID = seriesID;
    }

    public int getNumberID() {
        return numberID;
    }

    public void setNumberID(int numberID) {
        this.numberID = numberID;
    }
}
