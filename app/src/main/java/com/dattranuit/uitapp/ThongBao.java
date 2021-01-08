package com.dattranuit.uitapp;

import java.io.Serializable;

public class ThongBao implements Serializable {
    private String Title;
    private String CreateTime;
    private String CBGD;
    private String Khoa_BoMon;
    private String MonHoc;
    private String Lop;
    private String Phong;
    private String TietBD;
    private String TietKT;
    private String Thu;

    public String getTitle() {
        return Title;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public String getCBGD() {
        return CBGD;
    }

    public String getKhoa_BoMon() {
        return Khoa_BoMon;
    }

    public String getMonHoc() {
        return MonHoc;
    }

    public String getLop() {
        return Lop;
    }

    public String getPhong() {
        return Phong;
    }

    public String getTietBD() {
        return TietBD;
    }

    public String getTietKT() {
        return TietKT;
    }

    public String getThu() {
        return Thu;
    }

    public ThongBao(String title, String createTime, String CBGD, String khoa_BoMon, String monHoc, String lop, String phong, String tietBD, String tietKT, String thu) {
        Title = title;
        CreateTime = createTime;
        this.CBGD = CBGD;
        Khoa_BoMon = khoa_BoMon;
        MonHoc = monHoc;
        Lop = lop;
        Phong = phong;
        TietBD = tietBD;
        TietKT = tietKT;
        Thu = thu;
    }
}
