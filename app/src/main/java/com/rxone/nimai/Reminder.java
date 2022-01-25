package com.rxone.nimai;

public class Reminder {
    private int user_id;
    private int rem_id;
    private String doc;
    private String med;
    private String end_date;
    private String start_date;
    private String intrvl;
    private byte[] imgpath;
    private String qty;

    public Reminder(int user_id, int rem_id, String doc,String med, String end_date, String start_date, String intrvl, byte[] imgpath, String qty) {
        this.user_id = user_id;
        this.rem_id = rem_id;
        this.doc = doc;
        this.med = med;
        this.end_date = end_date;
        this.start_date = start_date;
        this.intrvl = intrvl;
        this.imgpath= imgpath;
        this.qty = qty;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }



    public int getRem_id() {
        return rem_id;
    }

    public void setRem_id(int rem_id) {
        this.rem_id = rem_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getMed() {
        return med;
    }

    public void setMed(String med) {
        this.med = med;
    }

    public String getIntrvl() {
        return intrvl;
    }

    public void setIntrvl(String intrvl) {
        this.intrvl = intrvl;
    }


    public byte[] getImgpath() {
        return imgpath;
    }

    public void setImgpath(byte[] imgpath) {
        this.imgpath = imgpath;
    }


    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
