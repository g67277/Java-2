package com.android.nazirshuqair.contacts;

import java.io.Serializable;

/**
 * Created by nazirshuqair on 10/14/14.
 */
public class Contacts implements Serializable {

    private static final long serialVersionUID = -7791154359356162736L;

    public String contactName;
    public String contactEmail;
    public String contactRelation;
    public String contactNum;



    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactRelation() {
        return contactRelation;
    }

    public void setContactRelation(String contactRelation) {
        this.contactRelation = contactRelation;
    }

    public Contacts(){
        contactName = "";
        contactEmail = "";
        contactRelation = "";
        contactNum = "";
    }

    public Contacts(String id, String email, String relation, String num) {
        this.contactName = id;
        this.contactEmail = email;
        this.contactRelation = relation;
        this.contactNum = num;
    }

    public void setData(Contacts data) {
        contactName = data.getContactName();
        contactEmail = data.getContactEmail();
        contactRelation = data.getContactRelation();
        contactNum = data.getContactNum();
    }

}
