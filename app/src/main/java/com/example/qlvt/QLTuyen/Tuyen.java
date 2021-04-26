package com.example.qlvt.QLTuyen;

public class Tuyen {
    private String maTuyen;
    private String tenTuyen;
    private String giaVe;

    public Tuyen(String maTuyen, String tenTuyen, String giaVe) {
        maTuyen = maTuyen;
        tenTuyen = tenTuyen;
        giaVe = giaVe;
    }

    public Tuyen() {
    }

    public String getMaTuyen() {
        return maTuyen;
    }

    public void setMaTuyen(String maTuyen) {
        this.maTuyen = maTuyen;
    }

    public String getTenTuyen() {
        return tenTuyen;
    }

    public void setTenTuyen(String tenTuyen) {
        this.tenTuyen = tenTuyen;
    }

    public String getGiaVe() {
        return giaVe;
    }

    public void setGiaVe(String giaVe) {
        this.giaVe = giaVe;
    }
}
