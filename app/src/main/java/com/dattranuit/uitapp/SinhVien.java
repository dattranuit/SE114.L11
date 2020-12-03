package com.dattranuit.uitapp;

public class SinhVien {

    private String HoTen;
    private String NgaySinh;
    private String GioiTinh;
    private String MSSV;
    private String LopSH;
    private String Khoa;
    private String BacDT;
    private String HeDT;

    public String getHoTen() {
        return HoTen;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public String getMSSV() {
        return MSSV;
    }

    public String getLopSH() {
        return LopSH;
    }

    public String getKhoa() {
        return Khoa;
    }

    public String getBacDT() {
        return BacDT;
    }

    public String getHeDT() {
        return HeDT;
    }

    public SinhVien(String hoTen, String ngaySinh, String gioiTinh, String MSSV, String lopSH, String khoa, String bacDT, String heDT) {
        HoTen = hoTen;
        NgaySinh = ngaySinh;
        GioiTinh = gioiTinh;
        this.MSSV = MSSV;
        LopSH = lopSH;
        Khoa = khoa;
        BacDT = bacDT;
        HeDT = heDT;
    }
}
