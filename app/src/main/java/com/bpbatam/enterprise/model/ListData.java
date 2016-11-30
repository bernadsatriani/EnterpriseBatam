package com.bpbatam.enterprise.model;

import java.util.List;

/**
 * Created by Setia Nugraha on 9/16/2016.
 */
public class ListData {
    public String sid;
    private String nama="";
    private String alamat="";
    private String jekel="";
    private String Atr1="";
    private String Atr2="";
    private String Atr3="";
    private int  Img;
    public List<BBS_CATEGORY.Datum> data ;

    public String getAtr3() {
        return(Atr3);
    }

    public void setAtr3(String nama) {
        this.Atr3=nama;
    }

    public String getAtr2() {
        return(Atr2);
    }

    public void setAtr2(String nama) {
        this.Atr2=nama;
    }

    public String getAtr1() {
        return(Atr1);
    }

    public void setAtr1(String nama) {
        this.Atr1=nama;
    }

    public String getNama() {
        return(nama);
    }

    public void setNama(String nama) {
        this.nama=nama;
    }

    public String getAlamat() {
        return(alamat);
    }

    public void setAlamat(String alamat) {
        this.alamat=alamat;
    }

    public String getJekel() {
        return(jekel);
    }

    public void setJekel(String jekel) {
        this.jekel=jekel;
    }

    public void setImg(int  jekel) {
        this.Img=jekel;
    }

    public int getImg() {
        return(Img);
    }

    public class Datum
    {
        public String Atr1;
        public String Atr2;
        public String Atr3;
    }

}
