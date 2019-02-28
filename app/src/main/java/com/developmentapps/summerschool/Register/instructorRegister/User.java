package com.developmentapps.summerschool.Register.instructorRegister;

import java.util.Date;


public class User {
    String username;
    String fullName;
    String Email;
    String Phonenumber;
    String Course;
    String Address;
    String Location;
    String Experience;
    String Institution;
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
        this.Phonenumber = Phonenumber;
    }

    public void setCourse(String Course) {
        this.Course = Course;
    }

    public void setLocation(String Location) {
        this.Location = Location;
    }

    public void setExperience(String Experience) {
        this.Experience = Experience;
    }

    public void setInstitution(String Institution) {
        this.Institution = Institution;
    }

    public void setAddress(String Address) {
        this.Address = Address;
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

    public String getPhonenumber() {
        return Phonenumber;
    }

    public String getCourse() {
        return Course;
    }


    public String getAddress() {
        return Address;
    }

    public String getLocation() {
        return Location;
    }

    public String getExperience() {
        return Experience;
    }

    public String getInstitution() {
        return Institution;
    }

    public Date getSessionExpiryDate() {
        return sessionExpiryDate;
    }
}