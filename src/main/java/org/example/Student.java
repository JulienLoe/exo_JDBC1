package org.example;

import java.util.Date;

public class Student {

    private String lastname;
    private String firstname;
    private int nbclass;

    private java.sql.Date date;

    public Student(String lastname, String firstname, int nbclass, java.sql.Date date) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.nbclass = nbclass;
        this.date = date;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public int getNbclass() {
        return nbclass;
    }

    public java.sql.Date getDate() {
        return date;
    }
}
