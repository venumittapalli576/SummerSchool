package com.developmentapps.summerschool.Register;

import java.util.Date;

public class User {
    String username;
    String fullName;
    String Email;
    String Phonenumber;
    Date sessionExpiryDate;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setPhonenumber(String Phonenumber) {
        this.Phonenumber= Phonenumber;
    }

    public void setSessionExpiryDate(Date sessionExpiryDate) {
        this.sessionExpiryDate = sessionExpiryDate;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhonenumber() {
        return Phonenumber;
    }

    public Date getSessionExpiryDate() {
        return sessionExpiryDate;
    }
}