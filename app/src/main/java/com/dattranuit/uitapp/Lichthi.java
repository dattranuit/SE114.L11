package com.dattranuit.uitapp;

import java.io.Serializable;

public class Lichthi implements Serializable {
    private String STT;
    private String maMH;
    private String maLop;
    private String caThi;
    private String thuThi;
    private String ngayThi;
    private String phongThi;

    public Lichthi(String STT, String maMH, String maLop, String caThi, String thuThi, String ngayThi, String phongThi) {
        this.STT = STT;
        this.maMH = maMH;
        this.maLop = maLop;
        this.caThi = caThi;
        this.thuThi = thuThi;
        this.ngayThi = ngayThi;
        this.phongThi = phongThi;
    }

    public String getSTT() {
        return STT;
    }

    public String getMaMH() {
        return maMH;
    }

    public String getMaLop() {
        return maLop;
    }

    public String getCaThi() {
        return caThi;
    }

    public String getThuThi() {
        return thuThi;
    }

    public String getNgayThi() {
        return ngayThi;
    }

    public String getPhongThi() {
        return phongThi;
    }
}
