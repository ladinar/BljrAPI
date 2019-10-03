package com.example.login.model;

import java.io.Serializable;

public class Leads implements Serializable {
    private String lead_id;
    private String opp_name;
    private String nik;
    private String id_customer;
    private String closing_date;
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getClosing_date() {
        return closing_date;
    }

    public void setClosing_date(String closing_date) {
        this.closing_date = closing_date;
    }

    public String getLead_id() {
        return lead_id;
    }

    public void setLead_id(String lead_id) {
        this.lead_id = lead_id;
    }

    public String getOpp_name() {
        return opp_name;
    }

    public void setOpp_name(String opp_name) {
        this.opp_name = opp_name;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

}
