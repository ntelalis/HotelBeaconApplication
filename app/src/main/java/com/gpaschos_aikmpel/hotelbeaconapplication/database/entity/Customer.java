package com.gpaschos_aikmpel.hotelbeaconapplication.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Customer {
    @PrimaryKey
    private int customerId;
    private int titleID;
    private String firstName;
    private String lastName;
    private String birthDate;
    private int countryID;
    private String email;
    private String password;
    private String modified;

    public Customer(int customerId, int titleID, String firstName, String lastName, String birthDate, int countryID, String email, String password, String modified) {
        this.customerId = customerId;
        this.titleID = titleID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.countryID = countryID;
        this.email = email;
        this.password = password;
        this.modified = modified;
    }

    public int getTitleID() {
        return titleID;
    }

    public void setTitleID(int titleID) {
        this.titleID = titleID;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public void update(int customerId, int titleID, String firstName, String lastName, String birthDate, int countryID, String modified) {
        setCustomerId(customerId);
        setTitleID(titleID);
        setFirstName(firstName);
        setLastName(lastName);
        setBirthDate(birthDate);
        setCountryID(countryID);
        setModified(modified);
    }
}
