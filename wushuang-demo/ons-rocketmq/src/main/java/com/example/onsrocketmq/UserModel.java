package com.example.onsrocketmq;

public class UserModel {
    int ID;
    String USERID;
    String LOGINPASSWORD;
    int ISDISABLE;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    public String getLOGINPASSWORD() {
        return LOGINPASSWORD;
    }

    public void setLOGINPASSWORD(String LOGINPASSWORD) {
        this.LOGINPASSWORD = LOGINPASSWORD;
    }

    public int getISDISABLE() {
        return ISDISABLE;
    }

    public void setISDISABLE(int ISDISABLE) {
        this.ISDISABLE = ISDISABLE;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "ID=" + ID +
                ", USERID='" + USERID + '\'' +
                ", LOGINPASSWORD='" + LOGINPASSWORD + '\'' +
                ", ISDISABLE=" + ISDISABLE +
                '}';
    }
}
