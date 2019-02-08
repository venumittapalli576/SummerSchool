package com.developmentapps.summerschool.Register.memberRegister;

import java.util.Date;

public class User {
    String username;
    String fullName;
    String Email;
    String Phonenumber;
    String Fathername;
    String Age;
    String Selectedcourse;
    String Address;
    String Location;
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

    public void setFathername(String Fathername) {
        this.Fathername= Fathername;
    }
    public void setAddress(String Address) {
        this.Address= Address;
    }
    public void setAge(String Age) {
        this.Age= Age;
    }
    public void setLocation(String Location) {
        this.Location= Location;
    }
    public void setSelectedcourse(String Intesteredcourse) {
        this.Selectedcourse= Intesteredcourse;
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

    public String getSelectedcourse() {
        return Phonenumber;
    }

    public String getAge() {
        return Age;
    }

    public String getAddress() {
        return Address;
    }

    public String getLocation() {
        return Location;
    }

    public String getFathername() {
        return Fathername;
    }

    public Date getSessionExpiryDate() {
        return sessionExpiryDate;
    }
}