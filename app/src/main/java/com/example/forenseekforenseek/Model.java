package com.example.forenseekforenseek;

import java.util.jar.Attributes;

public class Model
{
    public static final Model QUANTIZED = null ;
    public static Model FlOAT;
    String Name;
    String Record;
    String ID;
    String Email;
    String Address;

    Model()
    {

    }
    public Model(String Name, String Record, String ID, String Email,String Address) {
        this.Name = Name;
        this.Record = Record;
        this.ID = ID;
        this.Email = Email;
        this.Address = Address;
    }

    public static Model valueOf(String toUpperCase) {
        return null;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getRecord() {
        return Record;
    }

    public void setRecord(String Record) {
        this.Record = Record;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return Email;
    }
    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

   // public void setPurl(String purl) {
       // this.purl = purl;
   // }
}
