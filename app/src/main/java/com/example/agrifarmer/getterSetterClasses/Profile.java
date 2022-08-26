package com.example.agrifarmer.getterSetterClasses;

public class Profile {

    String email, fullname, location, phone;

    public Profile() {}

    public Profile(String email, String fullname, String location, String phone) {
        this.email = email;
        this.fullname = fullname;
        this.location = location;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
