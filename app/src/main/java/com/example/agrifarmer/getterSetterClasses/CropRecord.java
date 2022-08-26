package com.example.agrifarmer.getterSetterClasses;

public class CropRecord {
    String datetime, location, duration, crop1, crop2, crop3;

    public CropRecord() {}

    public CropRecord(String datetime, String location, String duration, String crop1, String crop2, String crop3) {
        this.datetime = datetime;
        this.location = location;
        this.duration = duration;
        this.crop1 = crop1;
        this.crop2 = crop2;
        this.crop3 = crop3;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCrop1() {
        return crop1;
    }

    public void setCrop1(String crop1) {
        this.crop1 = crop1;
    }

    public String getCrop2() {
        return crop2;
    }

    public void setCrop2(String crop2) {
        this.crop2 = crop2;
    }

    public String getCrop3() {
        return crop3;
    }

    public void setCrop3(String crop3) {
        this.crop3 = crop3;
    }
}
