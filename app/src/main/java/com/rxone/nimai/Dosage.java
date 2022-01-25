package com.rxone.nimai;

public class Dosage {
    private int rem_id;
    private int dos_id;
    private String dosetime;

    public Dosage(int rem_id,int dos_id, String dosetime) {
        this.rem_id = rem_id;
        this.dos_id = dos_id;
        this.dosetime = dosetime;
    }

    public String getDosetime() {
        return dosetime;
    }

    public void setDosetime(String dosetime) {
        this.dosetime = dosetime;
    }

    public int getRem_id() {
        return rem_id;
    }

    public void setRem_id(int rem_id) {
        this.rem_id = rem_id;
    }

    public int getDos_id() {
        return dos_id;
    }

    public void setDos_id(int dos_id) {
        this.dos_id = dos_id;
    }
}
